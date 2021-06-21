
package it.gov.pagopa.hubpa.payments.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;
import it.gov.pagopa.hubpa.payments.enumeration.PaymentStatusEnum;
import it.gov.pagopa.hubpa.payments.model.AmountOptEnum;
import it.gov.pagopa.hubpa.payments.model.PaaErrorEnum;
import it.gov.pagopa.hubpa.payments.model.partner.ObjectFactory;
import it.gov.pagopa.hubpa.payments.model.partner.CtPaymentPA;
import it.gov.pagopa.hubpa.payments.model.partner.CtFaultBean;
import it.gov.pagopa.hubpa.payments.model.partner.CtPaymentOptionsDescriptionListPA;
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
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PartnerService {

  @Autowired
  private DebitorRepository debitorRepository;

  @Autowired
  IncrementalIuvNumberRepository incrementalIuvNumberRepository;

  @Autowired
  private PaymentPositionRepository paymentPositionRepository;

  @Autowired
  private ObjectFactory factory;

  @Value("${pt.id_dominio}")
  private String ptIdDominio;

  @Value("${pt.id_intermediario}")
  private String ptIdIntermediario;

  @Value("${pt.id_stazione}")
  private String ptIdStazione;

  public PaVerifyPaymentNoticeRes paVerifyPaymentNotice(PaVerifyPaymentNoticeReq request) {

    log.debug(String.format("paVerifyPaymentNotice %s", request.getIdPA()));
    PaVerifyPaymentNoticeRes res = factory.createPaVerifyPaymentNoticeRes();
    CtFaultBean cFault = factory.createCtFaultBean();
    CtPaymentOptionsDescriptionListPA cPaymentList = factory.createCtPaymentOptionsDescriptionListPA();

    // verificare che il CF idPA /Intermediario idBrokerPA/Stazione idStation
    // corrispondono con quelli configurati altrimenti restituire rispettivamente i
    // seguenti FaultCode
    // PAA_ID_DOMINIO_ERRATO
    // PAA_ID_INTERMEDIARIO_ERRATO
    // PAA_STAZIONE_INT_ERRATA
    if (!request.getIdPA().equals(ptIdDominio)) {
      res.setOutcome(StOutcome.KO);
      cFault.setDescription("L'idPA ricevuto non e' tra quelli configurati");
      cFault.setFaultCode(PaaErrorEnum.PAA_ID_DOMINIO_ERRATO.getValue());
      cFault.setFaultString("ID dominio errato");
      res.setFault(cFault);
      // result.setFault(factory.createJaxbpaVeri)
    } else if (!request.getIdBrokerPA().equals(ptIdIntermediario)) {
      res.setOutcome(StOutcome.KO);
      cFault.setDescription("L'IdBrokerPA ricevuto non e' tra quelli configurati");
      cFault.setFaultCode(PaaErrorEnum.PAA_ID_INTERMEDIARIO_ERRATO.getValue());
      cFault.setFaultString("IdBrokerPA errato");
      res.setFault(cFault);

    } else if (!request.getIdStation().equals(ptIdStazione)) {
      res.setOutcome(StOutcome.KO);
      cFault.setDescription("L'IdStazione ricevuto non e' tra quelli configurati");
      cFault.setFaultCode(PaaErrorEnum.PAA_STAZIONE_INT_ERRATA.getValue());
      cFault.setFaultString("IdStazione errato");
      res.setFault(cFault);

    }
    // verificare che se esiste un payment_options che ha un notification_code
    // uguale a noticeNumber e lo stato della relativa payment_position sia
    // PUBBLICATO, altrimenti restituire PAA_PAGAMENTO_SCONOSCIUTO
    Optional<PaymentPosition> payment = paymentPositionRepository
        .findByNotificationCode(request.getQrCode().getNoticeNumber());
    if (!payment.isPresent() || (payment.get().getStatus() != PaymentStatusEnum.PUBBLICATO.getStatus())) {
      res.setOutcome(StOutcome.KO);
      cFault.setDescription("L'id del pagamento ricevuto " + request.getQrCode().getNoticeNumber() + " non esiste");
      cFault.setFaultCode(PaaErrorEnum.PAA_PAGAMENTO_SCONOSCIUTO.getValue());
      cFault.setFaultString("pagamento sconosciuto");
      res.setFault(cFault);
    } else {
      // generare una paVerifyPaymentNoticeRes positiva con :
      // amount = payment_options.amount
      // dueDate = payment_options.duo_date
      // detailDescription = payment_position.description
      // NOTA : paymentList presente nella response di fatto corrisponde alle Option
      // nello specifico con la verifca si richiede l'opzione legato allo
      // IUV/NoticeNumber
      res.setOutcome(StOutcome.OK);
      // cPaymentList.setAmount(res.get().getAmount());
      // cPaymentList.setOptions(AmountOptEnum.EQ.getValue());
      // cPaymentList.setDueDate(res.get().getDueDate());
      // cPaymentList.setDetailDescription(""); // get from payment
      // // allCCP = true se solo se l’opzione è associabile a tutti transfer con
      // Conti
      // // Correnti Postali altrimenti false
      // cPaymentList.setAllCCP(false);

    }

    return res;

    // mock response
    // PaVerifyPaymentNoticeRes result = new PaVerifyPaymentNoticeRes();
    // result.setCompanyName("company name");
    // result.setOfficeName("officeName");
    // result.setFiscalCodePA("77777777777");
    // result.setPaymentDescription("payment");
    // result.setOfficeName("officeName");

    // return result;
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
