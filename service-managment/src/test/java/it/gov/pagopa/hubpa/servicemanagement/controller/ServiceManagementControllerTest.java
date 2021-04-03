package it.gov.pagopa.hubpa.servicemanagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import javax.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.gov.pagopa.hubpa.servicemanagement.mock.TributeServiceModelMock;
import it.gov.pagopa.hubpa.servicemanagement.model.ServiceConfiguratedModel;
import it.gov.pagopa.hubpa.servicemanagement.model.TributeServiceModel;
import it.gov.pagopa.hubpa.servicemanagement.service.ServiceManagementService;

@ExtendWith(MockitoExtension.class)
class ServiceManagementControllerTest {

    @InjectMocks
    private ServiceManagementController serviceManagementController;

    @Mock
    private ServiceManagementService serviceManagementService;

    @Test
    void isServiceConfiguratedTest() throws ServletException {
	when(serviceManagementService.isServiceConfigurated(any(Long.class))).thenReturn(Boolean.TRUE);
	ServiceConfiguratedModel serviceConfiguratedModel = serviceManagementController.isServiceConfigurated(1L);
	assertThat(serviceConfiguratedModel.getResult()).isTrue();
    }

    @Test
    void getServiceTest() throws ServletException {
	TributeServiceModel modelMock = TributeServiceModelMock.getMock();
	when(serviceManagementService.getService(any(Long.class))).thenReturn(modelMock);
	TributeServiceModel tributeServiceModel = serviceManagementController.getService(5L);
	assertThat(tributeServiceModel.getDenomination()).isEqualTo("TariTefa2021");
    }

    @Test
    void saveServiceTest() throws ServletException {
	when(serviceManagementService.saveService(any(TributeServiceModel.class))).thenReturn(Boolean.TRUE);
	ServiceConfiguratedModel serviceConfiguratedModel = serviceManagementController
		.saveService(new TributeServiceModel());
	assertThat(serviceConfiguratedModel.getResult()).isTrue();
    }

}
