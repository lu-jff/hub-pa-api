package it.gov.pagopa.hubpa.api.iban;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IbanRepository extends JpaRepository<IbanEntity, Long> {
  public List<IbanEntity> findIbanByCodiceFiscale(String codiceFiscale);
}
