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
	Long creditorId = source.getIdPrimaryCreditor();
	Long secondaryCreditorId = source.getIdSecondaryCreditor();
	LocalDate duoDateUnique = source.getDueDateUnique();
	List<InstallmentModel> installments = source.getInstallments();
	int totalInstallments = installments != null ? installments.size() : 0;

	destination.setCreditorId(creditorId);
	destination.setSecondaryCreditorId(secondaryCreditorId);
	destination.setDenomination(source.getDenomination());
	destination.setPercentage(source.getPercentageSecondary());
	destination.setTotalInstallments(totalInstallments);

	int installmentNumber = 0;
	if (duoDateUnique != null) {
	    destination.addPaymentOptionTemplate(addPaymentOptionTemplateUnique(duoDateUnique,
		    Integer.valueOf(installmentNumber), true, ibanPrimary, ibanSecondary, BigDecimal.valueOf(100), BigDecimal.valueOf(100)));
	}

	for (InstallmentModel installment : installments) {
	    installmentNumber++;
	    destination.addPaymentOptionTemplate(addPaymentOptionTemplateUnique(installment.getDueDate(),
		    Integer.valueOf(installmentNumber), false, ibanPrimary, ibanSecondary,
		    installment.getPercentagePrimary(), installment.getPercentageSecondary()));
	}

	return destination;
    }

    private PaymentOptionTemplate addPaymentOptionTemplateUnique(LocalDate duoDate, Integer installmentNumber,
	    Boolean isFinal, String primaryIban, String secondaryIban, BigDecimal primaryPercentage,
	    BigDecimal secondaryPercentage) {
	PaymentOptionTemplate paymentOptionTemplate = new PaymentOptionTemplate();
	paymentOptionTemplate.setDueDate(duoDate);
	paymentOptionTemplate.setInstallmentNumber(installmentNumber);
	paymentOptionTemplate.setIsFinal(isFinal);

	paymentOptionTemplate.addTransferTemplate(addTransferTemplate(primaryIban, primaryPercentage, false));
	paymentOptionTemplate
		.addTransferTemplate(addTransferTemplate(secondaryIban, secondaryPercentage, true));

	return paymentOptionTemplate;
    }

    private TransferTemplate addTransferTemplate(String iban, BigDecimal percentage, Boolean isSecondaryCreditor) {
	TransferTemplate transferTemplate = new TransferTemplate();
	transferTemplate.setIban(iban);
	transferTemplate.setPercentage(percentage);
	transferTemplate.setIsSecondaryCreditor(isSecondaryCreditor);

	return transferTemplate;

    }

}
