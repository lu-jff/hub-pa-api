package it.italia.developers.spid.spidspringrest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import it.gov.pagopa.hubpa.authservice.controller.SpidSpringRestController;
import it.gov.pagopa.hubpa.authservice.model.SpidProviders;
import it.gov.pagopa.hubpa.authservice.service.TokenJwtService;
import it.gov.pagopa.hubpa.spid.integration.exception.IntegrationServiceException;
import it.gov.pagopa.hubpa.spid.integration.model.AuthRequest;
import it.gov.pagopa.hubpa.spid.integration.model.IdpEntry;
import it.gov.pagopa.hubpa.spid.integration.model.ResponseDecoded;
import it.gov.pagopa.hubpa.spid.integration.service.SPIDIntegrationService;

@ExtendWith(MockitoExtension.class)
class SpidSpringRestControllerIT {

    @InjectMocks
    SpidSpringRestController spidSpringRestController;

    @Mock
    private SPIDIntegrationService spidIntegrationService;

    @Mock
    private TokenJwtService tokenJwtService;

    @Test
    void listProvidersTest() throws ServletException, IntegrationServiceException {
	
	List<IdpEntry> lista=new ArrayList<>();
	IdpEntry idpEntry=new IdpEntry();
	idpEntry.setEntityId("entityIdTest");
	lista.add(idpEntry);

	when(spidIntegrationService.getAllIdpEntry()).thenReturn(lista);

	SpidProviders spidProviders = spidSpringRestController.listIdProviders();

	assertThat(spidProviders.getExtraInfo().get(0).getTitle()).isEqualTo("Maggiori informazioni");
	assertThat(spidProviders.getIdentityProviders().get(0).getEntityId()).isEqualTo("entityIdTest");
    }
    @Test
    void authRequestTest() throws ServletException, IntegrationServiceException {

	ReflectionTestUtils.setField(spidSpringRestController, "assertionConsumerServiceIndex", 0);
	
	AuthRequest aa = new AuthRequest();
	aa.setDestinationUrl("destinationUrlTest");
	
	when(spidIntegrationService.buildAuthenticationRequest(any(String.class),any(Integer.class))).thenReturn(aa);

	AuthRequest spidProviders = spidSpringRestController.authRequest("test");

	assertThat(spidProviders.getDestinationUrl()).isEqualTo("destinationUrlTest");
    }
    @Test
    void decodeResponseTest() throws ServletException, IntegrationServiceException {

	MockHttpServletRequest request = new MockHttpServletRequest();
	MockHttpServletResponse response = new MockHttpServletResponse();

	ResponseDecoded rDec = new ResponseDecoded();
	rDec.setCodiceIdentificativo("codiceIdentificativo");
	when(spidIntegrationService.processAuthenticationResponse(any(HttpServletRequest.class),
		any(HttpServletResponse.class))).thenReturn(rDec);
	when(tokenJwtService.generateToken(any(String.class))).thenReturn("token123");
	// AuthRequest authRequest

	ResponseDecoded responseEntity = spidSpringRestController.decodeResponse("sasas", request, response);

	assertThat(responseEntity.getCodiceIdentificativo()).isEqualTo("codiceIdentificativo");
    }
    @Test
    void decodeResponseExceptionTest() throws ServletException, IntegrationServiceException
    {
	MockHttpServletRequest request = new MockHttpServletRequest();
	MockHttpServletResponse response = new MockHttpServletResponse();
	
	when(spidIntegrationService.processAuthenticationResponse(any(HttpServletRequest.class),
		any(HttpServletResponse.class))).thenThrow(NullPointerException.class);
	assertThatCode(() -> {
	     spidSpringRestController.decodeResponse("sasas", request, response);
	}).hasCauseExactlyInstanceOf(NullPointerException.class);
	
    }
}
