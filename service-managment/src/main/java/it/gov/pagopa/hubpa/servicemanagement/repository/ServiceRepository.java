package it.gov.pagopa.hubpa.servicemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.gov.pagopa.hubpa.servicemanagement.entity.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    long countByFiscalCodePrimaryCreditor(String fiscalCode);

    List<Service> findByFiscalCodePrimaryCreditor(String fiscalCode);
}
