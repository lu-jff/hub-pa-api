package it.gov.pagopa.hubpa.payments.endpoints;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.addressing.server.annotation.Action;

import it.gov.pagopa.hubpa.payments.model.partner.ObjectFactory;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeRes;
import it.gov.pagopa.hubpa.payments.service.PartnerService;
import lombok.extern.slf4j.Slf4j;

@Endpoint
@Slf4j
public class PartnerEndpoint {

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private ObjectFactory factory;

    @Action("http://pagopa-api.pagopa.gov.it/service/pa/paForNode/paVerifyPaymentNoticeReq")
    @PayloadRoot(localPart = "paVerifyPaymentNoticeReq", namespace = "http://pagopa-api.pagopa.gov.it/pa/paForNode.xsd")
    @ResponsePayload
    public JAXBElement<PaVerifyPaymentNoticeRes> paVerifyPaymentNotice(
            @RequestPayload JAXBElement<PaVerifyPaymentNoticeReq> request) {

        log.info(" paVerifyPaymentNotice START ");
        return factory.createPaVerifyPaymentNoticeRes(partnerService.paVerifyPaymentNotice(request.getValue()));
    }

    @Action("http://pagopa-api.pagopa.gov.it/service/pa/paForNode/paGetPaymentReq")
    @PayloadRoot(localPart = "paGetPaymentReq", namespace = "http://pagopa-api.pagopa.gov.it/pa/paForNode.xsd")
    @ResponsePayload
    public JAXBElement<PaGetPaymentRes> paGetPayment(@RequestPayload JAXBElement<PaGetPaymentReq> request) {

        log.info(" paGetPayment START ");
        return factory.createPaGetPaymentRes(partnerService.paGetPayment(request.getValue()));
    }

    @Action("http://pagopa-api.pagopa.gov.it/service/pa/paForNode/paSendRTReq")
    @PayloadRoot(localPart = "paSendRTReq", namespace = "http://pagopa-api.pagopa.gov.it/pa/paForNode.xsd")
    @ResponsePayload
    public JAXBElement<PaSendRTRes> paSendRT(@RequestPayload JAXBElement<PaSendRTReq> request) {

        log.info(" paSendRT START ");
        return factory.createPaSendRTRes(partnerService.paSendRT(request.getValue()));
    }
}
