package it.gov.pagopa.hubpa.servicemanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.gov.pagopa.hubpa.servicemanagement.repository.ServiceRepository;

@Service
public class ServiceManagementService {
    @Autowired
    ServiceRepository serviceRepository;

    public Boolean isServiceConfigurated(String fiscalCode) {
	long countConfiguration = serviceRepository.countByFiscalCodePrimaryCreditor(fiscalCode);
	return countConfiguration > 0;
    }

    public it.gov.pagopa.hubpa.servicemanagement.entity.Service getService(String fiscalCode) {
	List<it.gov.pagopa.hubpa.servicemanagement.entity.Service> services = serviceRepository.findByFiscalCodePrimaryCreditor(fiscalCode);
	it.gov.pagopa.hubpa.servicemanagement.entity.Service service = null;

	if (!services.isEmpty()) {
	    service = services.get(0);
	}

	return service;
    }

    @Transactional
    public Boolean saveService(it.gov.pagopa.hubpa.servicemanagement.entity.Service service) {

	serviceRepository.saveAndFlush(service);

	return true;
    }
}
