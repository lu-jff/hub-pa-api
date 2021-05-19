package it.gov.pagopa.hubpa.support.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.gov.pagopa.hubpa.support.entity.Support;
import it.gov.pagopa.hubpa.support.model.BooleanResponseModel;
import it.gov.pagopa.hubpa.support.model.SupportModel;
import it.gov.pagopa.hubpa.support.service.SupportService;

@RestController()
@RequestMapping("support")
public class SupportController {

    @Autowired
    private SupportService serviceManagementService;

    @Autowired
    ModelMapper modelMapper;
    
    private Logger logger = LoggerFactory.getLogger(SupportController.class);

    @ApiOperation(value = "Salva la richiesta di supporto", notes = "Servizio REST per salvare la richiesta di supporto", response = BooleanResponseModel.class)
    @PostMapping(value = "send", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public BooleanResponseModel saveSupport(
	    @ApiParam(value = "Modello del supporto", required = true) @RequestBody final SupportModel supportModel) {
	logger.info("POST Save Support");
	it.gov.pagopa.hubpa.support.entity.Support service = modelMapper.map(supportModel, Support.class);
	return new BooleanResponseModel(serviceManagementService.save(service));
    }

}
