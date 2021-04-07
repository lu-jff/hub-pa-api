package it.gov.pagopa.hubpa.api.iban;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "iban")
public class IbanEntity {

  @Id
  @GeneratedValue(generator = "iban_generator")
  @SequenceGenerator(name = "iban_generator", sequenceName = "iban_sequence", initialValue = 1)
  private Long id;

  @Column(name = "codAmm")
  private String codAmm;

  @Column(name = "denominazioneEnte")
  private String denominazioneEnte;

  @Column(name = "codiceFiscale")
  private String codiceFiscale;

  @Column(name = "iban")
  private String iban;

  @Column(name = "idSellerBank")
  private String idSellerBank;

  @Column(name = "stato")
  private String stato;

  @Column(name = "dataAttivazione")
  private Date dataAttivazione;

  @Column(name = "descrizione")
  private String descrizione;
}
