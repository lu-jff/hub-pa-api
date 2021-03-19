package it.gov.pagopa.hubpa.authservice.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import javax.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import it.gov.pagopa.hubpa.authservice.model.AuthRequest;
import it.gov.pagopa.hubpa.spid.integration.exception.IntegrationServiceException;

@ExtendWith(MockitoExtension.class)
class TokenJwtServiceTest {

    @InjectMocks
    private TokenJwtService tokenJwtService;

    @Test
    void allTest() throws ServletException, IntegrationServiceException {
	ReflectionTestUtils.setField(tokenJwtService, "jwtSecret", "secret");
	
	String emialtest="emailTest";
	String token=tokenJwtService.buildTokenKey(emialtest);
	assertThat(token).isEqualTo(emialtest);
	AuthRequest authReq = tokenJwtService.buildAuthRequestFromTokenKey(token);
	assertThat(authReq.getUsername()).isEqualTo(emialtest);
	String newToken=tokenJwtService.generateToken(emialtest);
	assertThat(newToken).isNotEmpty();
	Map<String, String> tokenMap = tokenJwtService.getTokenMap();
	assertThat(tokenMap.get(emialtest)).contains(newToken);
	tokenJwtService.setTokenMap(null);
	assertThat(tokenJwtService.getTokenMap()).isNull();
    }
    
}
