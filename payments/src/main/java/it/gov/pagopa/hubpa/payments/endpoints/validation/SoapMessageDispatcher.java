package it.gov.pagopa.hubpa.payments.endpoints.validation;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import it.gov.pagopa.hubpa.payments.endpoints.validation.exceptions.SoapValidationException;
import it.gov.pagopa.hubpa.payments.model.PaaErrorEnum;
import it.gov.pagopa.hubpa.payments.model.partner.CtFaultBean;
import it.gov.pagopa.hubpa.payments.model.partner.CtResponse;
import it.gov.pagopa.hubpa.payments.model.partner.ObjectFactory;
import it.gov.pagopa.hubpa.payments.model.partner.StOutcome;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SoapMessageDispatcher extends MessageDispatcherServlet {

    private static final long serialVersionUID = 2735436671084580797L;

    @Autowired
    private ObjectFactory factory;

    @Override
    protected void doService(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {

        String fultCode = null;
        String faultString = null;
        String description = null;

        try {

            super.doService(httpServletRequest, httpServletResponse);
        } catch (SoapValidationException e) {

            log.error("Processing resulted in exception: " + e.getMessage());
            fultCode = e.getFaultCode().getValue();
            faultString = e.getFaultString();
            description = e.getDescription();
            httpServletResponse.setStatus(200);
        } catch (Exception e) {

            log.error("Processing resulted in generic exception: " + e.getMessage());
            fultCode = PaaErrorEnum.PAA_SEMANTICA.getValue();
            faultString = "Generic Error";
            httpServletResponse.setStatus(500);
        }

        if (fultCode != null) {

            CtResponse response = factory.createCtResponse();
            CtFaultBean faultBean = factory.createCtFaultBean();
            faultBean.setDescription(description);
            faultBean.setFaultCode(fultCode);
            faultBean.setFaultString(faultString);
            response.setOutcome(StOutcome.KO);
            response.setFault(faultBean);

            try {

                ServletOutputStream outputStream = httpServletResponse.getOutputStream();
                httpServletResponse.setContentType("text/xml");
                JAXB.marshal(response, outputStream);
                outputStream.flush();

            } catch (IOException e) {

                httpServletResponse.setStatus(500);
            }

        }

    }
}
