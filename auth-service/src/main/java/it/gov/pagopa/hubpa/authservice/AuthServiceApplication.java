package it.gov.pagopa.hubpa.authservice;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Configuration
@ComponentScan(basePackages = { "it.gov.pagopa.hubpa" })
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:integration.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:${spid-spring-integration.properties.path}", ignoreResourceNotFound = true)
@EnableAutoConfiguration(exclude = { org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class })
public class AuthServiceApplication {

    public static void main(final String[] args) {
	SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2).select()
		.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any())
		.build().apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
	return new ApiInfo("SPID REST API", "Servizi rest di autenticazione SPID per l'applicazione TariTefa", "0.0.1",
		null, null, null, null, new ArrayList<>());

    }
}
