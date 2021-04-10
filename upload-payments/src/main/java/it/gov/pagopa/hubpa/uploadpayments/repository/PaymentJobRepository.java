package it.gov.pagopa.hubpa.uploadpayments.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.gov.pagopa.hubpa.uploadpayments.entity.PaymentJob;

@Repository
public interface PaymentJobRepository extends JpaRepository<PaymentJob, Long> {
    long countByJobIdInAndStatusNot(List<Long> jobIds,Integer status);
    List<PaymentJob> findByCreditorId(Long creditorId);

}
