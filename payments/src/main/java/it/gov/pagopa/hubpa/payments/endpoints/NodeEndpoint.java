package it.gov.pagopa.hubpa.payments.endpoints;

import javax.xml.bind.JAXBElement;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import it.gov.pagopa.hubpa.payments.model.node.ObjectFactory;
import it.gov.pagopa.hubpa.payments.model.node.PaVerifyPaymentNoticeReq;
import it.gov.pagopa.hubpa.payments.model.node.PaVerifyPaymentNoticeRes;

@Endpoint
public class NodeEndpoint {

    @PayloadRoot( localPart = "paVerifyPaymentNoticeReq", namespace = "http://pagopa-api.pagopa.gov.it/pa/paForNode.xsd")
    @ResponsePayload
    public JAXBElement<PaVerifyPaymentNoticeRes> paVerifyPaymentNotice(@RequestPayload JAXBElement<PaVerifyPaymentNoticeReq> request) {
        System.out.println(" - VerifyPaymentNoticeReq -");
        ObjectFactory factory = new ObjectFactory ( );

        PaVerifyPaymentNoticeRes result = new PaVerifyPaymentNoticeRes();
        result.setCompanyName("request.getIdBrokerPSP()");
        result.setOfficeName("officeName");
        result.setFiscalCodePA("77777777777");
        result.setPaymentDescription("payment");
        result.setOfficeName("officeName");

        return factory.createPaVerifyPaymentNoticeRes(result);
    }
}
