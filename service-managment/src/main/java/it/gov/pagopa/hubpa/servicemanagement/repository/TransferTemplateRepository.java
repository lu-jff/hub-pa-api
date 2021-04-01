package it.gov.pagopa.hubpa.servicemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.gov.pagopa.hubpa.servicemanagement.entity.TransferTemplate;

@Repository
public interface TransferTemplateRepository extends JpaRepository<TransferTemplate, Long> {

}
