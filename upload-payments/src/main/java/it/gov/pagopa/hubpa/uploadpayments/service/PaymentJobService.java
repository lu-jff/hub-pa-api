
package it.gov.pagopa.hubpa.uploadpayments.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.gov.pagopa.hubpa.uploadpayments.entity.PaymentJob;
import it.gov.pagopa.hubpa.uploadpayments.model.PaymentsModel;
import it.gov.pagopa.hubpa.uploadpayments.repository.PaymentJobRepository;

@Service
public class PaymentJobService {

    @Autowired
    private PaymentJobRepository paymentJobRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Value("${QUEUE_NAME}")
    private String queueName;

    public Long countByIdsandStatusNot(List<Long> jobIds, Integer status) {
	return paymentJobRepository.countByJobIdInAndStatusNot(jobIds, status);
    }

    public Boolean create(PaymentJob paymentJob) {
	paymentJobRepository.saveAndFlush(paymentJob);
	return Boolean.TRUE;
    }

    public List<PaymentJob> getAll(Long creditorId) {
	return paymentJobRepository.findByCreditorId(creditorId);
    }

    public void uploadRows(PaymentsModel paymentsModel) {
	if (!paymentsModel.getDebitors().isEmpty()) {
	    rabbitTemplate.convertAndSend(queueName, paymentsModel);
	}
    }

    public Long savePaymentJob(PaymentJob paymentJob) {
	PaymentJob paymentJobNew = paymentJobRepository.saveAndFlush(paymentJob);
	return paymentJobNew.getJobId();
    }

    public long countByCreditorIdAndStatusNot(Long creditorId, Integer status) {
	return paymentJobRepository.countByCreditorIdAndStatusNot(creditorId, status);
    }

}
