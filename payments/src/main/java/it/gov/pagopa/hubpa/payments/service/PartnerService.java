
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

  public PaGetPaymentRes paGetPayment(PaGetPaymentReq request)
      throws DatatypeConfigurationException, SoapValidationException {

    log.debug("[paGetPayment] isAuthorize check");
    partnerValidator.isAuthorize(request.getIdPA(), request.getIdBrokerPA(), request.getIdStation());

    log.debug(String.format("[paGetPayment] get Payment %s", request.getQrCode().getNoticeNumber()));
    Optional<PaymentOptions> option = paymentOptionsRepository
        .findByNotificationCode(request.getQrCode().getNoticeNumber());
    Optional<PaymentPosition> position = Optional
        .ofNullable((option.isPresent() ? option.get().getPaymentPosition() : null));

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
    responseData.setPaymentAmount(option.get().getAmount());
    responseData
        .setDueDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(option.get().getDuoDate().toString()));
    responseData.setRetentionDate(
        DatatypeFactory.newInstance().newXMLGregorianCalendar(option.get().getRetentionDate().toString()));
    responseData.setLastPayment(false); // de-scoping
    responseData.setDescription(position.get().getDescription());
    responseData.setCompanyName(position.get().getCompanyName());
    responseData.setOfficeName(position.get().getOfficeName());

    // debitor data
    uniqueIdentifier
        .setEntityUniqueIdentifierType(position.get().getDebitor().getType().equals(1) ? StEntityUniqueIdentifierType.F
            : StEntityUniqueIdentifierType.G);
    uniqueIdentifier.setEntityUniqueIdentifierValue(position.get().getDebitor().getFiscalCode());
    debitor.setUniqueIdentifier(uniqueIdentifier);
    debitor.setFullName(position.get().getDebitor().getName());
    debitor.setStreetName(position.get().getDebitor().getAddress());
    debitor.setCivicNumber(position.get().getDebitor().getNumber());
    debitor.setPostalCode(position.get().getDebitor().getCap());
    debitor.setCity(position.get().getDebitor().getArea());
    debitor.setStateProvinceRegion(position.get().getDebitor().getProvince());
    debitor.setCountry(position.get().getDebitor().getCountry());
    debitor.setEMail(position.get().getDebitor().getEmail());

    // Transfer list
    transferList.getTransfer()
        .addAll(IntStream.range(0, option.get().getTransfers().size())
            .mapToObj(
                index -> getTransferResponse(option.get().getTransfers().get(index), index, request.getTransferType()))
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
