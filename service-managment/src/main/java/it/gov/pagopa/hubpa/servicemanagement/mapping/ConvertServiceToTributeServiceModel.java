package it.gov.pagopa.hubpa.servicemanagement.mapping;

import java.math.BigDecimal;
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
	    String fiscalCodePrimaryCreditor = source.getFiscalCodePrimaryCreditor();
	    String fiscalCodeSecondaryCreditor = source.getFiscalCodeSecondaryCreditor();
	    BigDecimal percentageSecondary = source.getPercentage();

	    destination.setDenomination(denomination);
	    destination.setFiscalCodePrimaryCreditor(fiscalCodePrimaryCreditor);
	    destination.setFiscalCodeSecondaryCreditor(fiscalCodeSecondaryCreditor);
	    destination.setPercentageSecondary(percentageSecondary);

	    List<PaymentOptionTemplate> paymentOptionTemplateList = source.getPaymentOptionTemplate();
	    List<InstallmentModel> installments = new ArrayList<>();
	    if (!paymentOptionTemplateList.isEmpty()) {
		Collections.sort(paymentOptionTemplateList,
			(paymentOptionTemplate2, paymentOptionTemplate1) -> paymentOptionTemplate2
				.getInstallmentNumber().compareTo(paymentOptionTemplate2.getInstallmentNumber()));
		for (PaymentOptionTemplate paymentOptionTemplate : paymentOptionTemplateList) {
		    this.setIbans(paymentOptionTemplate, destination);
		    if (Boolean.TRUE.equals(paymentOptionTemplate.getIsFinal())) {
			destination.setDueDateUnique(paymentOptionTemplate.getDueDate());
		    } else {
			InstallmentModel installmentModel = new InstallmentModel();
			installmentModel.setDueDate(paymentOptionTemplate.getDueDate());

			this.addInstallment(paymentOptionTemplate, installmentModel, installments);
		    }
		}
	    }
	    if (!installments.isEmpty()) {
		destination.setInstallments(installments);
	    }
	
	return destination;
    }
    private void addInstallment(PaymentOptionTemplate paymentOptionTemplate, InstallmentModel installmentModel,
	    List<InstallmentModel> installments) {
	List<TransferTemplate> transferTemplateList = paymentOptionTemplate.getTransferTemplate();
	if (!transferTemplateList.isEmpty()) {
	    for (TransferTemplate transferTemplate : transferTemplateList) {
		BigDecimal percentage = transferTemplate.getPercentage();
		if (Boolean.TRUE.equals(transferTemplate.getIsSecondaryCreditor())) {
		    installmentModel.setPercentageSecondary(percentage);
		} else {
		    installmentModel.setPercentagePrimary(percentage);
		}
	    }
	}
	installments.add(installmentModel);
    }
    private void setIbans(PaymentOptionTemplate paymentOptionTemplate, TributeServiceModel tributeServiceModel) {
	List<TransferTemplate> transferTemplateList = paymentOptionTemplate.getTransferTemplate();
	if (!transferTemplateList.isEmpty()) {
	    for (TransferTemplate transferTemplate : transferTemplateList) {
		String iban = transferTemplate.getIban();
		String postalIban = transferTemplate.getPostalIban();
		String postalIbanHolder = transferTemplate.getPostalIbanHolder();
		String postalAuthCode = transferTemplate.getPostalAuthCode();
		if (Boolean.TRUE.equals(transferTemplate.getIsSecondaryCreditor())) {
		    tributeServiceModel.setIbanSecondary(iban);
			tributeServiceModel.setPostalIbanSecondary(postalIban);
			tributeServiceModel.setPostalIbanHolderSecondary(postalIbanHolder);
			tributeServiceModel.setPostalAuthCodeSecondary(postalAuthCode);
		} else {
		    tributeServiceModel.setIbanPrimary(iban);
			tributeServiceModel.setPostalIbanPrimary(postalIban);
			tributeServiceModel.setPostalIbanHolderPrimary(postalIbanHolder);
			tributeServiceModel.setPostalAuthCodePrimary(postalAuthCode);
		}
	    }
	}
    }

}
