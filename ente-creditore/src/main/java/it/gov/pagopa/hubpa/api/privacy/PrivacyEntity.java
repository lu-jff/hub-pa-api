package it.gov.pagopa.hubpa.api.privacy;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Setter;


@Setter
@Entity
@Table(name = "privacy")
public class PrivacyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codiceFiscaleRefP")
    private String codiceFiscaleRefP;

    @Column(name = "dataAccettazione")
    private LocalDateTime dataAccettazione;

   


}
