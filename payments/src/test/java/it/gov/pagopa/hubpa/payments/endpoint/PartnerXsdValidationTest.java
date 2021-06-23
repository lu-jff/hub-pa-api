package it.gov.pagopa.hubpa.payments.endpoint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import it.gov.pagopa.hubpa.payments.PaymentsApplication;
import it.gov.pagopa.hubpa.payments.config.WebServicesConfiguration;
import it.gov.pagopa.hubpa.payments.repository.DebitorRepository;
import it.gov.pagopa.hubpa.payments.repository.IncrementalIuvNumberRepository;
import it.gov.pagopa.hubpa.payments.repository.PaymentPositionRepository;
import it.gov.pagopa.hubpa.payments.service.PartnerService;
import it.gov.pagopa.hubpa.payments.service.PaymentService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = { WebServicesConfiguration.class, PaymentsApplication.class })
class PartnerXsdValidationTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private PartnerService partnerService;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private DebitorRepository debitorRepository;

    @MockBean
    private IncrementalIuvNumberRepository incrementalIuvNumberRepository;

    @MockBean
    private PaymentPositionRepository paymentPositionRepository;

    private final static String PAA_SINTASSI_XSD = "PAA_SINTASSI_XSD";

    @Test
    void shouldGetWsdlTest() throws Exception {

        this.webClient.get().uri("/partner/partner.wsdl").exchange().expectStatus().isOk();
    }

    @Test
    void shouldXsdValiationErrorWithPaVerifyPaymentNoticeTest() throws Exception {

        String invalidRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:paf=\"http://pagopa-api.pagopa.gov.it/pa/paForNode.xsd\"><soapenv:Header/><soapenv:Body><paf:paVerifyPaymentNoticeReq><idPA>?</idPA><idBrokerPA>1</idBrokerPA><idStation>1</idStation><qrCode><fiscalCode>1</fiscalCode><noticeNumber>1</noticeNumber></qrCode></paf:paVerifyPaymentNoticeReq></soapenv:Body></soapenv:Envelope>";

        this.webClient.post().uri("/partner").header("SOAPAction", "paVerifyPaymentNotice")
                .contentType(MediaType.TEXT_XML).bodyValue(invalidRequest).exchange().expectStatus().isOk()
                .expectBody(String.class).returnResult().getResponseBody().contains(PAA_SINTASSI_XSD);
    }

    @Test
    void shouldGenericErrorWithPaVerifyPaymentNoticeTest() throws Exception {

        Mockito.when(partnerService.paVerifyPaymentNotice(Mockito.any())).thenThrow(Exception.class);

        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:paf=\"http://pagopa-api.pagopa.gov.it/pa/paForNode.xsd\"><soapenv:Header/><soapenv:Body><paf:paVerifyPaymentNoticeReq><idPA>77777777777</idPA><idBrokerPA>77777777777</idBrokerPA><idStation>77777777777</idStation><qrCode><fiscalCode>77777777777</fiscalCode><noticeNumber>311111111112222222</noticeNumber></qrCode></paf:paVerifyPaymentNoticeReq></soapenv:Body></soapenv:Envelope>";

        this.webClient.post().uri("/partner").header("SOAPAction", "paVerifyPaymentNotice")
                .contentType(MediaType.TEXT_XML).bodyValue(request).exchange().expectStatus().is5xxServerError();
    }
}
