package it.gov.pagopa.hubpa.api.iban;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import it.gov.pagopa.hubpa.api.annotation.validation.IvaCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IbanDto {

  private Long id;
  private String codAmm;
  @NotEmpty
  private String denominazioneEnte;
  @IvaCode
  @NotEmpty
  private String codiceFiscale;
  @NotEmpty
  private String iban;
  private String idSellerBank;
  private String stato;
  private Date dataAttivazione;
  private String descrizione;
}
