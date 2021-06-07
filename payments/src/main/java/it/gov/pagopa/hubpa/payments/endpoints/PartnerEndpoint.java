package it.gov.pagopa.hubpa.payments.endpoints;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.addressing.server.annotation.Action;

import it.gov.pagopa.hubpa.payments.model.partner.CtPaymentPA;
import it.gov.pagopa.hubpa.payments.model.partner.ObjectFactory;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeRes;
import it.gov.pagopa.hubpa.payments.model.partner.StOutcome;

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

    @PayloadRoot(localPart = "paGetPaymentReq", namespace = "http://pagopa-api.pagopa.gov.it/pa/paForNode.xsd")
    @ResponsePayload
    public JAXBElement<PaGetPaymentRes> paGetPayment(@RequestPayload JAXBElement<PaGetPaymentReq> request) {

        logger.info(" paGetPayment START ");

        // mock response
        ObjectFactory factory = new ObjectFactory();
        PaGetPaymentRes result = new PaGetPaymentRes();

        CtPaymentPA data = new CtPaymentPA();
        data.setCompanyName("company name");
        data.setCreditorReferenceId("id");

        result.setData(data);

        JAXBElement<PaGetPaymentRes> response = factory.createPaGetPaymentRes(result);

        logger.info(" paGetPayment FINISH ");
        return response;
    }

    @PayloadRoot(localPart = "paSendRTReq", namespace = "http://pagopa-api.pagopa.gov.it/pa/paForNode.xsd")
    @ResponsePayload
    public JAXBElement<PaSendRTRes> paSendRT(@RequestPayload JAXBElement<PaSendRTReq> request) {

        logger.info(" paSendRT START ");

        // mock response
        ObjectFactory factory = new ObjectFactory();
        PaSendRTRes result = new PaSendRTRes();

        result.setOutcome(StOutcome.OK);

        JAXBElement<PaSendRTRes> response = factory.createPaSendRTRes(result);

        logger.info(" paGetPayment FINISH ");
        return response;
    }
}
