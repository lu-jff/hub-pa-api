package it.gov.pagopa.hubpa.api.ente;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class EnteCreditoreController {

  @Autowired
  private EnteCreditoreService enteCreditoreService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private RestTemplate restTemplate;

  @Value("${auth.introspect.path}")
  private String introspectPath;

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

  @ApiOperation(value = "Verifica se il Ref-P è autorizzato", notes = "Verifica se il Ref-P è autorizzato", response = EnteCreditoreRbacStatus.class)
  @GetMapping(value = "/rbac")
  public ResponseEntity<EnteCreditoreRbacStatus> rbac(@RequestHeader(name = "Authorization") String bearerToken) {
    logger.info("Role based access control");

    // Extract Token
    String token = bearerToken.substring(7);

    // Build introspection request payload
    IntrospectReq myIntrospectReq = new IntrospectReq();
    myIntrospectReq.setToken(token);

    // Set introspection request headers
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);

    try {
      HttpEntity<IntrospectReq> entity = new HttpEntity<>(myIntrospectReq, headers);

      ResponseEntity<RefpIntrospect> myRefp = restTemplate.exchange(introspectPath, HttpMethod.POST, entity,
          RefpIntrospect.class);
      String fiscalNumber = Optional.of(myRefp.getBody()).get().getUser().getFiscal_number();
      EnteCreditoreEntity ecE = enteCreditoreService.getByRefP(fiscalNumber);

      if (Boolean.FALSE.equals(ecE.getAllowed())) {
        throw new RestClientException("Allowed is false, Not Authorized");
      }

      return new ResponseEntity<>(modelMapper.map(ecE, EnteCreditoreRbacStatus.class), HttpStatus.OK);
    } catch (Exception ex) {
      logger.info(ex.getMessage());
      EnteCreditoreRbacStatus myRes = new EnteCreditoreRbacStatus();
      myRes.setAllowed(false);
      return new ResponseEntity<>(myRes, HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping(value = "ente")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  @ApiOperation(value = "Crea un Ente Creditore", notes = "Crea un Ente Creditore", response = EnteCreditoreDto.class)
  public EnteCreditoreDto createEcPost(
      @ApiParam(value = "Modello dell'Ente Creditore", required = true) @Valid @RequestBody EnteCreditoreDto ecDto) {
    EnteCreditoreEntity ecCreated = enteCreditoreService.create(modelMapper.map(ecDto, EnteCreditoreEntity.class));

    return modelMapper.map(ecCreated, EnteCreditoreDto.class);
  }

}