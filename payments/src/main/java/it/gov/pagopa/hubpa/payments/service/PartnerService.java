
package it.gov.pagopa.hubpa.payments.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gov.pagopa.hubpa.payments.endpoints.validation.PaymentValidator;
import it.gov.pagopa.hubpa.payments.endpoints.validation.exceptions.SoapValidationException;
import it.gov.pagopa.hubpa.payments.entity.PaymentOptions;
import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;
import it.gov.pagopa.hubpa.payments.entity.Transfers;
import it.gov.pagopa.hubpa.payments.model.partner.ObjectFactory;
import it.gov.pagopa.hubpa.payments.model.partner.StAmountOption;
import it.gov.pagopa.hubpa.payments.model.partner.StEntityUniqueIdentifierType;
import it.gov.pagopa.hubpa.payments.model.partner.CtPaymentPA;
import it.gov.pagopa.hubpa.payments.model.partner.CtSubject;
import it.gov.pagopa.hubpa.payments.model.partner.CtTransferListPA;
import it.gov.pagopa.hubpa.payments.model.partner.CtTransferPA;
import it.gov.pagopa.hubpa.payments.model.PaaErrorEnum;
import it.gov.pagopa.hubpa.payments.model.partner.CtEntityUniqueIdentifier;
import it.gov.pagopa.hubpa.payments.model.partner.CtPaymentOptionsDescriptionListPA;
import it.gov.pagopa.hubpa.payments.model.partner.CtPaymentOptionDescriptionPA;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeRes;
import it.gov.pagopa.hubpa.payments.model.partner.StOutcome;
import it.gov.pagopa.hubpa.payments.model.partner.StTransferType;
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
    PaymentOptions option = paymentOptionsRepository.findByNotificationCode(request.getQrCode().getNoticeNumber())
        .orElseThrow(() -> new SoapValidationException(PaaErrorEnum.PAA_PAGAMENTO_SCONOSCIUTO, "pagamento sconosciuto",
            "L'id del pagamento ricevuto non esiste"));

    PaymentPosition position = option.getPaymentPosition();

    log.debug("[paVerifyPaymentNotice] isPayable check");
    partnerValidator.isPayable(position, option);

    log.debug("[paVerifyPaymentNotice] Response generation");
    PaVerifyPaymentNoticeRes result = factory.createPaVerifyPaymentNoticeRes();
    CtPaymentOptionsDescriptionListPA paymentList = factory.createCtPaymentOptionsDescriptionListPA();
    CtPaymentOptionDescriptionPA paymentOption = factory.createCtPaymentOptionDescriptionPA();
    // generare una paVerifyPaymentNoticeRes positiva
    result.setOutcome(StOutcome.OK);
    // paymentList
    paymentOption.setAmount(option.getAmount());
    paymentOption.setOptions(StAmountOption.EQ); // de-scoping
    paymentOption.setDueDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(option.getDuoDate().toString()));
    paymentOption.setDetailDescription(position.getDescription());
    paymentOption.setAllCCP(isAllCCPostalIban(option)); // allCPP fa parte del modello del option
    paymentList.getPaymentOptionDescription().add(paymentOption);

    result.setPaymentList(paymentList);
    // general info
    result.setPaymentDescription(position.getDescription());
    result.setFiscalCodePA(request.getIdPA());
    result.setCompanyName(position.getCompanyName());
    result.setOfficeName(position.getOfficeName());

    return result;

  }

  private Boolean isAllCCPostalIban(PaymentOptions option) {
    // TODO : questa info sara' a livello di option
    return true;
  }

  public PaGetPaymentRes paGetPayment(PaGetPaymentReq request)
      throws DatatypeConfigurationException, SoapValidationException {

    log.debug("[paGetPayment] isAuthorize check");
    partnerValidator.isAuthorize(request.getIdPA(), request.getIdBrokerPA(), request.getIdStation());

    log.debug(String.format("[paGetPayment] get Payment %s", request.getQrCode().getNoticeNumber()));
    PaymentOptions option = paymentOptionsRepository.findByNotificationCode(request.getQrCode().getNoticeNumber())
        .orElseThrow(() -> new SoapValidationException(PaaErrorEnum.PAA_PAGAMENTO_SCONOSCIUTO, "pagamento sconosciuto",
            "L'id del pagamento ricevuto non esiste"));

    PaymentPosition position = option.getPaymentPosition();

    log.debug("[paGetPayment] isPayable check");
    partnerValidator.isPayable(position, option);

    log.debug("[paGetPayment] Response generation");

    // mock response
    PaGetPaymentRes response = factory.createPaGetPaymentRes();
    CtPaymentPA responseData = factory.createCtPaymentPA();
    CtSubject debitor = factory.createCtSubject();
    CtEntityUniqueIdentifier uniqueIdentifier = factory.createCtEntityUniqueIdentifier();
    CtTransferListPA transferList = factory.createCtTransferListPA();

    response.setOutcome(StOutcome.OK);

    // general payment data
    responseData.setCreditorReferenceId(request.getQrCode().getNoticeNumber().substring(1));
    responseData.setPaymentAmount(option.getAmount());
    responseData.setDueDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(option.getDuoDate().toString()));
    responseData
        .setRetentionDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(option.getRetentionDate().toString()));
    responseData.setLastPayment(false); // de-scoping
    responseData.setDescription(position.getDescription());
    responseData.setCompanyName(position.getCompanyName());
    responseData.setOfficeName(position.getOfficeName());

    // debitor data
    uniqueIdentifier.setEntityUniqueIdentifierType(
        position.getDebitor().getType().equals(1) ? StEntityUniqueIdentifierType.F : StEntityUniqueIdentifierType.G);
    uniqueIdentifier.setEntityUniqueIdentifierValue(position.getDebitor().getFiscalCode());
    debitor.setUniqueIdentifier(uniqueIdentifier);
    debitor.setFullName(position.getDebitor().getName());
    debitor.setStreetName(position.getDebitor().getAddress());
    debitor.setCivicNumber(position.getDebitor().getNumber());
    debitor.setPostalCode(position.getDebitor().getCap());
    debitor.setCity(position.getDebitor().getArea());
    debitor.setStateProvinceRegion(position.getDebitor().getProvince());
    debitor.setCountry(position.getDebitor().getCountry());
    debitor.setEMail(position.getDebitor().getEmail());

    // Transfer list
    transferList.getTransfer()
        .addAll(IntStream.range(0, option.getTransfers().size())
            .mapToObj(index -> getTransferResponse(option.getTransfers().get(index), index, request.getTransferType()))
            .collect(Collectors.toList()));

    responseData.setTransferList(transferList);
    responseData.setDebtor(debitor);
    response.setData(responseData);

    return response;
  }

  public PaSendRTRes paSendRT(PaSendRTReq request) {

    log.debug(String.format("paSendRT %s", request.getIdPA()));

    // mock response
    PaSendRTRes result = new PaSendRTRes();
    result.setOutcome(StOutcome.OK);
    return result;
  }

  private CtTransferPA getTransferResponse(Transfers transfer, int counter, StTransferType transferType) {

    CtTransferPA transferPa = factory.createCtTransferPA();
    transferPa.setFiscalCodePA(transfer.getOrganizationFiscalCode());
    transferPa.setIBAN(
        transferType != null && transferType.value().equals(StTransferType.POSTAL.value()) ? transfer.getPostalIban()
            : transfer.getIban());
    transferPa.setIdTransfer(counter + 1);
    transferPa.setRemittanceInformation(transfer.getReason());
    return transferPa;
  }
}
