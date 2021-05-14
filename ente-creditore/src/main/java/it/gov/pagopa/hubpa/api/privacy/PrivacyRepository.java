package it.gov.pagopa.hubpa.api.privacy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivacyRepository extends JpaRepository<PrivacyEntity, Long> {
  public long countByCodiceFiscaleRefP(String codiceFiscaleRefP);
}