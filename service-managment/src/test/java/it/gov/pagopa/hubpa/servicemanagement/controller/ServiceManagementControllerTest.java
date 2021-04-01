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
	
	/*List<IdpEntry> lista=new ArrayList<>();
	IdpEntry idpEntry=new IdpEntry();
	idpEntry.setEntityId("entityIdTest");
	lista.add(idpEntry);

	when(spidIntegrationService.getAllIdpEntry()).thenReturn(lista);

	InstallmentModel spidProviders = spidSpringRestController.listIdProviders();

	assertThat(spidProviders.getExtraInfo().get(0).getTitle()).isEqualTo("Maggiori informazioni");
	assertThat(spidProviders.getIdentityProviders().get(0).getEntityId()).isEqualTo("entityIdTest");*/
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
	ServiceConfiguratedModel serviceConfiguratedModel = serviceManagementController.saveService(new TributeServiceModel());
	assertThat(serviceConfiguratedModel.getResult()).isTrue();
    }
    //@Test
    //void authRequestTest() throws ServletException {

	/*ReflectionTestUtils.setField(spidSpringRestController, "assertionConsumerServiceIndex", 0);
	
	TributeServiceModel aa = new TributeServiceModel();
	aa.setDestinationUrl("destinationUrlTest");
	
	when(spidIntegrationService.buildAuthenticationRequest(any(String.class),any(Integer.class))).thenReturn(aa);

	TributeServiceModel spidProviders = spidSpringRestController.authRequest("test");

	assertThat(spidProviders.getDestinationUrl()).isEqualTo("destinationUrlTest");
	*/
    //}

   // @Test
   // void decodeResponseExceptionTest() throws ServletException
   // {
	/*MockHttpServletRequest request = new MockHttpServletRequest();
	MockHttpServletResponse response = new MockHttpServletResponse();
	
	when(spidIntegrationService.processAuthenticationResponse(any(HttpServletRequest.class),
		any(HttpServletResponse.class))).thenThrow(NullPointerException.class);
	assertThatCode(() -> {
	     spidSpringRestController.decodeResponse("sasas", request, response);
	}).hasCauseExactlyInstanceOf(NullPointerException.class);
	*/
  //  }
}
