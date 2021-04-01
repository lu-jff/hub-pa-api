package it.gov.pagopa.hubpa.servicemanagement.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.gov.pagopa.hubpa.servicemanagement.entity.PaymentOptionTemplate;
import it.gov.pagopa.hubpa.servicemanagement.entity.TransferTemplate;
import it.gov.pagopa.hubpa.servicemanagement.model.InstallmentModel;
import it.gov.pagopa.hubpa.servicemanagement.model.TributeServiceModel;
import it.gov.pagopa.hubpa.servicemanagement.repository.ServiceRepository;

@Service
public class ServiceManagementService {
    @Autowired
    ServiceRepository serviceRepository;

    public Boolean isServiceConfigurated(Long creditorId) {
	long countConfiguration = serviceRepository.countByCreditorId(creditorId);
	return countConfiguration > 0;
    }

    public TributeServiceModel getService(Long id) {
	List<it.gov.pagopa.hubpa.servicemanagement.entity.Service> services = serviceRepository.findByCreditorId(id);
	TributeServiceModel tributeServiceModel = new TributeServiceModel();
	if (!services.isEmpty()) {
	    it.gov.pagopa.hubpa.servicemanagement.entity.Service service = services.get(0);

	    String denomination = service.getDenomination();
	    long creditorId = service.getCreditorId();
	    long secondaryCreditorId = service.getSecondaryCreditorId();
	    Integer percentageSecondary = service.getPercentage();

	    tributeServiceModel.setDenomination(denomination);
	    tributeServiceModel.setIdPrimaryCreditor(creditorId);
	    tributeServiceModel.setIdSecondaryCreditor(secondaryCreditorId);
	    tributeServiceModel.setPercentageSecondary(percentageSecondary);

	    List<PaymentOptionTemplate> paymentOptionTemplateList = service.getPaymentOptionTemplate();
	    List<InstallmentModel> installments = new ArrayList<>();
	    if (!paymentOptionTemplateList.isEmpty()) {
		Collections.sort(paymentOptionTemplateList,
			(paymentOptionTemplate2, paymentOptionTemplate1) -> paymentOptionTemplate2
				.getInstallmentNumber().compareTo(paymentOptionTemplate2.getInstallmentNumber()));
		for (PaymentOptionTemplate paymentOptionTemplate : paymentOptionTemplateList) {
		    if (Boolean.TRUE.equals(paymentOptionTemplate.getIsFinal())) {
			tributeServiceModel.setDueDateUnique(paymentOptionTemplate.getDueDate());
		    } else {
			InstallmentModel installmentModel = new InstallmentModel();
			installmentModel.setDueDate(paymentOptionTemplate.getDueDate());

			this.addInstallment(paymentOptionTemplate, installmentModel, tributeServiceModel, installments);
		    }
		}
	    }
	    if (!installments.isEmpty()) {
		tributeServiceModel.setInstallments(installments);
	    }
	}

	return tributeServiceModel;
    }

    private void addInstallment(PaymentOptionTemplate paymentOptionTemplate, InstallmentModel installmentModel,
	    TributeServiceModel tributeServiceModel, List<InstallmentModel> installments) {
	List<TransferTemplate> transferTemplateList = paymentOptionTemplate.getTransferTemplate();
	if (!transferTemplateList.isEmpty()) {
	    for (TransferTemplate transferTemplate : transferTemplateList) {
		Integer percentage = transferTemplate.getPercentage();
		String iban = transferTemplate.getIban();
		if (Boolean.TRUE.equals(transferTemplate.getIsSecondaryCreditor())) {
		    installmentModel.setPercentageSecondary(percentage);
		    tributeServiceModel.setIbanSecondary(iban);
		} else {
		    installmentModel.setPercentagePrimary(percentage);
		    tributeServiceModel.setIbanPrimary(iban);
		}
	    }
	}
	installments.add(installmentModel);
    }

    @Transactional
    public Boolean saveService(TributeServiceModel tributeService) {
	it.gov.pagopa.hubpa.servicemanagement.entity.Service service = new it.gov.pagopa.hubpa.servicemanagement.entity.Service();

	String ibanPrimary = tributeService.getIbanPrimary();
	String ibanSecondary = tributeService.getIbanSecondary();
	Long creditorId = tributeService.getIdPrimaryCreditor();
	Long secondaryCreditorId = tributeService.getIdSecondaryCreditor();
	LocalDate duoDateUnique = tributeService.getDueDateUnique();
	List<InstallmentModel> installments = tributeService.getInstallments();
	int totalInstallments = installments != null ? installments.size() : 0;

	service.setCreditorId(creditorId);
	service.setSecondaryCreditorId(secondaryCreditorId);
	service.setDenomination(tributeService.getDenomination());
	service.setPercentage(tributeService.getPercentageSecondary());
	service.setTotalInstallments(totalInstallments);

	Collections.sort(installments,
		(installment2, installment1) -> installment1.getDueDate().compareTo(installment2.getDueDate()));

	int installmentNumber = 0;
	if (duoDateUnique != null) {
	    service.addPaymentOptionTemplate(addPaymentOptionTemplateUnique(duoDateUnique,
		    Integer.valueOf(installmentNumber), true, ibanPrimary, ibanSecondary, 100, 100));
	}

	for (InstallmentModel installment : installments) {
	    installmentNumber++;
	    service.addPaymentOptionTemplate(addPaymentOptionTemplateUnique(installment.getDueDate(),
		    Integer.valueOf(installmentNumber), false, ibanPrimary, ibanSecondary,
		    installment.getPercentagePrimary(), installment.getPercentageSecondary()));
	}

	serviceRepository.saveAndFlush(service);

	return true;
    }

    private PaymentOptionTemplate addPaymentOptionTemplateUnique(LocalDate duoDate, Integer installmentNumber,
	    Boolean isFinal, String primaryIban, String secondaryIban, Integer primaryPercentage,
	    Integer secondaryPercentage) {
	PaymentOptionTemplate paymentOptionTemplate = new PaymentOptionTemplate();
	paymentOptionTemplate.setDueDate(duoDate);
	paymentOptionTemplate.setInstallmentNumber(installmentNumber);
	paymentOptionTemplate.setIsFinal(isFinal);

	paymentOptionTemplate.addTransferTemplate(addTransferTemplate(primaryIban, primaryPercentage, false));
	paymentOptionTemplate.addTransferTemplate(addTransferTemplate(secondaryIban, secondaryPercentage, true));

	return paymentOptionTemplate;
    }

    private TransferTemplate addTransferTemplate(String iban, Integer percentage, Boolean isSecondaryCreditor) {
	TransferTemplate transferTemplate = new TransferTemplate();
	transferTemplate.setIban(iban);
	transferTemplate.setPercentage(percentage);
	transferTemplate.setIsSecondaryCreditor(isSecondaryCreditor);

	return transferTemplate;

    }
}
