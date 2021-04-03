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

    public Boolean isServiceConfigurated(Long creditorId) {
	long countConfiguration = serviceRepository.countByCreditorId(creditorId);
	return countConfiguration > 0;
    }

    public it.gov.pagopa.hubpa.servicemanagement.entity.Service getService(Long id) {
	List<it.gov.pagopa.hubpa.servicemanagement.entity.Service> services = serviceRepository.findByCreditorId(id);
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
