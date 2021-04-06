package it.gov.pagopa.hubpa.servicemanagement.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import it.gov.pagopa.hubpa.servicemanagement.entity.PaymentOptionTemplate;
import it.gov.pagopa.hubpa.servicemanagement.entity.Service;
import it.gov.pagopa.hubpa.servicemanagement.entity.TransferTemplate;
import it.gov.pagopa.hubpa.servicemanagement.model.InstallmentModel;
import it.gov.pagopa.hubpa.servicemanagement.model.TributeServiceModel;

public class ConvertServiceToTributeServiceModel implements Converter<Service, TributeServiceModel> {
    @Override
    public TributeServiceModel convert(MappingContext<Service, TributeServiceModel> context) {
	Service source = context.getSource();
	TributeServiceModel destination = new TributeServiceModel();
	
	String denomination = source.getDenomination();
	    long creditorId = source.getCreditorId();
	    long secondaryCreditorId = source.getSecondaryCreditorId();
	    Integer percentageSecondary = source.getPercentage();

	    destination.setDenomination(denomination);
	    destination.setIdPrimaryCreditor(creditorId);
	    destination.setIdSecondaryCreditor(secondaryCreditorId);
	    destination.setPercentageSecondary(percentageSecondary);

	    List<PaymentOptionTemplate> paymentOptionTemplateList = source.getPaymentOptionTemplate();
	    List<InstallmentModel> installments = new ArrayList<>();
	    if (!paymentOptionTemplateList.isEmpty()) {
		Collections.sort(paymentOptionTemplateList,
			(paymentOptionTemplate2, paymentOptionTemplate1) -> paymentOptionTemplate2
				.getInstallmentNumber().compareTo(paymentOptionTemplate2.getInstallmentNumber()));
		for (PaymentOptionTemplate paymentOptionTemplate : paymentOptionTemplateList) {
		    if (Boolean.TRUE.equals(paymentOptionTemplate.getIsFinal())) {
			destination.setDueDateUnique(paymentOptionTemplate.getDueDate());
		    } else {
			InstallmentModel installmentModel = new InstallmentModel();
			installmentModel.setDueDate(paymentOptionTemplate.getDueDate());

			this.addInstallment(paymentOptionTemplate, installmentModel, destination, installments);
		    }
		}
	    }
	    if (!installments.isEmpty()) {
		destination.setInstallments(installments);
	    }
	
	return destination;
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


}