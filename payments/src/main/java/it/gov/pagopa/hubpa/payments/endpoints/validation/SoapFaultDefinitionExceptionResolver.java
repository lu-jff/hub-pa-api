package it.gov.pagopa.hubpa.payments.endpoints.validation;

import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointExceptionResolver;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SoapFaultDefinitionExceptionResolver implements EndpointExceptionResolver {

    public boolean resolveException(MessageContext messageContext, Object endpoint, Exception ex) {

        if (ex instanceof SoapValidationException) {
            throw (SoapValidationException) ex;
        }
        return false;
    }
}
