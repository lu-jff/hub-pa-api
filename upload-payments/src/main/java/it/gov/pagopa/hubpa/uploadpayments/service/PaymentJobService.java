
package it.gov.pagopa.hubpa.uploadpayments.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import it.gov.pagopa.hubpa.uploadpayments.entity.PaymentJob;
import it.gov.pagopa.hubpa.uploadpayments.model.UploadCsvModel;
import it.gov.pagopa.hubpa.uploadpayments.repository.PaymentJobRepository;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Service
public class PaymentJobService {

    @Autowired
    private PaymentJobRepository paymentJobRepository;

    @Autowired
    private ApplicationContext context;

    @Value("${QUEUE_NAME}")
    private String queueName;

    // Send JSON messages
    @Bean
    MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    public Long countByIdsAndStatusNot(List<Long> jobIds, Integer status) {
        return paymentJobRepository.countByJobIdInAndStatusNot(jobIds, status);
    }

    public Boolean create(PaymentJob paymentJob) {
        paymentJobRepository.saveAndFlush(paymentJob);
        return Boolean.TRUE;
    }

    public List<PaymentJob> getAll(String fiscalCode) {
        return paymentJobRepository.findByFiscalCodeOrderByInsertDateDesc(fiscalCode);
    }

    public void uploadRows(UploadCsvModel uploadCsvModel) {
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        jmsTemplate.convertAndSend(queueName, uploadCsvModel);
    }

    public Long savePaymentJob(PaymentJob paymentJob) {
        PaymentJob paymentJobNew = paymentJobRepository.saveAndFlush(paymentJob);
        return paymentJobNew.getJobId();
    }

    public long countByFiscalCodeAndStatusNot(String fiscalCode, Integer status) {
        return paymentJobRepository.countByFiscalCodeAndStatusNot(fiscalCode, status);
    }

    public Optional<PaymentJob> getJob(Long jobId) {
        return paymentJobRepository.findById(jobId);
    }

}
