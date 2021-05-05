package it.gov.pagopa.hubpa.api.ente;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.http.HttpStatus;

@RestController
public class EnteCreditoreController {

  @Autowired
  private EnteCreditoreService enteCreditoreService;

  @Autowired
  private ModelMapper modelMapper;

  private Logger logger = LoggerFactory.getLogger(EnteCreditoreController.class);

  @ApiOperation(value = "Recupera i dati minimali di un Ente Creditore dato un codice fiscale del Ref-P", notes = "Recupera i dati minimali di un Ente Creditore dato un codice fiscale del Ref-P", response = EnteCreditoreMinimalDto.class)
  @GetMapping(value = "/ente/refp/{codiceFiscaleRefP}")
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
  @ApiOperation(value = "Crea un Ente Creditore", notes = "Crea un Ente Creditore", response = EnteCreditoreDto.class)
  public EnteCreditoreDto createEcPost(@ApiParam(value = "Modello dell'Ente Creditore", required = true) @Valid @RequestBody EnteCreditoreDto ecDto) {
    EnteCreditoreEntity ecCreated = enteCreditoreService.create(modelMapper.map(ecDto, EnteCreditoreEntity.class));

    return modelMapper.map(ecCreated, EnteCreditoreDto.class);
  }

}