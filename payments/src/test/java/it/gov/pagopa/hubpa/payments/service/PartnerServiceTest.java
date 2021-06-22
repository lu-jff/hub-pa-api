package it.gov.pagopa.hubpa.payments.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.xml.datatype.DatatypeConfigurationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import it.gov.pagopa.hubpa.payments.mock.DebitorMock;
import it.gov.pagopa.hubpa.payments.mock.PaGetPaymentReqMock;
import it.gov.pagopa.hubpa.payments.mock.PaSendRTReqMock;
import it.gov.pagopa.hubpa.payments.mock.PaVerifyPaymentNoticeReqMock;
import it.gov.pagopa.hubpa.payments.model.PaaErrorEnum;
import it.gov.pagopa.hubpa.payments.model.partner.ObjectFactory;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeRes;
import it.gov.pagopa.hubpa.payments.model.partner.StOutcome;
import it.gov.pagopa.hubpa.payments.model.partner.StAmountOption;
import it.gov.pagopa.hubpa.payments.repository.DebitorRepository;
import it.gov.pagopa.hubpa.payments.repository.IncrementalIuvNumberRepository;
import it.gov.pagopa.hubpa.payments.repository.PaymentOptionsRepository;
import it.gov.pagopa.hubpa.payments.repository.PaymentPositionRepository;

@ExtendWith(MockitoExtension.class)
class PartnerServiceTest {

  @InjectMocks
  private PartnerService partnerService;

  @Mock
  DebitorRepository debitorRepository;

  // @Mock
  // PaymentPositionRepository paymentPositionRepository;

  @Mock
  PaymentOptionsRepository paymentOptionRepository;

  @Mock
  IncrementalIuvNumberRepository incrementalIuvNumberRepository;

  @Mock
  private ObjectFactory factory;

  private ObjectFactory factoryUtil = new ObjectFactory();

  @Test
  void paVerifyPaymentNoticeTest() throws DatatypeConfigurationException {

    ReflectionTestUtils.setField(partnerService, "ptIdDominio", "77777777777");
    ReflectionTestUtils.setField(partnerService, "ptIdIntermediario", "77777777777");
    ReflectionTestUtils.setField(partnerService, "ptIdStazione", "77777777777_01");

    // Test preconditions
    PaVerifyPaymentNoticeReq requestBody = PaVerifyPaymentNoticeReqMock.getMock();

    when(factory.createCtFaultBean()).thenReturn(factoryUtil.createCtFaultBean());
    when(factory.createPaVerifyPaymentNoticeRes()).thenReturn(factoryUtil.createPaVerifyPaymentNoticeRes());
    when(factory.createCtPaymentOptionDescriptionPA()).thenReturn(factoryUtil.createCtPaymentOptionDescriptionPA());
    when(factory.createCtPaymentOptionsDescriptionListPA())
        .thenReturn(factoryUtil.createCtPaymentOptionsDescriptionListPA());

    when(paymentOptionRepository.findByNotificationCode(requestBody.getQrCode().getNoticeNumber()))
        .thenReturn(Optional.of(DebitorMock.createPaymentOptionsMock4()));

    // Test execution
    PaVerifyPaymentNoticeRes responseBody = partnerService.paVerifyPaymentNotice(requestBody);

    // Test postcondiction
    assertThat(responseBody.getOutcome()).isEqualTo(StOutcome.OK);
    assertThat(responseBody.getPaymentList().getPaymentOptionDescription().get(0).isAllCCP()).isEqualTo(true);
    assertThat(responseBody.getPaymentList().getPaymentOptionDescription().get(0).getAmount())
        .isEqualTo(DebitorMock.createPaymentOptionsMock4().getAmount());
    assertThat(responseBody.getPaymentList().getPaymentOptionDescription().get(0).getOptions())
        .isEqualTo(StAmountOption.EQ); // de-scoping
    assertThat(responseBody.getFiscalCodePA()).isEqualTo("77777777777");
    assertThat(responseBody.getOfficeName()).isEqualTo("officeName");
    assertThat(responseBody.getPaymentDescription()).isEqualTo("paymentDescription");
  }

  @Test
  void paVerifyPaymentNoticeTestKOsconosciuto() throws DatatypeConfigurationException {

    ReflectionTestUtils.setField(partnerService, "ptIdDominio", "77777777777");
    ReflectionTestUtils.setField(partnerService, "ptIdIntermediario", "77777777777");
    ReflectionTestUtils.setField(partnerService, "ptIdStazione", "77777777777_01");

    // Test preconditions
    PaVerifyPaymentNoticeReq requestBody = PaVerifyPaymentNoticeReqMock.getMock();

    when(factory.createCtFaultBean()).thenReturn(factoryUtil.createCtFaultBean());
    when(factory.createPaVerifyPaymentNoticeRes()).thenReturn(factoryUtil.createPaVerifyPaymentNoticeRes());
    when(factory.createCtPaymentOptionDescriptionPA()).thenReturn(factoryUtil.createCtPaymentOptionDescriptionPA());
    when(factory.createCtPaymentOptionsDescriptionListPA())
        .thenReturn(factoryUtil.createCtPaymentOptionsDescriptionListPA());

    when(paymentOptionRepository.findByNotificationCode(requestBody.getQrCode().getNoticeNumber()))
        .thenReturn(Optional.of(DebitorMock.createPaymentOptionsMock6()));

    // Test execution
    PaVerifyPaymentNoticeRes responseBody = partnerService.paVerifyPaymentNotice(requestBody);

    // Test postcondiction
    assertThat(responseBody.getOutcome()).isEqualTo(StOutcome.KO);
    assertThat(responseBody.getFault().getFaultCode()).isEqualTo(PaaErrorEnum.PAA_PAGAMENTO_SCONOSCIUTO.getValue());
  }

