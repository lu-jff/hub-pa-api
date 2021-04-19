package it.gov.pagopa.hubpa.servicemanagement.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.gov.pagopa.hubpa.servicemanagement.entity.Service;
import it.gov.pagopa.hubpa.servicemanagement.model.ServiceConfiguratedModel;
import it.gov.pagopa.hubpa.servicemanagement.model.TributeServiceModel;
import it.gov.pagopa.hubpa.servicemanagement.service.ServiceManagementService;
import it.gov.pagopa.hubpa.servicemanagement.validator.ServiceManagementValidator;

@RestController()
@RequestMapping("service-management")
public class ServiceManagementController {

    @Autowired
    private ServiceManagementService serviceManagementService;

    @Autowired
    private ServiceManagementValidator serviceManagementValidator;
    
    @Autowired
    ModelMapper modelMapper;

    @InitBinder("tributeServiceModel")
    private void initBindermembershipRequestValidator(WebDataBinder binder) {
	binder.setValidator(serviceManagementValidator);
    }

    @ApiOperation(value = "Indica se è presente un tributo", notes = "Servizio REST per ottenere l'informazione della presenza di un tributo configurato", response = ServiceConfiguratedModel.class)
    @GetMapping(value = "service/info/{fiscalCode}")
    public ServiceConfiguratedModel isServiceConfigurated(@PathVariable("fiscalCode") String fiscalCode) {
	return new ServiceConfiguratedModel(serviceManagementService.isServiceConfigurated(fiscalCode));
    }

    @ApiOperation(value = "Salva il tributo", notes = "Servizio REST per creare un tributo", response = ServiceConfiguratedModel.class)
    @PostMapping(value = "service", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ServiceConfiguratedModel saveService(@Valid
	    @ApiParam(value = "Modello del tributo", required = true) @RequestBody final TributeServiceModel tributeServiceModel) {
	it.gov.pagopa.hubpa.servicemanagement.entity.Service service = modelMapper.map(tributeServiceModel,
		it.gov.pagopa.hubpa.servicemanagement.entity.Service.class);
	return new ServiceConfiguratedModel(serviceManagementService.saveService(service));
    }

    @ApiOperation(value = "Recupera i dati del tributo", notes = "Servizio REST per recuperare i dati di un tributo", response = TributeServiceModel.class)
    @GetMapping(value = "service/{fiscalCode}")
    public TributeServiceModel getService(@PathVariable("fiscalCode") String fiscalCode) {
	Service service=serviceManagementService.getService(fiscalCode);
	TributeServiceModel tributeServiceModel=null;
	if(service!=null) {
	    tributeServiceModel = modelMapper.map(service, TributeServiceModel.class);
	}
	return tributeServiceModel;
    }

}
