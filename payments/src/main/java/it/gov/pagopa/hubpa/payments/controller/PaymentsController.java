package it.gov.pagopa.hubpa.payments.controller;

import com.opencsv.ICSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.gov.pagopa.hubpa.payments.entity.Debitor;
import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;
import it.gov.pagopa.hubpa.payments.model.*;
import it.gov.pagopa.hubpa.payments.model.tribute.TributeServiceModel;
import it.gov.pagopa.hubpa.payments.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("payments")
public class PaymentsController {
    @Autowired
    PaymentService paymentService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.service-management.path}")
    private String serviceManagementPath;

    private Logger logger = LoggerFactory.getLogger(PaymentsController.class);

    @ApiOperation(value = "Salva la lista dei pagamenti", notes = "Servizio REST per salvare la lista dei pagamenti", response = PaymentJobMinimalModel.class)
    @PostMapping(value = "create")
    public PaymentJobMinimalModel createPayments(
	    @ApiParam(value = "Lista dei pagamenti", required = true) @Valid @RequestBody final UploadCsvPartialModel uploadCvsModel) {
	logger.info("POST create payments");
	TributeServiceModel tributeServiceModel = restTemplate.getForObject(
		serviceManagementPath + "/service-management/service/" + uploadCvsModel.getFiscalCodeCreditor(),
		TributeServiceModel.class);
	UploadCsvModel upload = modelMapper.map(uploadCvsModel, UploadCsvModel.class);
	upload.setTributeService(tributeServiceModel);
	PaymentsModel paymentsModel = modelMapper.map(upload, PaymentsModel.class);
	List<Debitor> debitors = paymentsModel.getDebitors().stream()
		.map(paymentModel -> modelMapper.map(paymentModel, Debitor.class)).collect(Collectors.toList());

	return paymentService.create(debitors);
    }

    @ApiOperation(value = "Recupera lista dei pagamenti dato il codice fiscale dell'ente", notes = "Servizio REST per recuperare lista dei pagamenti dato il codice fiscale dell'ente", response = List.class)
    @PostMapping(value = "find")
    public FindResponseModel getPayments(
	    @ApiParam(value = "Filtri", required = true) @RequestBody final FindModel findModel) {

	logger.info("POST find payments");

	Pageable paging = PageRequest.of(findModel.getPage(), findModel.getSize(),
		Sort.by("information").descending().and(Sort.by("insertDate").descending()));
	Page<PaymentPosition> pageResults = paymentService.getPaymentsByFilters(findModel.getFiscalCode(),
		findModel.getFilters(), paging);

	List<PaymentPosition> content = pageResults.getContent();
	List<PaymentMinimalModel> result = content.stream()
		.map(paymentModel -> modelMapper.map(paymentModel, PaymentMinimalModel.class))
		.collect(Collectors.toList());

	FindResponseModel response = new FindResponseModel();
	response.setCurrentPage(pageResults.getNumber());
	response.setPayments(result);
	response.setTotalItems(pageResults.getTotalElements());
	response.setTotalPages(pageResults.getTotalPages());

	return response;
    }

    @ApiOperation(value = "Recupera il dettaglio di un pagamento dato un paymentPositionId", notes = "Recupera il dettaglio di un pagamento dato un paymentPositionId", response = PaymentPositionDetailModel.class)
    @GetMapping(value = "/info/{id}")
    public PaymentPositionDetailModel getPaymentsByPaymentPositionId(@PathVariable("id") Long id) {
	logger.info("GET info");
	return modelMapper.map(paymentService.getPaymentByPaymentPositionId(id), PaymentPositionDetailModel.class);

    }

    @ApiOperation(value = "Esporta i pagamenti in formato csv dato un jobId", notes = "Esporta i pagamenti in formato csv dato un jobId")
    @GetMapping(value = "/export/{jobId}")
    public void exportCsv(@PathVariable("jobId") Long jobId, HttpServletResponse response)
	    throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
	logger.info("GET export CSV");
	String filename = "pagamenti.csv";

	response.setContentType("text/csv");
	response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

	StatefulBeanToCsv<CsvPositionModel> writer = new StatefulBeanToCsvBuilder<CsvPositionModel>(
		response.getWriter()).withQuotechar(ICSVWriter.NO_QUOTE_CHARACTER)
			.withSeparator(ICSVWriter.DEFAULT_SEPARATOR).withOrderedResults(false).build();

	List<PaymentPosition> content = paymentService.getPaymentsByJobId(jobId);
	List<CsvPositionModel> csvPositions = content.stream()
		.map(csvPositionModel -> modelMapper.map(csvPositionModel, CsvPositionModel.class))
		.collect(Collectors.toList());
	// write all users to csv file
	writer.write(csvPositions);

    }

    @ApiOperation(value = "Pubblica i pagamenti selezionati", notes = "Servizio REST per pubblicare i pagamenti selezionati", response = BooleanResponseModel.class)
    @PostMapping(value = "publishPayments")
    public BooleanResponseModel publishPayments(
	    @ApiParam(value = "Lista di ID e data", required = true) @Valid @RequestBody final PublishModel publishModel) {
	logger.info("POST publish payments");
	for (Long id : publishModel.getIds()) {
	    paymentService.updatePublishPayment(id, publishModel.getPublishDate());
	}
	return new BooleanResponseModel(true);
    }
}
