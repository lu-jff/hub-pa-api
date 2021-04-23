package it.gov.pagopa.hubpa.payments.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.gov.pagopa.hubpa.payments.entity.Debitor;
import it.gov.pagopa.hubpa.payments.model.PaymentJobMinimalModel;
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
}
