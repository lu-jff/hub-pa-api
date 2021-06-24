package it.gov.pagopa.hubpa.payments.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.util.Optional;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import it.gov.pagopa.hubpa.payments.endpoints.validation.PaymentValidator;
import it.gov.pagopa.hubpa.payments.endpoints.validation.exceptions.SoapValidationException;
import it.gov.pagopa.hubpa.payments.entity.PaymentOptions;
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

@ExtendWith(MockitoExtension.class)
class PartnerServiceTest {

  @InjectMocks
  private PartnerService partnerService;

  @Mock
  DebitorRepository debitorRepository;

  @Mock
  PaymentOptionsRepository paymentOptionRepository;

  @Mock
  IncrementalIuvNumberRepository incrementalIuvNumberRepository;

  @Mock
  PaymentValidator paymentValidator;

  @Mock
  private ObjectFactory factory;

  private ObjectFactory factoryUtil = new ObjectFactory();

  @Test
  void paVerifyPaymentNoticeTest() throws DatatypeConfigurationException {

    // Test preconditions
    PaVerifyPaymentNoticeReq requestBody = PaVerifyPaymentNoticeReqMock.getMock();

    doNothing().when(paymentValidator).isAuthorize(Mockito.any(), Mockito.any(), Mockito.any());
    doNothing().when(paymentValidator).isPayable(Mockito.any(), Mockito.any());
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

    // Test preconditions
    PaVerifyPaymentNoticeReq requestBody = PaVerifyPaymentNoticeReqMock.getMock();
    PaymentOptions option = DebitorMock.createPaymentOptionsMock5();
    String description = "Description";
    String faultString = "faultString";

    when(paymentOptionRepository.findByNotificationCode(requestBody.getQrCode().getNoticeNumber()))
        .thenReturn(Optional.of(option));

    doNothing().when(paymentValidator).isAuthorize(Mockito.any(), Mockito.any(), Mockito.any());

    doThrow(new SoapValidationException(PaaErrorEnum.PAA_PAGAMENTO_SCONOSCIUTO, faultString, description))
        .when(paymentValidator).isPayable(option.getPaymentPosition(), option);

    // Test execution
    try {

      partnerService.paVerifyPaymentNotice(requestBody);

    } catch (SoapValidationException e) {
      // Test postcondiction
      assertThat(e.getFaultCode().getValue()).isEqualTo(PaaErrorEnum.PAA_PAGAMENTO_SCONOSCIUTO.getValue());
      assertThat(e.getDescription()).isEqualTo(description);
      assertThat(e.getFaultString()).isEqualTo(faultString);
    }
  }

  @Test
  void paVerifyPaymentNoticeTestKOpagato() throws DatatypeConfigurationException {

    // Test preconditions
    PaVerifyPaymentNoticeReq requestBody = PaVerifyPaymentNoticeReqMock.getMock();
    PaymentOptions option = DebitorMock.createPaymentOptionsMock5();
    String description = "Description";
    String faultString = "faultString";

    when(paymentOptionRepository.findByNotificationCode(requestBody.getQrCode().getNoticeNumber()))
        .thenReturn(Optional.of(option));

    doNothing().when(paymentValidator).isAuthorize(Mockito.any(), Mockito.any(), Mockito.any());

    doThrow(new SoapValidationException(PaaErrorEnum.PAA_PAGAMENTO_DUPLICATO, faultString, description))
        .when(paymentValidator).isPayable(option.getPaymentPosition(), option);

    // Test execution
    try {

      partnerService.paVerifyPaymentNotice(requestBody);

    } catch (SoapValidationException e) {
      // Test postcondiction
      assertThat(e.getFaultCode().getValue()).isEqualTo(PaaErrorEnum.PAA_PAGAMENTO_DUPLICATO.getValue());
      assertThat(e.getDescription()).isEqualTo(description);
      assertThat(e.getFaultString()).isEqualTo(faultString);
    }

  }

  @Test
  void paVerifyPaymentNoticeTestKOdominio() throws DatatypeConfigurationException {

    // Test preconditions
    PaVerifyPaymentNoticeReq requestBody = PaVerifyPaymentNoticeReqMock.getMock();
    String description = "Description";
    String faultString = "faultString";

    doThrow(new SoapValidationException(PaaErrorEnum.PAA_ID_DOMINIO_ERRATO, faultString, description))
        .when(paymentValidator)
        .isAuthorize(requestBody.getIdPA(), requestBody.getIdBrokerPA(), requestBody.getIdStation());

    // Test execution
    try {

      partnerService.paVerifyPaymentNotice(requestBody);

    } catch (SoapValidationException e) {
      // Test postcondiction
      assertThat(e.getFaultCode().getValue()).isEqualTo(PaaErrorEnum.PAA_ID_DOMINIO_ERRATO.getValue());
      assertThat(e.getDescription()).isEqualTo(description);
      assertThat(e.getFaultString()).isEqualTo(faultString);
    }

  }

