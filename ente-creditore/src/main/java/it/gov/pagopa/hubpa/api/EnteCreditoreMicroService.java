package it.gov.pagopa.hubpa.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class EnteCreditoreMicroService {

    public static void main(String[] args) {
	SpringApplication.run(EnteCreditoreMicroService.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
	return new ModelMapper();
    }

    @Bean
    public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2).select()
		.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any())
		.build().apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
	return new ApiInfo("Ente Creditore REST API",
		"Servizi rest di Gestione ente Creditore per l'applicazione TariTefa", "0.0.1", null, null, null, null,
		new ArrayList<>());

    }
}
