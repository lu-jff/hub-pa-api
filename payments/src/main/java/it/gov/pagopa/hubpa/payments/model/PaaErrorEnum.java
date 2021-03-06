package it.gov.pagopa.hubpa.payments.model;

import lombok.Getter;

@Getter
public enum PaaErrorEnum {
  PAA_ID_DOMINIO_ERRATO("PAA_ID_DOMINIO_ERRATO"), PAA_ID_INTERMEDIARIO_ERRATO("PAA_ID_INTERMEDIARIO_ERRATO"),
  PAA_STAZIONE_INT_ERRATA("PAA_STAZIONE_INT_ERRATA"), PAA_PAGAMENTO_SCONOSCIUTO("PAA_PAGAMENTO_SCONOSCIUTO"),
  PAA_PAGAMENTO_DUPLICATO("PAA_PAGAMENTO_DUPLICATO"), PAA_SEMANTICA("PAA_SEMANTICA");

  private final String value;

  PaaErrorEnum(String value) {
    this.value = value;
  }
}
