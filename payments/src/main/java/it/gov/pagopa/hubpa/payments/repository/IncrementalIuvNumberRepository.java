package it.gov.pagopa.hubpa.payments.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import it.gov.pagopa.hubpa.payments.entity.IncrementalIuvNumber;

@Repository
public interface IncrementalIuvNumberRepository extends JpaRepository<IncrementalIuvNumber, Long> {
    
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    IncrementalIuvNumber findByIdDominioPaAndAnno(String idDomminioPa,Integer anno);
    
}
