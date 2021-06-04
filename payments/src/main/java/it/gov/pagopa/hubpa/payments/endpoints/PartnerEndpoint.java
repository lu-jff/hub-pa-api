package it.gov.pagopa.hubpa.payments.endpoints;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import it.gov.pagopa.hubpa.payments.model.partner.ObjectFactory;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeRes;

@Endpoint
public class PartnerEndpoint {

    private Logger logger = LoggerFactory.getLogger(PartnerEndpoint.class);

    @PayloadRoot(localPart = "paVerifyPaymentNoticeReq", namespace = "http://pagopa-api.pagopa.gov.it/pa/paForNode.xsd")
    @ResponsePayload
    public JAXBElement<PaVerifyPaymentNoticeRes> paVerifyPaymentNotice(
            @RequestPayload JAXBElement<PaVerifyPaymentNoticeReq> request) {

        logger.info(" paVerifyPaymentNotice START ");

        // mock response
        ObjectFactory factory = new ObjectFactory();
        PaVerifyPaymentNoticeRes result = new PaVerifyPaymentNoticeRes();
        result.setCompanyName("request.getIdBrokerPSP()");
        result.setOfficeName("officeName");
        result.setFiscalCodePA("77777777777");
        result.setPaymentDescription("payment");
        result.setOfficeName("officeName");
        JAXBElement<PaVerifyPaymentNoticeRes> response = factory.createPaVerifyPaymentNoticeRes(result);

        logger.info(" paVerifyPaymentNotice FINISH ");
        return response;
    }
}
