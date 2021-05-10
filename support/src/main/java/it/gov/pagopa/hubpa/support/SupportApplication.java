package it.gov.pagopa.hubpa.support;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = { "it.gov.pagopa.hubpa" })
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class SupportApplication {

    public static void main(final String[] args) {
	SpringApplication.run(SupportApplication.class, args);
    }

    @Bean
    public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2).select()
		.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any())
		.build().apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
	return new ApiInfo("Support REST API", "Servizi rest di gestione supporto per l'applicazione TariTefa", "0.0.1",
		null, null, null, null, new ArrayList<>());

    }

}
