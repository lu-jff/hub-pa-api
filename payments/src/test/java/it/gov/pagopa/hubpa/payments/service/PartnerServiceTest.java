package it.gov.pagopa.hubpa.payments.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import it.gov.pagopa.hubpa.payments.mock.DebitorMock;
import it.gov.pagopa.hubpa.payments.mock.PaGetPaymentReqMock;
import it.gov.pagopa.hubpa.payments.mock.PaSendRTReqMock;
import it.gov.pagopa.hubpa.payments.mock.PaVerifyPaymentNoticeReqMock;

import it.gov.pagopa.hubpa.payments.model.partner.ObjectFactory;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeRes;
import it.gov.pagopa.hubpa.payments.model.partner.StOutcome;
import it.gov.pagopa.hubpa.payments.repository.DebitorRepository;
import it.gov.pagopa.hubpa.payments.repository.IncrementalIuvNumberRepository;
import it.gov.pagopa.hubpa.payments.repository.PaymentPositionRepository;

@ExtendWith(MockitoExtension.class)
class PartnerServiceTest {

  @InjectMocks
  private PartnerService partnerService;

  @Mock
  DebitorRepository debitorRepository;

  @Mock
  PaymentPositionRepository paymentPositionRepository;

  @Mock
  IncrementalIuvNumberRepository incrementalIuvNumberRepository;

  @Mock
  private ObjectFactory factory;

  private ObjectFactory factoryUtil = new ObjectFactory();

  @Test
  void paVerifyPaymentNoticeTest() {

    // Test preconditions
    PaVerifyPaymentNoticeReq requestBody = PaVerifyPaymentNoticeReqMock.getMock();

    when(factory.createCtFaultBean()).thenReturn(factoryUtil.createCtFaultBean());

    when(factory.createPaVerifyPaymentNoticeRes()).thenReturn(factoryUtil.createPaVerifyPaymentNoticeRes());

    when(paymentPositionRepository.findByNotificationCode(requestBody.getQrCode().getNoticeNumber()))
        .thenReturn(Optional.of(DebitorMock.createPaymentPositionMock()));

    // Test execution
    PaVerifyPaymentNoticeRes responseBody = partnerService.paVerifyPaymentNotice(requestBody);

    // Test postcondiction
    assertThat(responseBody.getFiscalCodePA()).isEqualTo("77777777777");
    assertThat(responseBody.getOfficeName()).isEqualTo("officeName");
    assertThat(responseBody.getPaymentDescription()).isEqualTo("payment");
  }

  @Test
  void paGetPaymentTest() {

    // Test preconditions
    PaGetPaymentReq requestBody = PaGetPaymentReqMock.getMock();

    // Test execution
    PaGetPaymentRes responseBody = partnerService.paGetPayment(requestBody);

    // Test postcondiction
    assertThat(responseBody.getData().getCompanyName()).isEqualTo("company name");
    assertThat(responseBody.getData().getCreditorReferenceId()).isEqualTo("id");
  }

  @Test
  void paSendRTTest() {

    // Test preconditions
    PaSendRTReq requestBody = PaSendRTReqMock.getMock();

    // Test execution
    PaSendRTRes responseBody = partnerService.paSendRT(requestBody);

    // Test postcondiction
    assertThat(responseBody.getOutcome()).isEqualTo(StOutcome.OK);
  }

}
