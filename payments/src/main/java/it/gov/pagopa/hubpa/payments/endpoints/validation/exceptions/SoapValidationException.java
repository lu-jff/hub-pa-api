package it.gov.pagopa.hubpa.payments.endpoints.validation.exceptions;

import it.gov.pagopa.hubpa.payments.model.PaaErrorEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoapValidationException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    private PaaErrorEnum faultCode;
    private String description;
    private String faultString;

    public SoapValidationException(PaaErrorEnum faultCode, String faultString, String description) {
        super(description);
        this.setFaultCode(faultCode);
        this.setDescription(description);
        this.setFaultString(faultString);
    }
}
