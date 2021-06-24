package it.gov.pagopa.hubpa.payments.endpoints.validation.exceptions;

import it.gov.pagopa.hubpa.payments.model.PaaErrorEnum;
import lombok.Getter;

@Getter
public class SoapValidationException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    private final PaaErrorEnum faultCode;
    private final String description;
    private final String faultString;

    public SoapValidationException(PaaErrorEnum faultCode, String faultString, String description) {
        super(description);
        this.faultCode = faultCode;
        this.description = description;
        this.faultString = faultString;
    }
}
