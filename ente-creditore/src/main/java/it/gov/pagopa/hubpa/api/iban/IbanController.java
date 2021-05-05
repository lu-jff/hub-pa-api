package it.gov.pagopa.hubpa.api.iban;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class IbanController {

  @Autowired
  private IbanService ibanService;

  @Autowired
  private ModelMapper modelMapper;

  private Logger logger = LoggerFactory.getLogger(IbanController.class);

  @ApiOperation(value = "Recupera la lista degli IBAN di un Ente Creditore", notes = "Recupera la lista degli IBAN di un Ente Creditore", response = List.class)
  @GetMapping(value = "/ente/{codiceFiscaleEnteCreditore}/iban")
  public List<IbanOnlyDto> getIbanByEnteCreditore(@PathVariable("codiceFiscaleEnteCreditore") String codiceFiscaleEc) {
    logger.info("GET IBAN");
    List<IbanEntity> ibans = ibanService.getByEnteCreditore(codiceFiscaleEc);
    if (ibans != null) {
      return ibans.stream().map(myEntity -> modelMapper.map(myEntity, IbanOnlyDto.class)).collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }

  }

  @ApiOperation(value = "Crea un IBAN", notes = "Crea un IBAN", response = IbanDto.class)
  @PostMapping(value = "/ente/iban")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public IbanDto createIbanPost(@ApiParam(value = "Modello dell'IBAN", required = true) @Valid @RequestBody IbanDto ibanDto) {
    IbanEntity ibanCreated = ibanService.create(modelMapper.map(ibanDto, IbanEntity.class));

    return modelMapper.map(ibanCreated, IbanDto.class);
  }

}