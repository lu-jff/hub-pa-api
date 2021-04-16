package it.gov.pagopa.hubpa.api.pa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaDto {

  private Long id;
  private String codAmm;
  private String desAmm;
  private String tipologiaIstat;
  private String codiceFiscale;
  private String indirizzo;
  private String comune;
  private String cap;
  private String provincia;
  private String emailCertificata;
  private String sitoIstituzionale;
}
