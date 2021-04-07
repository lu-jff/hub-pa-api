package it.gov.pagopa.hubpa.api.ente;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
public class EnteCreditoreController {

  @Autowired
  private EnteCreditoreService enteCreditoreService;

  @Autowired
  private ModelMapper modelMapper;

  private Logger logger = LoggerFactory.getLogger(EnteCreditoreController.class);

  @GetMapping(value = "/ente/{codiceFiscaleRefP}")
  public EnteCreditoreMinimalDto getEnteCreditoreByRefP(@PathVariable("codiceFiscaleRefP") String codiceFiscaleRefP) {
    logger.info("GET Ente Creditore");
    EnteCreditoreEntity ecE = enteCreditoreService.getByRefP(codiceFiscaleRefP);
    if (ecE != null) {
      return modelMapper.map(ecE, EnteCreditoreMinimalDto.class);
    } else {
      return null;
    }

  }

  @PostMapping(value = "ente")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public EnteCreditoreDto createEcPost(@RequestBody EnteCreditoreDto ecDto) {
    EnteCreditoreEntity ecCreated = enteCreditoreService.create(modelMapper.map(ecDto, EnteCreditoreEntity.class));

    return modelMapper.map(ecCreated, EnteCreditoreDto.class);
  }

}