  @Test
  void paVerifyPaymentNoticeTestKOpagato() throws DatatypeConfigurationException {

    ReflectionTestUtils.setField(partnerService, "ptIdDominio", "77777777777");
    ReflectionTestUtils.setField(partnerService, "ptIdIntermediario", "77777777777");
    ReflectionTestUtils.setField(partnerService, "ptIdStazione", "77777777777_01");

    // Test preconditions
    PaVerifyPaymentNoticeReq requestBody = PaVerifyPaymentNoticeReqMock.getMock();

    when(factory.createCtFaultBean()).thenReturn(factoryUtil.createCtFaultBean());
    when(factory.createPaVerifyPaymentNoticeRes()).thenReturn(factoryUtil.createPaVerifyPaymentNoticeRes());
    when(factory.createCtPaymentOptionDescriptionPA()).thenReturn(factoryUtil.createCtPaymentOptionDescriptionPA());
    when(factory.createCtPaymentOptionsDescriptionListPA())
        .thenReturn(factoryUtil.createCtPaymentOptionsDescriptionListPA());

    when(paymentOptionRepository.findByNotificationCode(requestBody.getQrCode().getNoticeNumber()))
        .thenReturn(Optional.of(DebitorMock.createPaymentOptionsMock5()));

    // Test execution
    PaVerifyPaymentNoticeRes responseBody = partnerService.paVerifyPaymentNotice(requestBody);

    // Test postcondiction
    assertThat(responseBody.getOutcome()).isEqualTo(StOutcome.KO);
    assertThat(responseBody.getFault().getFaultCode()).isEqualTo(PaaErrorEnum.PAA_PAGAMENTO_DUPLICATO.getValue());
  }

  @Test
  void paVerifyPaymentNoticeTestKOdominio() throws DatatypeConfigurationException {

    ReflectionTestUtils.setField(partnerService, "ptIdDominio", "77777777778");
    ReflectionTestUtils.setField(partnerService, "ptIdIntermediario", "77777777777");
    ReflectionTestUtils.setField(partnerService, "ptIdStazione", "77777777777_01");

    // Test preconditions
    PaVerifyPaymentNoticeReq requestBody = PaVerifyPaymentNoticeReqMock.getMock();

    when(factory.createCtFaultBean()).thenReturn(factoryUtil.createCtFaultBean());
    when(factory.createPaVerifyPaymentNoticeRes()).thenReturn(factoryUtil.createPaVerifyPaymentNoticeRes());

    // Test execution
    PaVerifyPaymentNoticeRes responseBody = partnerService.paVerifyPaymentNotice(requestBody);

    // Test postcondiction
    assertThat(responseBody.getOutcome()).isEqualTo(StOutcome.KO);
    assertThat(responseBody.getFault().getFaultCode()).isEqualTo(PaaErrorEnum.PAA_ID_DOMINIO_ERRATO.getValue());
  }

  @Test
  void paVerifyPaymentNoticeTestKOintermediario() throws DatatypeConfigurationException {

    ReflectionTestUtils.setField(partnerService, "ptIdDominio", "77777777777");
    ReflectionTestUtils.setField(partnerService, "ptIdIntermediario", "77777777778");
    ReflectionTestUtils.setField(partnerService, "ptIdStazione", "77777777777_01");

    // Test preconditions
    PaVerifyPaymentNoticeReq requestBody = PaVerifyPaymentNoticeReqMock.getMock();

    when(factory.createCtFaultBean()).thenReturn(factoryUtil.createCtFaultBean());
    when(factory.createPaVerifyPaymentNoticeRes()).thenReturn(factoryUtil.createPaVerifyPaymentNoticeRes());

    // Test execution
    PaVerifyPaymentNoticeRes responseBody = partnerService.paVerifyPaymentNotice(requestBody);

    // Test postcondiction
    assertThat(responseBody.getOutcome()).isEqualTo(StOutcome.KO);
    assertThat(responseBody.getFault().getFaultCode()).isEqualTo(PaaErrorEnum.PAA_ID_INTERMEDIARIO_ERRATO.getValue());
  }

  @Test
  void paVerifyPaymentNoticeTestKOstazione() throws DatatypeConfigurationException {

    ReflectionTestUtils.setField(partnerService, "ptIdDominio", "77777777777");
    ReflectionTestUtils.setField(partnerService, "ptIdIntermediario", "77777777777");
    ReflectionTestUtils.setField(partnerService, "ptIdStazione", "77777777777_02");

    // Test preconditions
    PaVerifyPaymentNoticeReq requestBody = PaVerifyPaymentNoticeReqMock.getMock();

    when(factory.createCtFaultBean()).thenReturn(factoryUtil.createCtFaultBean());
    when(factory.createPaVerifyPaymentNoticeRes()).thenReturn(factoryUtil.createPaVerifyPaymentNoticeRes());

    // Test execution
    PaVerifyPaymentNoticeRes responseBody = partnerService.paVerifyPaymentNotice(requestBody);

    // Test postcondiction
    assertThat(responseBody.getOutcome()).isEqualTo(StOutcome.KO);
    assertThat(responseBody.getFault().getFaultCode()).isEqualTo(PaaErrorEnum.PAA_STAZIONE_INT_ERRATA.getValue());
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
