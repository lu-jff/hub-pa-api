package it.gov.pagopa.hubpa.qworker;

import javax.jms.ConnectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import it.gov.pagopa.hubpa.uploadpayments.model.BooleanResponseModel;
import it.gov.pagopa.hubpa.uploadpayments.model.PaymentJobMinimalModel;
import it.gov.pagopa.hubpa.uploadpayments.model.UploadCsvModel;

@Component
public class QueueReceiveController {

    @Value("${QUEUE_NAME}")
    private String queueName;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.upload-payments.path}")
    private String uploadPaymentsPath;

    @Value("${service.payments.path}")
    private String paymentsPath;

    private final Logger logger = LoggerFactory.getLogger(QueueReceiveController.class);

    @Bean
    public JmsListenerContainerFactory<DefaultMessageListenerContainer> csvFactory(ConnectionFactory connectionFactory,
	    DefaultJmsListenerContainerFactoryConfigurer configurer) {
	DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	configurer.configure(factory, connectionFactory);
	return factory;
    }

    @JmsListener(destination = "job_queue", containerFactory = "csvFactory")
    public void receiveMessage(UploadCsvModel csv) {

	logger.info("Received message: {}", csv.getFiscalCodeCreditor());
	PaymentJobMinimalModel responsePayments = null;
	try {
	    responsePayments = restTemplate.postForObject(paymentsPath + "/payments/create", csv,
		    PaymentJobMinimalModel.class);
	} catch (Exception e) {
	    logger.error("Errore nella chiamata al servizio create di Payment. Aggiorno il job con errore", e);
	}
	if (responsePayments == null || responsePayments.getJobId() == null) {
	    responsePayments = new PaymentJobMinimalModel();
	    responsePayments.setJobId(csv.getJobId());
	    responsePayments.setStatus(3);
	}

	try {
	    restTemplate.postForObject(uploadPaymentsPath + "/upload-payments/update/" + responsePayments.getJobId(),
		    responsePayments, BooleanResponseModel.class);
	} catch (Exception e) {
	    logger.error("Errore nella chiamata di aggiornamento del job {}", responsePayments.getJobId(), e);
	}

    }

}