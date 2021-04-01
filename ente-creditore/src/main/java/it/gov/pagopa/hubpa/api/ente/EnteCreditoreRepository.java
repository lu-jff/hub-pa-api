package it.gov.pagopa.hubpa.api.ente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnteCreditoreRepository extends JpaRepository<EnteCreditoreEntity, Long> {
  public EnteCreditoreEntity findEnteCreditoreByCodiceFiscaleRp(String codiceFiscaeleRefP);
}