package it.gov.pagopa.hubpa.api.pa;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import it.gov.pagopa.hubpa.api.annotation.validation.IvaCode;
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
  @IvaCode
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
