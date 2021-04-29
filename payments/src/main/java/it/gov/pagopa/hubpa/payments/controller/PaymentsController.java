package it.gov.pagopa.hubpa.payments.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.opencsv.ICSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.gov.pagopa.hubpa.payments.entity.Debitor;
import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;
import it.gov.pagopa.hubpa.payments.model.CsvPositionModel;
import it.gov.pagopa.hubpa.payments.model.FindModel;
import it.gov.pagopa.hubpa.payments.model.FindResponseModel;
import it.gov.pagopa.hubpa.payments.model.PaymentJobMinimalModel;
import it.gov.pagopa.hubpa.payments.model.PaymentMinimalModel;
import it.gov.pagopa.hubpa.payments.model.PaymentPositionDetailModel;
import it.gov.pagopa.hubpa.payments.model.PaymentsModel;
import it.gov.pagopa.hubpa.payments.model.UploadCsvModel;
import it.gov.pagopa.hubpa.payments.model.UploadCsvPartialModel;
import it.gov.pagopa.hubpa.payments.model.tribute.TributeServiceModel;
import it.gov.pagopa.hubpa.payments.service.PaymentService;

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

    @ApiOperation(value = "Salva la lista dei pagamenti", notes = "Servizio REST per salvare la lista dei pagamenti", response = PaymentJobMinimalModel.class)
    @PostMapping(value = "create")
    public PaymentJobMinimalModel createPayments(
	    @ApiParam(value = "Lista dei pagamenti", required = true) @RequestBody final UploadCsvPartialModel uploadCvsModel) {

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

	Pageable paging = PageRequest.of(findModel.getPage(), findModel.getSize(), Sort.by("insertDate").descending());
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

	return modelMapper.map(paymentService.getPaymentByPaymentPositionId(id), PaymentPositionDetailModel.class);

    }

    @ApiOperation(value = "Esporta i pagamenti in formato csv dato un jobId", notes = "Esporta i pagamenti in formato csv dato un jobId")
    @GetMapping(value = "/export/{jobId}")
    public void exportCsv(@PathVariable("jobId") Long jobId, HttpServletResponse response)
	    throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
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

}
