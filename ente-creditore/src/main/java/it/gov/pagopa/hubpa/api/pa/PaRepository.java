package it.gov.pagopa.hubpa.api.pa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

@Repository
public interface PaRepository extends JpaRepository<PaEntity, Long> {
  @Query(value = "SELECT pa FROM PaEntity pa WHERE pa.tipologiaIstat IN :tipologieIstat")
  List<PaEntity> findPaForTefa(@Param("tipologieIstat") List<String> tipologieIstat);
  
  PaEntity findByCodiceFiscale(String fiscalCode);
}