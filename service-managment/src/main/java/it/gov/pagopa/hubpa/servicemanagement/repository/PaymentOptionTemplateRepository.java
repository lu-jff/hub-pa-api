package it.gov.pagopa.hubpa.servicemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.gov.pagopa.hubpa.servicemanagement.entity.PaymentOptionTemplate;

@Repository
public interface PaymentOptionTemplateRepository extends JpaRepository<PaymentOptionTemplate, Long> {
    List<PaymentOptionTemplate> findByServiceId(Long questionId);
}
