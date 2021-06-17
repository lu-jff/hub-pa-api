package it.gov.pagopa.hubpa.servicemanagement.mapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import it.gov.pagopa.hubpa.servicemanagement.entity.PaymentOptionTemplate;
import it.gov.pagopa.hubpa.servicemanagement.entity.Service;
import it.gov.pagopa.hubpa.servicemanagement.entity.TransferTemplate;
import it.gov.pagopa.hubpa.servicemanagement.model.InstallmentModel;
import it.gov.pagopa.hubpa.servicemanagement.model.TributeServiceModel;

public class ConvertTributeServiceModelToService implements Converter<TributeServiceModel, Service> {
    @Override
    public Service convert(MappingContext<TributeServiceModel, Service> context) {
	TributeServiceModel source = context.getSource();
	Service destination = new Service();

	String ibanPrimary = source.getIbanPrimary();
	String ibanSecondary = source.getIbanSecondary();
	String postalIbanPrimary = source.getPostalIbanPrimary();
	String postalIbanHolderPrimary = source.getPostalIbanHolderPrimary();
	String postalAuthCodePrimary = source.getPostalAuthCodePrimary();
	String postalIbanSecondary = source.getPostalIbanSecondary();
	String postalIbanHolderSecondary = source.getPostalIbanHolderSecondary();
	String postalAuthCodeSecondary = source.getPostalAuthCodeSecondary();
	String fiscalCodePrimaryCreditor = source.getFiscalCodePrimaryCreditor();
	String fiscalCodeSecondaryCreditor = source.getFiscalCodeSecondaryCreditor();
	LocalDate duoDateUnique = source.getDueDateUnique();
	List<InstallmentModel> installments = source.getInstallments();
	int totalInstallments = installments != null ? installments.size() : 0;

	destination.setFiscalCodePrimaryCreditor(fiscalCodePrimaryCreditor);
	destination.setFiscalCodeSecondaryCreditor(fiscalCodeSecondaryCreditor);
	destination.setDenomination(source.getDenomination());
	destination.setPercentage(source.getPercentageSecondary());
	destination.setTotalInstallments(totalInstallments);

	int installmentNumber = 0;
	if (duoDateUnique != null) {
		destination.addPaymentOptionTemplate(addPaymentOptionTemplateUnique(duoDateUnique,
				Integer.valueOf(installmentNumber), true, ibanPrimary, ibanSecondary, BigDecimal.valueOf(100),
				BigDecimal.valueOf(100), postalIbanPrimary, postalIbanHolderPrimary, postalAuthCodePrimary,
				postalIbanSecondary, postalIbanHolderSecondary, postalAuthCodeSecondary));
	}

	for (InstallmentModel installment : installments) {
		installmentNumber++;
		destination.addPaymentOptionTemplate(addPaymentOptionTemplateUnique(installment.getDueDate(),
				Integer.valueOf(installmentNumber), false, ibanPrimary, ibanSecondary,
				installment.getPercentagePrimary(), installment.getPercentageSecondary(), postalIbanPrimary,
				postalIbanHolderPrimary, postalAuthCodePrimary, postalIbanSecondary, postalIbanHolderSecondary,
				postalAuthCodeSecondary));
	}

	return destination;
    }

	private PaymentOptionTemplate addPaymentOptionTemplateUnique(LocalDate duoDate, Integer installmentNumber,
			Boolean isFinal, String primaryIban, String secondaryIban, BigDecimal primaryPercentage,
			BigDecimal secondaryPercentage, String postalIbanPrimary, String postalIbanHolderPrimary,
			String postalAuthCodePrimary, String postalIbanSecondary, String postalIbanHolderSecondary,
			String postalAuthCodeSecondary) {
		PaymentOptionTemplate paymentOptionTemplate = new PaymentOptionTemplate();
		paymentOptionTemplate.setDueDate(duoDate);
		paymentOptionTemplate.setInstallmentNumber(installmentNumber);
		paymentOptionTemplate.setIsFinal(isFinal);

		paymentOptionTemplate.addTransferTemplate(addTransferTemplate(primaryIban, primaryPercentage, false,
				postalIbanPrimary, postalIbanHolderPrimary, postalAuthCodePrimary));
		paymentOptionTemplate.addTransferTemplate(addTransferTemplate(secondaryIban, secondaryPercentage, true,
				postalIbanSecondary, postalIbanHolderSecondary, postalAuthCodeSecondary));

		return paymentOptionTemplate;
	}

	private TransferTemplate addTransferTemplate(String iban, BigDecimal percentage, Boolean isSecondaryCreditor,
			String postalIban, String postalIbanHolder, String postalAuthCode) {
		TransferTemplate transferTemplate = new TransferTemplate();
		transferTemplate.setIban(iban);
		transferTemplate.setPercentage(percentage);
		transferTemplate.setIsSecondaryCreditor(isSecondaryCreditor);
		transferTemplate.setPostalIban(postalIban);
		transferTemplate.setPostalIbanHolder(postalIbanHolder);
		transferTemplate.setPostalAuthCode(postalAuthCode);		
		return transferTemplate;

	}

}
