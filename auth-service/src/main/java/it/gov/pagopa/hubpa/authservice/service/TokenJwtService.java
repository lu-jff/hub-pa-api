package it.gov.pagopa.hubpa.authservice.service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.gov.pagopa.hubpa.authservice.model.AuthRequest;

@Service
public class TokenJwtService {

    @Value("${jwttoken.secret}")
    private String jwtSecret;
    
    private Map<String, String> tokenMap = new HashMap<>();

    @Value("${jwttoken.timeout:15}")
    private int tokenTimeout;

    public String buildTokenKey(String email) {
	return email;
    }

    public AuthRequest buildAuthRequestFromTokenKey(String tokenKey) {
	return new AuthRequest(tokenKey);
    }

    public String generateToken(String tokenKey) {

	Map<String, Object> claims = new HashMap<>();

	String token = createToken(claims, tokenKey);
	tokenMap.put(tokenKey, token);
	return token;
    }

    private String createToken(Map<String, Object> claims, String subject) {
	Date iatDate = Date.from(Instant.now());
	Date expDate = Date.from(Instant.now().plusMillis(TimeUnit.MINUTES.toMillis(tokenTimeout)));
	return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date.from(Instant.now()))
		.setExpiration(expDate).setIssuedAt(iatDate).signWith(SignatureAlgorithm.HS256, this.jwtSecret).compact();
    }

    public Map<String, String> getTokenMap() {
	return tokenMap;
    }

    public void setTokenMap(Map<String, String> tokenMap) {
	this.tokenMap = tokenMap;
    }

}
