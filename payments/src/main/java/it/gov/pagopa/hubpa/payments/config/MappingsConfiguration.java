package it.gov.pagopa.hubpa.payments.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.gov.pagopa.hubpa.payments.entity.Debitor;
import it.gov.pagopa.hubpa.payments.mapper.ConvertDebitorModelToDebitor;
import it.gov.pagopa.hubpa.payments.mapper.ConvertUploadCsvModelToPaymentsModel;
import it.gov.pagopa.hubpa.payments.model.DebitorModel;
import it.gov.pagopa.hubpa.payments.model.PaymentsModel;
import it.gov.pagopa.hubpa.payments.model.UploadCsvModel;

@Configuration
public class MappingsConfiguration {

    @Bean
    public ModelMapper modelMapper() {
	ModelMapper mapper = new ModelMapper();
	mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

	Converter<DebitorModel, Debitor> convertDebitorModelToDebitor = new ConvertDebitorModelToDebitor();
	Converter<UploadCsvModel, PaymentsModel> converteruploadCsvModelToPaymentsModel = new ConvertUploadCsvModelToPaymentsModel();
	
	mapper.createTypeMap(DebitorModel.class, Debitor.class).setConverter(convertDebitorModelToDebitor);
	mapper.createTypeMap(UploadCsvModel.class, PaymentsModel.class).setConverter(converteruploadCsvModelToPaymentsModel);
	

	return mapper;
    }

}
