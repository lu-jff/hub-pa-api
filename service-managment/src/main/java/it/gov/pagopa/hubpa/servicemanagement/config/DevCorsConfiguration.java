package it.gov.pagopa.hubpa.servicemanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DevCorsConfiguration {

    @Bean
    public RestTemplate restTemplate() {
	return new RestTemplate();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
	return new WebMvcConfigurer() {
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
		String origins = System.getenv().get("SPRING_CORS_ORIGINS");
		registry.addMapping("/service-management/**").allowedOrigins(origins);
	    }
	};
    }

}
