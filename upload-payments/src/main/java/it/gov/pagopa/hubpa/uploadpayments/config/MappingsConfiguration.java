package it.gov.pagopa.hubpa.uploadpayments.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.gov.pagopa.hubpa.uploadpayments.entity.PaymentJob;
import it.gov.pagopa.hubpa.uploadpayments.mapping.ConvertUploadCsvModelToPaymentJob;
import it.gov.pagopa.hubpa.commons.model.UploadCsvModel;

@Configuration
public class MappingsConfiguration {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Converter<UploadCsvModel, PaymentJob> converterUploadCsvModelToPaymentJob = new ConvertUploadCsvModelToPaymentJob();

		mapper.createTypeMap(UploadCsvModel.class, PaymentJob.class).setConverter(converterUploadCsvModelToPaymentJob);

		return mapper;
	}

}
