
package it.gov.pagopa.hubpa.api.pa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaService {

  @Autowired
  private PaRepository paRepository;

  public List<PaEntity> getPaForTefa(List<String> tipologieIstat) {
    return paRepository.findPaForTefa(tipologieIstat);
  }

  public PaEntity create(PaEntity paEntity) {
    return paRepository.save(paEntity);

  }
}
