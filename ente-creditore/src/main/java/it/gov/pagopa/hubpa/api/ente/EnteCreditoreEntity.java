package it.gov.pagopa.hubpa.api.ente;

import java.sql.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/* Schema and sample data extracted from Portale Delle Adesioni

denominazioneEnte                   2 Circolo Didattico ''San Giovanni Bosco''
codAmm                              istsc_baee15800a
codiceFiscale                       80012320729
dataAdesione                        2015-12-14
codiceGs1Gln                        NULL
cognomeRp                           De Santis
nomeRp                              Paolo
codiceFiscaleRp                     DSNPLA64L01A252Y
mailRp                              paolo.desantis@istruzione.it
telefonoRp                          0658492389
cellulareRp                         3403608985
tipoIntermediazione                 INTERMEDIATA
denominazioneIntermediarioPartner   Ministero dell'Istruzione, Ministero dell'Universita' e della Ricerca
cognomeRt                           De Santis
nomeRt                              Paolo
codiceFiscaleRt                     DSNPLA64L01A252Y
mailRt                              paolo.desantis@istruzione.it
telefonoRt                          0658492389
cellulareRt                         3403608985
statoConnessione                    400
modello                             MODELLO 3
dataCollaudo                        NULL
dataPreEsercizio                    NULL
dataEsercizio                       2016-12-01
auxDigit                            0
codiceSegregazione                  NULL
applicationCode                     01
codiceInterbancario                 BMHKP
idStazione                          80185250588_01
statoAssociazione                   Attiva
dataStatoAssociazione               2018-07-19

*/

@Getter
@Setter
@Entity
@Table(name = "ente")
public class EnteCreditoreEntity {

    @Id
    @GeneratedValue(generator = "ente_generator")
    @SequenceGenerator(name = "ente_generator", sequenceName = "ente_sequence", initialValue = 1)
    private Long id;

    // denominazioneEnte 2 Circolo Didattico ''San Giovanni Bosco''
    @Column(name = "denominazioneEnte", nullable = false)
    private String denominazioneEnte;

    // codAmm istsc_baee15800a (Foreign Key)
    @Column(name = "codAmm", nullable = false)
    private String codAmm;

    // codiceFiscale 80012320729
    @Column(name = "codiceFiscale")
    private String codiceFiscale;

    // dataAdesione 2015-12-14
    @Column(name = "dataAdesione")
    private Date dataAdesione;

    // codiceGs1Gln NULL
    @Column(name = "codiceGs1Gln")
    private String codiceGs1Gln;

    // cognomeRp De Santis
    @Column(name = "cognomeRp")
    private String cognomeRp;

    // nomeRp Paolo
    @Column(name = "nomeRp")
    private String nomeRp;

    // codiceFiscaleRp DSNPLA64L01A252Y
    @Column(name = "codiceFiscaleRp")
    private String codiceFiscaleRp;

    // mailRp paolo.desantis@istruzione.it
    @Column(name = "mailRp")
    private String mailRp;

    // telefonoRp 0658492389
    @Column(name = "telefonoRp")
    private String telefonoRp;

    // cellulareRp 3403608985
    @Column(name = "cellulareRp")
    private String cellulareRp;

    // tipoIntermediazione INTERMEDIATA
    @Column(name = "tipoIntermediazione")
    private String tipoIntermediazione;

    // denominazioneIntermediarioPartner Ministero dell'Istruzione, Ministero
    @Column(name = "denominazioneIntermediarioPartner")
    private String denominazioneIntermediarioPartner;

    // cognomeRp De Santis
    @Column(name = "cognomeRt")
    private String cognomeRt;

    // nomeRp Paolo
    @Column(name = "nomeRt")
    private String nomeRt;

    // codiceFiscaleRp DSNPLA64L01A252Y
    @Column(name = "codiceFiscalaRt")
    private String codiceFiscaleRt;

    // mailRp paolo.desantis@istruzione.it
    @Column(name = "mailRt")
    private String mailRt;

    // telefonoRp 0658492389
    @Column(name = "telefonoRt")
    private String telefonoRt;

    // cellulareRp 3403608985
    @Column(name = "cellulareRt")
    private String cellulareRt;

    // statoConnessione 400
    @Column(name = "statoConnessione")
    private Integer statoConnessione;

    // dataCollaudo NULL
    @Column(name = "dataCollaudo")
    private Date dataCollaudo;

    // dataPreEsercizio NULL
    @Column(name = "dataPreEsercizio")
    private Date dataPreEsercizio;

    // dataEsercizio 2016-12-01
    @Column(name = "dataEsercizio")
    private Date dataEsercizio;

    // auxDigit 0
    @Column(name = "auxDigit")
    private Integer auxDigit;

    // codiceSegregazione NULL
    @Column(name = "codiceSegregazione")
    private String codiceSegregazione;

    // applicationCode 01
    @Column(name = "applicationCode")
    private String applicationCode;

    // codiceInterbancario BMHKP
    @Column(name = "codiceInterbancario")
    private String codiceInterbancario;

    // idStazione 80185250588_01
    @Column(name = "idStazione")
    private String idStazione;

    // statoAssociazione Attiva
    @Column(name = "statoAssociazione")
    private String statoAssociazione;

    // dataStatoAssociazione 2018-07-19
    @Column(name = "dataStatoAssociazione")
    private String dataStatoAssociazione;
}
