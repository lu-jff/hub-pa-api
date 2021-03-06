
package it.gov.pagopa.hubpa.payments.service;

import java.util.Optional;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

  @Value("${pt.id_dominio}")
  private String ptIdDominio;

  @Value("${pt.id_intermediario}")
  private String ptIdIntermediario;

  @Value("${pt.id_stazione}")
  private String ptIdStazione;

  public PaVerifyPaymentNoticeRes paVerifyPaymentNotice(PaVerifyPaymentNoticeReq request)
      throws DatatypeConfigurationException {

    log.debug(String.format("paVerifyPaymentNotice %s", request.getIdPA()));
    PaVerifyPaymentNoticeRes result = factory.createPaVerifyPaymentNoticeRes();
    CtFaultBean faultError = factory.createCtFaultBean();
    CtPaymentOptionsDescriptionListPA paymentList = factory.createCtPaymentOptionsDescriptionListPA();
    CtPaymentOptionDescriptionPA paymentOption = factory.createCtPaymentOptionDescriptionPA();
    // verificare che l' idPA /Intermediario /Stazione
    // corrispondono con quelli configurati altrimenti restituire rispettivamente i
    // seguenti FaultCode
    // PAA_ID_DOMINIO_ERRATO
    // PAA_ID_INTERMEDIARIO_ERRATO
    // PAA_STAZIONE_INT_ERRATA
    if (!request.getIdPA().equals(ptIdDominio)) {
      result.setOutcome(StOutcome.KO);
      faultError.setDescription("L'idPA ricevuto non e' tra quelli configurati");
      faultError.setFaultCode(PaaErrorEnum.PAA_ID_DOMINIO_ERRATO.getValue());
      faultError.setFaultString("ID dominio errato");
      result.setFault(faultError);
    } else if (!request.getIdBrokerPA().equals(ptIdIntermediario)) {
      result.setOutcome(StOutcome.KO);
      faultError.setDescription("L'IdBrokerPA ricevuto non e' tra quelli configurati");
      faultError.setFaultCode(PaaErrorEnum.PAA_ID_INTERMEDIARIO_ERRATO.getValue());
      faultError.setFaultString("IdBrokerPA errato");
      result.setFault(faultError);
    } else if (!request.getIdStation().equals(ptIdStazione)) {
      result.setOutcome(StOutcome.KO);
      faultError.setDescription("L'IdStazione ricevuto non e' tra quelli configurati");
      faultError.setFaultCode(PaaErrorEnum.PAA_STAZIONE_INT_ERRATA.getValue());
      faultError.setFaultString("IdStazione errato");
      result.setFault(faultError);
    } else {
      // verificare che se esiste un payment_options NON_PAGATO che ha un
      // notification_code
      // uguale a noticeNumber e se lo stato della relativa payment_position e'
      // PUBBLICATO

      Optional<PaymentOptions> option = paymentOptionsRepository
          .findByNotificationCode(request.getQrCode().getNoticeNumber());

      Optional<PaymentPosition> position = Optional
          .ofNullable((option.isPresent() ? option.get().getPaymentPosition() : null));

      if ((!option.isPresent() || !position.isPresent())
          || (!position.get().getStatus().equals(PaymentStatusEnum.PUBBLICATO.getStatus())
              && (!position.get().getStatus().equals(PaymentStatusEnum.PAGATO_PARZIALE.getStatus())))) {
        result.setOutcome(StOutcome.KO);
        faultError
            .setDescription("L'id del pagamento ricevuto " + request.getQrCode().getNoticeNumber() + " non esiste");
        faultError.setFaultCode(PaaErrorEnum.PAA_PAGAMENTO_SCONOSCIUTO.getValue());
        faultError.setFaultString("pagamento sconosciuto");
        result.setFault(faultError);
      } else {
        if (!option.isPresent() || !option.get().getStatus().equals(PaymentOptionStatusEnum.NON_PAGATO.getStatus())) {
          result.setOutcome(StOutcome.KO);
          faultError
              .setDescription("L'id del pagamento ricevuto " + request.getQrCode().getNoticeNumber() + " e' duplicato");
          faultError.setFaultCode(PaaErrorEnum.PAA_PAGAMENTO_DUPLICATO.getValue());
          faultError.setFaultString("pagamento duplicato");
          result.setFault(faultError);
        } else {
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
          result.setFiscalCodePA(ptIdDominio);
          result.setCompanyName(position.get().getCompanyName());
          result.setOfficeName(position.get().getOfficeName());

        }
      }
    }
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
