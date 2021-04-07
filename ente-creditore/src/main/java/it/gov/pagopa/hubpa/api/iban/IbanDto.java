package it.gov.pagopa.hubpa.api.iban;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IbanDto {

  private Long id;
  private String codAmm;
  private String denominazioneEnte;
  private String codiceFiscale;
  private String iban;
  private String idSellerBank;
  private String stato;
  private Date dataAttivazione;
  private String descrizione;
}
