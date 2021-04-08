package it.gov.pagopa.hubpa.api.pa;

import java.util.Arrays;
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
public class PaController {

  @Autowired
  private PaService paService;

  @Autowired
  private ModelMapper modelMapper;

  private Logger logger = LoggerFactory.getLogger(PaController.class);

  @GetMapping(value = "/ente/secondary")
  public List<PaDescriptionDto> getAllEcForTefa() {
    logger.info("GET PROVINCE E CITTA METROPOLITANE");
    List<String> tipologieIstat = Arrays.asList("Citta' Metropolitane", "Province e loro Consorzi e Associazioni");
    List<PaEntity> enti = paService.getPaForTefa(tipologieIstat);
    if (enti != null) {
      return enti.stream().map(myEntity -> modelMapper.map(myEntity, PaDescriptionDto.class))
          .collect(Collectors.toList());
    } else {
      return Collections.emptyList();
    }

  }

  @PostMapping(value = "/ente/secondary")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public PaDto createIbanPost(@RequestBody PaDto paDto) {
    PaEntity paCreated = paService.create(modelMapper.map(paDto, PaEntity.class));

    return modelMapper.map(paCreated, PaDto.class);
  }

}