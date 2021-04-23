package it.gov.pagopa.hubpa.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;

@Repository
public interface PaymentPositionRepository extends JpaRepository<PaymentPosition, Long> {

}
