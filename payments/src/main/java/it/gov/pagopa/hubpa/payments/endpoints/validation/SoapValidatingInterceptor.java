package it.gov.pagopa.hubpa.payments.endpoints.validation;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.xml.transform.TransformerException;

import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.xml.sax.SAXParseException;

import it.gov.pagopa.hubpa.payments.endpoints.validation.exceptions.SoapValidationException;
import it.gov.pagopa.hubpa.payments.model.PaaErrorEnum;

public class SoapValidatingInterceptor extends PayloadValidatingInterceptor {

    @Override
    protected boolean handleRequestValidationErrors(MessageContext messageContext, SAXParseException[] errors)
            throws TransformerException {

        if (errors.length > 0) {
            String validationErrorsString = Arrays.stream(errors).map(
                    error -> "[" + error.getLineNumber() + "," + error.getColumnNumber() + "]: " + error.getMessage())
                    .collect(Collectors.joining(" -- "));
            throw new SoapValidationException(PaaErrorEnum.PAA_SINTASSI_XSD, "Xsd Validation Error",
                    validationErrorsString);
        }
        return true;
    }
}
