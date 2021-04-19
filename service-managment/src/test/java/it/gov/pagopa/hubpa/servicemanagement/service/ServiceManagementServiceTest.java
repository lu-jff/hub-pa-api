package it.gov.pagopa.hubpa.servicemanagement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.gov.pagopa.hubpa.servicemanagement.entity.Service;
import it.gov.pagopa.hubpa.servicemanagement.mock.ServiceMock;
import it.gov.pagopa.hubpa.servicemanagement.repository.ServiceRepository;

@ExtendWith(MockitoExtension.class)
class ServiceManagementServiceTest {

    @InjectMocks
    private ServiceManagementService serviceManagementService;

    @Mock
    ServiceRepository serviceRepository;

    @Test
    void isServiceConfiguratedTest() throws ServletException {
	when(serviceRepository.countByFiscalCodePrimaryCreditor(any(String.class))).thenReturn(1L);
	Boolean result = serviceManagementService.isServiceConfigurated("12345678901");
	assertThat(result).isTrue();

    }

    @Test
    void getServiceTest() throws ServletException {
	Service serviceMock = ServiceMock.getMock();
	List<Service> serviceMockList = new ArrayList<>();
	serviceMockList.add(serviceMock);

	when(serviceRepository.findByFiscalCodePrimaryCreditor(any(String.class))).thenReturn(serviceMockList);
	Service serviceModel = serviceManagementService.getService("12345678901");
	assertThat(serviceModel.getDenomination()).isEqualTo("TariTefa2021");
    }

    @Test
    void saveServiceTest() throws ServletException {
	Service serviceMock = ServiceMock.getMock();
	when(serviceRepository.saveAndFlush(any(Service.class))).thenReturn(serviceMock);
	Boolean result = serviceManagementService.saveService(serviceMock);
	assertThat(result).isTrue();
    }
}
