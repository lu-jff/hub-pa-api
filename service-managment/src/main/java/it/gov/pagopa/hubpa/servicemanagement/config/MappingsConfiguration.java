package it.gov.pagopa.hubpa.servicemanagement.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.gov.pagopa.hubpa.servicemanagement.entity.Service;
import it.gov.pagopa.hubpa.servicemanagement.mapping.ConvertServiceToTributeServiceModel;
import it.gov.pagopa.hubpa.servicemanagement.mapping.ConvertTributeServiceModelToService;
import it.gov.pagopa.hubpa.servicemanagement.model.TributeServiceModel;

@Configuration
public class MappingsConfiguration {

    @Bean
    public ModelMapper modelMapper() {
	ModelMapper modelMapper = new ModelMapper();
	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	Converter<TributeServiceModel, Service> converterService = new ConvertTributeServiceModelToService();
	Converter<Service, TributeServiceModel> converterTributeServiceModel = new ConvertServiceToTributeServiceModel();
	
	modelMapper.createTypeMap(TributeServiceModel.class, Service.class).setConverter(converterService);
	modelMapper.createTypeMap(Service.class, TributeServiceModel.class).setConverter(converterTributeServiceModel);

	return modelMapper;
    }

}
