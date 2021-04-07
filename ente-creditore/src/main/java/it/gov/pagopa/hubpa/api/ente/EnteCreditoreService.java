package it.gov.pagopa.hubpa.api.ente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnteCreditoreService {

  @Autowired
  private EnteCreditoreRepository enteCreditoreRepository;

  public EnteCreditoreEntity getByRefP(String codiceFiscaleRefP) {
    return enteCreditoreRepository.findEnteCreditoreByCodiceFiscaleRp(codiceFiscaleRefP);
  }

  public EnteCreditoreEntity create(EnteCreditoreEntity ecEntity) {
    return enteCreditoreRepository.save(ecEntity);

  }

}