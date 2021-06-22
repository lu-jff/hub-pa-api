package it.gov.pagopa.hubpa.payments.endpoints.validation;

public class SoapValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SoapValidationException(String message) {
        super(message);
    }
}

