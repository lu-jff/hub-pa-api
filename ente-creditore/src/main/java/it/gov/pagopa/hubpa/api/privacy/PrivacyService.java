package it.gov.pagopa.hubpa.api.privacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivacyService {

  @Autowired
  private PrivacyRepository privacyRepository;

  public long countByRefP(String codiceFiscaleRefP) {
    return privacyRepository.countByCodiceFiscaleRefP(codiceFiscaleRefP);
  }

  public PrivacyEntity create(PrivacyEntity ecEntity) {
    return privacyRepository.save(ecEntity);

  }

}