package it.gov.pagopa.hubpa.api.iban;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
public class IbanController {

  @Autowired
  private IbanService ibanService;

  @Autowired
  private ModelMapper modelMapper;

  private Logger logger = LoggerFactory.getLogger(IbanController.class);

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

  @PostMapping(value = "/ente/iban")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public IbanDto createIbanPost(@RequestBody IbanDto ibanDto) {
    IbanEntity ibanCreated = ibanService.create(modelMapper.map(ibanDto, IbanEntity.class));

    return modelMapper.map(ibanCreated, IbanDto.class);
  }

}