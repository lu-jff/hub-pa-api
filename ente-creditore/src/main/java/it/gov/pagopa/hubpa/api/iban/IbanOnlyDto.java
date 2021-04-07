package it.gov.pagopa.hubpa.api.iban;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IbanOnlyDto {
  private String iban;
  private String descrizione;
}