
package it.gov.pagopa.hubpa.uploadpayments.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gov.pagopa.hubpa.uploadpayments.entity.PaymentJob;
import it.gov.pagopa.hubpa.uploadpayments.repository.PaymentJobRepository;

@Service
public class PaymentJobService {

    @Autowired
    private PaymentJobRepository paymentJobRepository;

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

}