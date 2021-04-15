package it.gov.pagopa.hubpa.uploadpayments.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.gov.pagopa.hubpa.uploadpayments.entity.PaymentJob;
import it.gov.pagopa.hubpa.uploadpayments.enumeration.JobStatusEnum;
import it.gov.pagopa.hubpa.uploadpayments.model.BooleanResponseModel;
import it.gov.pagopa.hubpa.uploadpayments.model.PaymentJobModel;
import it.gov.pagopa.hubpa.uploadpayments.service.PaymentJobService;

@RestController()
@RequestMapping("upload-payments")
public class UploadPaymentsController {
    @Autowired
    PaymentJobService paymentJobService;

    @Autowired
    private ModelMapper modelMapper;

    @ApiOperation(value = "Indica se lo stato dei job indicati è diverso da In Attesa", notes = "Servizio REST per ottenere l'informazione se lo stato dei job indicati è diverso da In Attesa", response = BooleanResponseModel.class)
    @GetMapping(value = "statusChanged")
    public BooleanResponseModel isJobStatusChanged(@RequestParam List<Long> jobIds) {
	return new BooleanResponseModel(
		paymentJobService.countByIdsandStatusNot(jobIds, JobStatusEnum.IN_ATTESA.getStatus()) > 0 ? Boolean.TRUE
			: Boolean.FALSE);
    }

    @ApiOperation(value = "Registra un caricamento di file csv", notes = "Servizio REST per registrare un caricamento di file csv", response = BooleanResponseModel.class)
    @PostMapping(value = "createJobRecord")
    public BooleanResponseModel createPaymentJob(
	    @ApiParam(value = "Modello del job dei pagamenti", required = true) @RequestBody final PaymentJobModel paymentJobModel) {
	return new BooleanResponseModel(paymentJobService.create(modelMapper.map(paymentJobModel, PaymentJob.class)));
    }
   
    @ApiOperation(value = "Recupera la lista dei file csv caricati", notes = "Servizio REST per recuperare la lista dei file csv caricati", response = List.class)
    @GetMapping(value = "getAll/{creditorId}")
    public List<PaymentJobModel> getAllJob(@PathVariable("creditorId") Long creditorId) {
	List<PaymentJob> paymentJobList = paymentJobService.getAll(creditorId);
	return paymentJobList.stream().map(paymentJob -> 
	modelMapper.map(paymentJob, PaymentJobModel.class))
		.collect(Collectors.toList());
    }
}
