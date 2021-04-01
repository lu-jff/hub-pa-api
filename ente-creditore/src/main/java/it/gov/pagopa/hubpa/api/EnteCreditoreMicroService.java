package it.gov.pagopa.hubpa.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EnteCreditoreMicroService {

	public static void main(String[] args) {
		SpringApplication.run(EnteCreditoreMicroService.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
