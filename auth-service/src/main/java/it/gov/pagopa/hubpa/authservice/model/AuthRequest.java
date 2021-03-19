package it.gov.pagopa.hubpa.authservice.model;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;

    public AuthRequest(String email) {
	this.username = email;
    }

}
