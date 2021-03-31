package it.gov.pagopa.hubpa.api.ente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class EnteCreditoreController {

  @Autowired
  private EnteCreditoreRepository enteCreditoreRepository;

  @GetMapping("/ente")
  public Page<EnteCreditore> getEnteByRefP(Pageable pageable) {
    return enteCreditoreRepository.findAll(pageable);
  }

}