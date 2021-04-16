package it.gov.pagopa.hubpa.api.pa;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "pa")
public class PaEntity {

  @Id
  @GeneratedValue(generator = "pa_generator")
  @SequenceGenerator(name = "pa_generator", sequenceName = "pa_sequence", initialValue = 1)
  private Long id;

  @Column(name = "codAmm")
  private String codAmm;

  @Column(name = "desAmm")
  private String desAmm;

  @Column(name = "tipologiaIstat")
  private String tipologiaIstat;

  @Column(name = "codiceFiscale")
  private String codiceFiscale;

  @Column(name = "indirizzo")
  private String indirizzo;

  @Column(name = "comune")
  private String comune;

  @Column(name = "cap")
  private String cap;

  @Column(name = "provincia")
  private String provincia;

  @Column(name = "emailCertificata")
  private String emailCertificata;

  @Column(name = "sitoIstituzionale")
  private String sitoIstituzionale;

}
