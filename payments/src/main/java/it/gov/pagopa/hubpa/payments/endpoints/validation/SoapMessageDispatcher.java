package it.gov.pagopa.hubpa.payments.endpoints.validation;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapMessageCreationException;
import org.springframework.ws.soap.SoapMessageException;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import it.gov.pagopa.hubpa.payments.model.partner.CtFaultBean;
import it.gov.pagopa.hubpa.payments.model.partner.CtResponse;
import it.gov.pagopa.hubpa.payments.model.partner.ObjectFactory;
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

        String fultCode = "PAA_SEMANTICA", faultDescription = "Generic Error";

        try {

            super.doService(httpServletRequest, httpServletResponse);
        } catch (SoapValidationException e) {

            log.error("Processing resulted in exception: " + e.getMessage());
            fultCode = "PAA_SINTASSI_XSD";
            faultDescription = "Structure error in request: " + e.getMessage();
            httpServletResponse.setStatus(400);
        } catch (Exception e) {

            log.error("Processing resulted in generic exception: " + e.getMessage());
            fultCode = "PAA_SEMANTICA";
            faultDescription = "Generic Error";
            httpServletResponse.setStatus(500);
        }

        if (!(httpServletResponse.getStatus() >= 200 && httpServletResponse.getStatus() <= 299)) {

            CtResponse response = factory.createCtResponse();
            CtFaultBean faultBean = factory.createCtFaultBean();
            faultBean.setDescription(faultDescription);
            faultBean.setFaultCode(fultCode);
            response.setFault(faultBean);

            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            httpServletResponse.setContentType("text/xml");
            JAXB.marshal(response, outputStream);
            outputStream.flush();
        }

    }
}
