package it.gov.pagopa.hubpa.payments.model.ente;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaDto {

  private Long id;
  private String codAmm;
  @NotBlank
  private String desAmm;
  private String tipologiaIstat;
  @NotBlank
  private String codiceFiscale;
  private String indirizzo;
  private String comune;
  private String cap;
  private String provincia;
  @Email
  private String emailCertificata;
  private String sitoIstituzionale;
}