  @Test
  void paVerifyPaymentNoticeTestKOintermediario() throws DatatypeConfigurationException {

    // Test preconditions
    PaVerifyPaymentNoticeReq requestBody = PaVerifyPaymentNoticeReqMock.getMock();
    String description = "Description";
    String faultString = "faultString";

    doThrow(new SoapValidationException(PaaErrorEnum.PAA_ID_INTERMEDIARIO_ERRATO, faultString, description))
        .when(paymentValidator)
        .isAuthorize(requestBody.getIdPA(), requestBody.getIdBrokerPA(), requestBody.getIdStation());

    // Test execution
    try {

      partnerService.paVerifyPaymentNotice(requestBody);

    } catch (SoapValidationException e) {
      // Test postcondiction
      assertThat(e.getFaultCode().getValue()).isEqualTo(PaaErrorEnum.PAA_ID_INTERMEDIARIO_ERRATO.getValue());
      assertThat(e.getDescription()).isEqualTo(description);
      assertThat(e.getFaultString()).isEqualTo(faultString);
    }

  }

  @Test
  void paVerifyPaymentNoticeTestKOstazione() throws DatatypeConfigurationException {

    // Test preconditions
    PaVerifyPaymentNoticeReq requestBody = PaVerifyPaymentNoticeReqMock.getMock();
    String description = "Description";
    String faultString = "faultString";

    doThrow(new SoapValidationException(PaaErrorEnum.PAA_ID_DOMINIO_ERRATO, faultString, description))
        .when(paymentValidator)
        .isAuthorize(requestBody.getIdPA(), requestBody.getIdBrokerPA(), requestBody.getIdStation());

    // Test execution
    try {

      partnerService.paVerifyPaymentNotice(requestBody);

    } catch (SoapValidationException e) {
      // Test postcondiction
      assertThat(e.getFaultCode().getValue()).isEqualTo(PaaErrorEnum.PAA_ID_DOMINIO_ERRATO.getValue());
      assertThat(e.getDescription()).isEqualTo(description);
      assertThat(e.getFaultString()).isEqualTo(faultString);
    }
  }

  @Test
  void paGetPaymentTest() throws SoapValidationException, DatatypeConfigurationException {

    // Test preconditions
    PaGetPaymentReq requestBody = PaGetPaymentReqMock.getMock();
    PaymentOptions option = DebitorMock.createPaymentOptionsMock4();

    when(factory.createPaGetPaymentRes()).thenReturn(factoryUtil.createPaGetPaymentRes());
    when(factory.createCtPaymentPA()).thenReturn(factoryUtil.createCtPaymentPA());
    when(factory.createCtSubject()).thenReturn(factoryUtil.createCtSubject());
    when(factory.createCtEntityUniqueIdentifier()).thenReturn(factoryUtil.createCtEntityUniqueIdentifier());
    when(factory.createCtTransferListPA()).thenReturn(factoryUtil.createCtTransferListPA());
    when(factory.createCtTransferPA()).thenReturn(factoryUtil.createCtTransferPA());

    when(paymentOptionRepository.findByNotificationCode(requestBody.getQrCode().getNoticeNumber()))
        .thenReturn(Optional.of(option));

    // Test execution
    PaGetPaymentRes responseBody = partnerService.paGetPayment(requestBody);

    // Test postcondiction
    assertThat(responseBody.getData().getCompanyName()).isEqualTo(option.getPaymentPosition().getCompanyName());
    assertThat(responseBody.getData().getCreditorReferenceId()).isEqualTo(option.getNotificationCode().substring(1));
    assertThat(responseBody.getData().getDebtor().getFullName())
        .isEqualTo(option.getPaymentPosition().getDebitor().getName());
    assertThat(responseBody.getData().getDebtor().getCity())
        .isEqualTo(option.getPaymentPosition().getDebitor().getArea());
    assertThat(responseBody.getData().getDebtor().getCivicNumber())
        .isEqualTo(option.getPaymentPosition().getDebitor().getNumber());
    assertThat(responseBody.getData().getDebtor().getCountry())
        .isEqualTo(option.getPaymentPosition().getDebitor().getCountry());
    assertThat(responseBody.getData().getDebtor().getEMail())
        .isEqualTo(option.getPaymentPosition().getDebitor().getEmail());
    assertThat(responseBody.getData().getDebtor().getPostalCode())
        .isEqualTo(option.getPaymentPosition().getDebitor().getCap());
    assertThat(responseBody.getData().getDebtor().getStateProvinceRegion())
        .isEqualTo(option.getPaymentPosition().getDebitor().getProvince());
    assertThat(responseBody.getData().getDebtor().getStreetName())
        .isEqualTo(option.getPaymentPosition().getDebitor().getAddress());
    assertThat(responseBody.getData().getDebtor().getUniqueIdentifier().getEntityUniqueIdentifierValue())
        .isEqualTo(option.getPaymentPosition().getDebitor().getFiscalCode());
    assertThat(responseBody.getData().getDebtor().getUniqueIdentifier().getEntityUniqueIdentifierType().value())
        .isEqualTo(option.getPaymentPosition().getDebitor().getType().equals(1) ? "F" : "G");
    assertThat(responseBody.getData().getDescription()).isEqualTo(option.getPaymentPosition().getDescription());
    assertThat(responseBody.getData().getDueDate())
        .isEqualTo(DatatypeFactory.newInstance().newXMLGregorianCalendar(option.getDuoDate().toString()));
    assertThat(responseBody.getData().getRetentionDate())
        .isEqualTo(DatatypeFactory.newInstance().newXMLGregorianCalendar(option.getRetentionDate().toString()));
    assertThat(responseBody.getData().getOfficeName()).isEqualTo(option.getPaymentPosition().getOfficeName());
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
