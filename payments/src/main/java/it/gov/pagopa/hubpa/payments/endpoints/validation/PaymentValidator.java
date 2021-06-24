package it.gov.pagopa.hubpa.payments.endpoints.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.gov.pagopa.hubpa.payments.endpoints.validation.exceptions.SoapValidationException;
import it.gov.pagopa.hubpa.payments.entity.PaymentOptions;
import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;
import it.gov.pagopa.hubpa.payments.enumeration.PaymentOptionStatusEnum;
import it.gov.pagopa.hubpa.payments.enumeration.PaymentStatusEnum;
import it.gov.pagopa.hubpa.payments.model.PaaErrorEnum;

@Component
public class PaymentValidator {

    @Value("${pt.id_dominio}")
    private String ptIdDominio;

    @Value("${pt.id_intermediario}")
    private String ptIdIntermediario;

    @Value("${pt.id_stazione}")
    private String ptIdStazione;

    public void isAuthorize(String ptIdDominioReq, String ptIdIntermediarioReq, String ptIdStazioneReq)
            throws SoapValidationException {

        if (!ptIdDominioReq.equals(ptIdDominio)) {
            throw new SoapValidationException(PaaErrorEnum.PAA_ID_DOMINIO_ERRATO, "ID dominio errato",
                    "L'idPA ricevuto non e' tra quelli configurati");
        }

        if (!ptIdIntermediarioReq.equals(ptIdIntermediario)) {
            throw new SoapValidationException(PaaErrorEnum.PAA_ID_INTERMEDIARIO_ERRATO, "IdBrokerPA errato",
                    "L'IdBrokerPA ricevuto non e' tra quelli configurati");
        }

        if (!ptIdStazioneReq.equals(ptIdStazione)) {
            throw new SoapValidationException(PaaErrorEnum.PAA_STAZIONE_INT_ERRATA, "IdStazione errato",
                    "L'IdStazione ricevuto non e' tra quelli configurati");
        }
    }

    public void isPayable(PaymentPosition position, PaymentOptions option) {

        if ((option == null || position == null)
                || (!position.getStatus().equals(PaymentStatusEnum.PUBBLICATO.getStatus())
                        && (!position.getStatus().equals(PaymentStatusEnum.PAGATO_PARZIALE.getStatus())))) {
            throw new SoapValidationException(PaaErrorEnum.PAA_PAGAMENTO_SCONOSCIUTO, "pagamento sconosciuto",
                    "L'id del pagamento ricevuto non esiste");
        }

        if (!option.getStatus().equals(PaymentOptionStatusEnum.NON_PAGATO.getStatus())) {
            throw new SoapValidationException(PaaErrorEnum.PAA_PAGAMENTO_DUPLICATO, "pagamento duplicato",
                    "L'id del pagamento ricevuto  e' duplicato");
        }

    }
}
