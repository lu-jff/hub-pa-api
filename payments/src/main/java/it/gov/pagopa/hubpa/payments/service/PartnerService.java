
package it.gov.pagopa.hubpa.payments.service;

import java.util.Optional;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.gov.pagopa.hubpa.payments.endpoints.validation.PaymentValidator;
import it.gov.pagopa.hubpa.payments.endpoints.validation.exceptions.SoapValidationException;
import it.gov.pagopa.hubpa.payments.entity.PaymentOptions;
import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;
import it.gov.pagopa.hubpa.payments.enumeration.PaymentOptionStatusEnum;
import it.gov.pagopa.hubpa.payments.enumeration.PaymentStatusEnum;
import it.gov.pagopa.hubpa.payments.model.PaaErrorEnum;
import it.gov.pagopa.hubpa.payments.model.partner.ObjectFactory;
import it.gov.pagopa.hubpa.payments.model.partner.StAmountOption;
import it.gov.pagopa.hubpa.payments.model.partner.CtPaymentPA;
import it.gov.pagopa.hubpa.payments.model.partner.CtFaultBean;
import it.gov.pagopa.hubpa.payments.model.partner.CtPaymentOptionsDescriptionListPA;
import it.gov.pagopa.hubpa.payments.model.partner.CtPaymentOptionDescriptionPA;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeRes;
import it.gov.pagopa.hubpa.payments.model.partner.StOutcome;
import it.gov.pagopa.hubpa.payments.repository.IncrementalIuvNumberRepository;
import it.gov.pagopa.hubpa.payments.repository.PaymentOptionsRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PartnerService {

  @Autowired
  IncrementalIuvNumberRepository incrementalIuvNumberRepository;

  @Autowired
  private PaymentOptionsRepository paymentOptionsRepository;

  @Autowired
  private ObjectFactory factory;

  @Autowired
  private PaymentValidator partnerValidator;

  public PaVerifyPaymentNoticeRes paVerifyPaymentNotice(PaVerifyPaymentNoticeReq request)
      throws DatatypeConfigurationException, SoapValidationException {

    log.debug("[paVerifyPaymentNotice] isAuthorize check");
    partnerValidator.isAuthorize(request.getIdPA(), request.getIdBrokerPA(), request.getIdStation());

    log.debug(String.format("[paVerifyPaymentNotice] get Payment %s", request.getQrCode().getNoticeNumber()));
    Optional<PaymentOptions> option = paymentOptionsRepository
        .findByNotificationCode(request.getQrCode().getNoticeNumber());
    Optional<PaymentPosition> position = Optional
        .ofNullable((option.isPresent() ? option.get().getPaymentPosition() : null));

    log.debug("[paVerifyPaymentNotice] isPayable check");
    partnerValidator.isPayable(position, option);

    log.debug("[paVerifyPaymentNotice] Response generation");
    PaVerifyPaymentNoticeRes result = factory.createPaVerifyPaymentNoticeRes();
    CtPaymentOptionsDescriptionListPA paymentList = factory.createCtPaymentOptionsDescriptionListPA();
    CtPaymentOptionDescriptionPA paymentOption = factory.createCtPaymentOptionDescriptionPA();
    // generare una paVerifyPaymentNoticeRes positiva
    result.setOutcome(StOutcome.OK);
    // paymentList
    paymentOption.setAmount(option.get().getAmount());
    paymentOption.setOptions(StAmountOption.EQ); // de-scoping
    paymentOption
        .setDueDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(option.get().getDuoDate().toString()));
    paymentOption.setDetailDescription(position.get().getDescription());
    paymentOption.setAllCCP(isAllCCPostalIban(option)); // allCPP fa parte del modello del option
    paymentList.getPaymentOptionDescription().add(paymentOption);

    result.setPaymentList(paymentList);
    // general info
    result.setPaymentDescription(position.get().getDescription());
    result.setFiscalCodePA(request.getIdPA());
    result.setCompanyName(position.get().getCompanyName());
    result.setOfficeName(position.get().getOfficeName());

    return result;

  }

  private Boolean isAllCCPostalIban(Optional<PaymentOptions> option) {
    // TODO : questa info sara' a livello di option
    return true;
  }

  public PaGetPaymentRes paGetPayment(PaGetPaymentReq request) {

    log.debug(String.format("paGetPayment %s", request.getIdPA()));

    // mock response
    PaGetPaymentRes result = new PaGetPaymentRes();
    CtPaymentPA data = new CtPaymentPA();
    data.setCompanyName("company name");
    data.setCreditorReferenceId("id");
    result.setData(data);
    return result;
  }

  public PaSendRTRes paSendRT(PaSendRTReq request) {

    log.debug(String.format("paSendRT %s", request.getIdPA()));

    // mock response
    PaSendRTRes result = new PaSendRTRes();
    result.setOutcome(StOutcome.OK);
    return result;
  }

}
