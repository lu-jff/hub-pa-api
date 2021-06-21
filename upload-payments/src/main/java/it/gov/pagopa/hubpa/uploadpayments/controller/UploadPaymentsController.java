package it.gov.pagopa.hubpa.uploadpayments.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import it.gov.pagopa.hubpa.commons.model.BooleanResponseModel;
import it.gov.pagopa.hubpa.commons.model.PaymentJobMinimalModel;
import it.gov.pagopa.hubpa.commons.model.UploadCsvModel;
import it.gov.pagopa.hubpa.uploadpayments.model.PaymentJobModel;
import it.gov.pagopa.hubpa.uploadpayments.service.PaymentJobService;

@RestController()
@RequestMapping("upload-payments")
public class UploadPaymentsController {
	@Autowired
	PaymentJobService paymentJobService;

	@Autowired
	private ModelMapper modelMapper;

	private Logger logger = LoggerFactory.getLogger(UploadPaymentsController.class);

	@ApiOperation(value = "Indica se lo stato dei job indicati è diverso da In Attesa", notes = "Servizio REST per ottenere l'informazione se lo stato dei job indicati è diverso da In Attesa", response = BooleanResponseModel.class)
	@GetMapping(value = "statusChanged")
	public BooleanResponseModel isJobStatusChanged(@RequestParam List<Long> jobIds) {
		logger.info("GET is Job Status Changed");
		return new BooleanResponseModel(
				paymentJobService.countByIdsAndStatusNot(jobIds, JobStatusEnum.IN_ATTESA.getStatus()) > 0 ? Boolean.TRUE
						: Boolean.FALSE);
	}

	@ApiOperation(value = "Verifica se sono stati caricati dei job non andati in errore", notes = "Servizio REST per verificare se sono stati caricati dei job non andati in errore", response = BooleanResponseModel.class)
	@GetMapping(value = "isPaymentJobAvailable/{fiscalCode}")
	public BooleanResponseModel isPaymentJobAvailable(@PathVariable("fiscalCode") String fiscalCode) {
		logger.info("GET is Payment Job Available");
		return new BooleanResponseModel(
				paymentJobService.countByFiscalCodeAndStatusNot(fiscalCode, JobStatusEnum.FALLITO.getStatus()) > 0
						? Boolean.TRUE
						: Boolean.FALSE);
	}

	@ApiOperation(value = "Registra un caricamento di file csv", notes = "Servizio REST per registrare un caricamento di file csv", response = BooleanResponseModel.class)
	@PostMapping(value = "createJobRecord")
	public BooleanResponseModel createJobRecord(
			@ApiParam(value = "Modello del job dei pagamenti", required = true) @RequestBody final PaymentJobModel paymentJobModel) {
		logger.info("POST create job record");
		paymentJobModel.setInsertDate(LocalDateTime.now(ZoneId.of("Europe/Paris")));
		return new BooleanResponseModel(paymentJobService.create(modelMapper.map(paymentJobModel, PaymentJob.class)));
	}

	@ApiOperation(value = "Recupera la lista dei file csv caricati", notes = "Servizio REST per recuperare la lista dei file csv caricati", response = List.class)
	@GetMapping(value = "getAll/{fiscalCode}")
	public List<PaymentJobModel> getAllJob(@PathVariable("fiscalCode") String fiscalCode) {
		logger.info("GET Get All");
		List<PaymentJob> paymentJobList = paymentJobService.getAll(fiscalCode);
		return paymentJobList.stream().map(paymentJob -> modelMapper.map(paymentJob, PaymentJobModel.class))
				.collect(Collectors.toList());
	}

	@ApiOperation(value = "Carica sulla coda gli oggetti rappresentati il file csv", notes = "Servizio REST per caricare sulla coda gli oggetti rappresentati il file csv", response = BooleanResponseModel.class)
	@PostMapping(value = "upload")
	public BooleanResponseModel uploadCsvModels(
			@ApiParam(value = "Modello della righe del csv", required = true) @RequestBody final UploadCsvModel uploadCsvModel) {
		logger.info("POST Upload CSV");
		PaymentJob paymentJob = modelMapper.map(uploadCsvModel, PaymentJob.class);
		Long jobId = paymentJobService.savePaymentJob(paymentJob);
		uploadCsvModel.setJobId(jobId);
		paymentJobService.uploadRows(uploadCsvModel);

		return new BooleanResponseModel(true);
	}

	@ApiOperation(value = "Aggiorna un caricamento di file csv", notes = "Servizio REST per aggiornare un caricamento di file csv", response = BooleanResponseModel.class)
	@PostMapping(value = "update/{jobId}")
	public BooleanResponseModel updatePaymentJob(@PathVariable("jobId") Long jobId,
			@ApiParam(value = "Modello del job dei pagamenti", required = true) @RequestBody final PaymentJobMinimalModel paymentJobModel) {
		logger.info("POST update job");
		BooleanResponseModel resp = new BooleanResponseModel(Boolean.FALSE);
		Optional<PaymentJob> opt = paymentJobService.getJob(jobId);
		if (opt.isPresent()) {
			PaymentJob paymentJob = opt.get();
			paymentJob.setStatus(paymentJobModel.getStatus());
			paymentJob.setNRecordAdded(paymentJobModel.getNRecordAdded());
			paymentJob.setNRecordFound(paymentJobModel.getNRecordFound());
			paymentJob.setNRecordWarning(paymentJobModel.getNRecordWarning());
			paymentJob.setElaborationDate(LocalDateTime.now(ZoneId.of("Europe/Paris")));
			resp = new BooleanResponseModel(paymentJobService.create(paymentJob));
		}
		return resp;
	}
}
