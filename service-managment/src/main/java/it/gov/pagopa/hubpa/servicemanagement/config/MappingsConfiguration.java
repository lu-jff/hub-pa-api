package it.gov.pagopa.hubpa.servicemanagement.config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.gov.pagopa.hubpa.servicemanagement.entity.PaymentOptionTemplate;
import it.gov.pagopa.hubpa.servicemanagement.entity.Service;
import it.gov.pagopa.hubpa.servicemanagement.entity.TransferTemplate;
import it.gov.pagopa.hubpa.servicemanagement.model.InstallmentModel;
import it.gov.pagopa.hubpa.servicemanagement.model.TributeServiceModel;

@Configuration
public class MappingsConfiguration {

    @Bean
    public ModelMapper modelMapper() {
	ModelMapper modelMapper = new ModelMapper();
	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	Converter<TributeServiceModel, Service> converterService = new Converter<TributeServiceModel, Service>() {
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
			    Integer.valueOf(installmentNumber), true, ibanPrimary, ibanSecondary, 100, 100));
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
		    Boolean isFinal, String primaryIban, String secondaryIban, Integer primaryPercentage,
		    Integer secondaryPercentage) {
		PaymentOptionTemplate paymentOptionTemplate = new PaymentOptionTemplate();
		paymentOptionTemplate.setDueDate(duoDate);
		paymentOptionTemplate.setInstallmentNumber(installmentNumber);
		paymentOptionTemplate.setIsFinal(isFinal);

		paymentOptionTemplate.addTransferTemplate(addTransferTemplate(primaryIban, primaryPercentage, false));
		paymentOptionTemplate
			.addTransferTemplate(addTransferTemplate(secondaryIban, secondaryPercentage, true));

		return paymentOptionTemplate;
	    }

	    private TransferTemplate addTransferTemplate(String iban, Integer percentage, Boolean isSecondaryCreditor) {
		TransferTemplate transferTemplate = new TransferTemplate();
		transferTemplate.setIban(iban);
		transferTemplate.setPercentage(percentage);
		transferTemplate.setIsSecondaryCreditor(isSecondaryCreditor);

		return transferTemplate;

	    }

	};
	Converter<Service, TributeServiceModel> converterTributeServiceModel = new Converter<Service, TributeServiceModel>() {
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
	};
	modelMapper.createTypeMap(TributeServiceModel.class, Service.class).setConverter(converterService);
	modelMapper.createTypeMap(Service.class, TributeServiceModel.class).setConverter(converterTributeServiceModel);

	return modelMapper;
    }

}
