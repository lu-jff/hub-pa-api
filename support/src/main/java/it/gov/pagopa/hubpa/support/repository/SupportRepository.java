package it.gov.pagopa.hubpa.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.gov.pagopa.hubpa.support.entity.Support;

@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {

}
