package it.gov.pagopa.hubpa.payments.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;

@Repository
public interface PaymentPositionRepository extends JpaRepository<PaymentPosition, Long>, JpaSpecificationExecutor<PaymentPosition> {

    List<PaymentPosition> findAllByJobId(Long jobId);

}
