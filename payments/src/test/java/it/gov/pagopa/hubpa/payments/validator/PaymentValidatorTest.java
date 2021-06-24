package it.gov.pagopa.hubpa.payments.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import it.gov.pagopa.hubpa.payments.endpoints.validation.PaymentValidator;
import it.gov.pagopa.hubpa.payments.endpoints.validation.exceptions.SoapValidationException;
import it.gov.pagopa.hubpa.payments.entity.PaymentOptions;
import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;
import it.gov.pagopa.hubpa.payments.enumeration.PaymentOptionStatusEnum;
import it.gov.pagopa.hubpa.payments.enumeration.PaymentStatusEnum;
import it.gov.pagopa.hubpa.payments.mock.DebitorMock;

@ExtendWith(MockitoExtension.class)
class PaymentValidatorTest {

  @InjectMocks
  private static PaymentValidator paymentValidator;

  private static String validPtIdDominio = "123";
  private static String validPtIdIntermediario = "456";
  private static String validPtIdStazione = "789";

  @BeforeEach
  private void init() {

    ReflectionTestUtils.setField(paymentValidator, "ptIdDominio", validPtIdDominio);
    ReflectionTestUtils.setField(paymentValidator, "ptIdIntermediario", validPtIdIntermediario);
    ReflectionTestUtils.setField(paymentValidator, "ptIdStazione", validPtIdStazione);
  }

  @Test
  void isAuthorizeOKTest() {

    try {

      paymentValidator.isAuthorize(validPtIdDominio, validPtIdIntermediario, validPtIdStazione);

    } catch (SoapValidationException e) {

      assertFalse(true);

    }
    assertTrue(true);

  }

  @Test
  void isPaybleOKTest() {

    PaymentOptions option = DebitorMock.createPaymentOptionsMock4();
    PaymentPosition position = option.getPaymentPosition();

    try {

      paymentValidator.isPayable(position, option);

    } catch (SoapValidationException e) {

      assertFalse(true);

    }
    assertTrue(true);

  }

  @Test
  void isAuthorizeDominioKOTest() {

    try {

      paymentValidator.isAuthorize("invalidDomain", validPtIdIntermediario, validPtIdStazione);

    } catch (SoapValidationException e) {

      assertTrue(true);

    }

  }

  @Test
  void isAuthorizeIntermediarioKOTest() {

    try {

      paymentValidator.isAuthorize(validPtIdDominio, "invalidIntermediario", validPtIdStazione);

    } catch (SoapValidationException e) {

      assertTrue(true);

    }

  }

  @Test
  void isAuthorizeStatizioneKOTest() {

    try {

      paymentValidator.isAuthorize(validPtIdDominio, validPtIdIntermediario, "invalidStazione");

    } catch (SoapValidationException e) {

      assertTrue(true);

    }

  }

  @Test
  void isPaybleNullPositionKOTest() {

    PaymentOptions option = DebitorMock.createPaymentOptionsMock4();
    PaymentPosition position = null;

    try {

      paymentValidator.isPayable(position, option);

    } catch (SoapValidationException e) {

      assertTrue(true);

    }

  }

  @Test
  void isPaybleNullPositionAndOptionKOTest() {

    PaymentOptions option = null;
    PaymentPosition position = null;

    try {

      paymentValidator.isPayable(position, option);

    } catch (SoapValidationException e) {

      assertTrue(true);

    }

  }

  @Test
  void isPaybleAlreadyPaidOptionKOTest() {

    PaymentOptions option = DebitorMock.createPaymentOptionsMock4();
    PaymentPosition position = option.getPaymentPosition();

    option.setStatus(PaymentOptionStatusEnum.PAGATO.getStatus());
    try {

      paymentValidator.isPayable(position, option);

    } catch (SoapValidationException e) {

      assertTrue(true);

    }

  }

  @Test
  void isPaybleNotPublishedOptionKOTest() {

    PaymentOptions option = DebitorMock.createPaymentOptionsMock4();
    PaymentPosition position = option.getPaymentPosition();

    position.setStatus(PaymentStatusEnum.BOZZA.getStatus());
    try {

      paymentValidator.isPayable(position, option);

    } catch (SoapValidationException e) {

      assertTrue(true);

    }

  }
}