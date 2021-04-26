package it.gov.pagopa.hubpa.qworker;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;

import it.gov.pagopa.hubpa.uploadpayments.model.UploadCsvModel;

@Component
public class QueueReceiveController {

  @Value("${QUEUE_NAME}")
  private String queueName;

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
  }

}