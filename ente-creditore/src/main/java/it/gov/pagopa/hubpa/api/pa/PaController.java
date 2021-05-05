package it.gov.pagopa.hubpa.api.pa;

import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class PaController {

  @Autowired
  private PaService paService;

  @Autowired
  private ModelMapper modelMapper;

  private Logger logger = LoggerFactory.getLogger(PaController.class);

  @ApiOperation(value = "Recupera la lista delle PA", notes = "Recupera la lista delle PA", response = List.class)
  @GetMapping(value = "/ente/pa")
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

  @ApiOperation(value = "Crea una PA", notes = "Crea una PA", response = PaDto.class)
  @PostMapping(value = "/ente/pa")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public PaDto createIbanPost(@ApiParam(value = "Modello della PA", required = true) @Valid  @RequestBody PaDto paDto) {
    PaEntity paCreated = paService.create(modelMapper.map(paDto, PaEntity.class));

    return modelMapper.map(paCreated, PaDto.class);
  }

}