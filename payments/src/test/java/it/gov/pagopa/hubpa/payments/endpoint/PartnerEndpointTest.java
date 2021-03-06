package it.gov.pagopa.hubpa.payments.endpoint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.gov.pagopa.hubpa.payments.endpoints.PartnerEndpoint;
import it.gov.pagopa.hubpa.payments.mock.PaGetPaymentReqMock;
import it.gov.pagopa.hubpa.payments.mock.PaGetPaymentResMock;
import it.gov.pagopa.hubpa.payments.mock.PaSendRTReqMock;
import it.gov.pagopa.hubpa.payments.mock.PaSendRTResMock;
import it.gov.pagopa.hubpa.payments.mock.PaVerifyPaymentNoticeReqMock;
import it.gov.pagopa.hubpa.payments.mock.PaVerifyPaymentNoticeResMock;

import it.gov.pagopa.hubpa.payments.model.partner.ObjectFactory;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeRes;
import it.gov.pagopa.hubpa.payments.service.PartnerService;

@ExtendWith(MockitoExtension.class)
class PartnerEndpointTest {

  @InjectMocks
  private PartnerEndpoint partnerEndpoint;

  @Mock
  private PartnerService partnerService;

  @Mock
  private ObjectFactory factory;

  private ObjectFactory factoryUtil = new ObjectFactory();

  @Test
  void paVerifyPaymentNoticeTest() throws DatatypeConfigurationException {

    // Test preconditions
    PaVerifyPaymentNoticeReq requestBody = PaVerifyPaymentNoticeReqMock.getMock();
    PaVerifyPaymentNoticeRes responseBody = PaVerifyPaymentNoticeResMock.getMock();
    JAXBElement<PaVerifyPaymentNoticeReq> request = factoryUtil.createPaVerifyPaymentNoticeReq(requestBody);

    when(partnerService.paVerifyPaymentNotice(requestBody)).thenReturn(responseBody);
    when(factory.createPaVerifyPaymentNoticeRes(responseBody))
        .thenReturn(factoryUtil.createPaVerifyPaymentNoticeRes(responseBody));

    // Test execution
    JAXBElement<PaVerifyPaymentNoticeRes> response = partnerEndpoint.paVerifyPaymentNotice(request);

    // Test postcondiction
    assertThat(response.getValue()).isEqualTo(responseBody);
  }

  @Test
  void paGetPaymentTest() {

    // Test preconditions
    PaGetPaymentReq requestBody = PaGetPaymentReqMock.getMock();
    PaGetPaymentRes responseBody = PaGetPaymentResMock.getMock();
    JAXBElement<PaGetPaymentReq> request = factoryUtil.createPaGetPaymentReq(requestBody);

    when(partnerService.paGetPayment(requestBody)).thenReturn(responseBody);
    when(factory.createPaGetPaymentRes(responseBody)).thenReturn(factoryUtil.createPaGetPaymentRes(responseBody));

    // Test execution
    JAXBElement<PaGetPaymentRes> response = partnerEndpoint.paGetPayment(request);

    // Test postcondiction
    assertThat(response.getValue()).isEqualTo(responseBody);
  }

  @Test
  void paSendRTTest() {

    // Test preconditions
    PaSendRTReq requestBody = PaSendRTReqMock.getMock();
    PaSendRTRes responseBody = PaSendRTResMock.getMock();
    JAXBElement<PaSendRTReq> request = factoryUtil.createPaSendRTReq(requestBody);

    when(partnerService.paSendRT(requestBody)).thenReturn(responseBody);
    when(factory.createPaSendRTRes(responseBody)).thenReturn(factoryUtil.createPaSendRTRes(responseBody));

    // Test execution
    JAXBElement<PaSendRTRes> response = partnerEndpoint.paSendRT(request);

    // Test postcondiction
    assertThat(response.getValue()).isEqualTo(responseBody);
  }

}
