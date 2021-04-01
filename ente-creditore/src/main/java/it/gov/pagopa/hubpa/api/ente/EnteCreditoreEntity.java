package it.gov.pagopa.hubpa.api.ente;

import java.sql.Date;

import javax.persistence.*;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenominazioneEnte() {
        return denominazioneEnte;
    }

    public void setDenominazioneEnte(String myDenominazioneEnte) {
        this.denominazioneEnte = myDenominazioneEnte;
    }

    public String getCodAmm() {
        return codAmm;
    }

    public void setCodAmm(String myCodAmm) {
        this.codAmm = myCodAmm;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String myCodiceFiscale) {
        this.codiceFiscale = myCodiceFiscale;
    }

    public Date getDataAdesione() {
        return dataAdesione;
    }

    public void setDataAdesione(Date myDataAdesione) {
        this.dataAdesione = myDataAdesione;
    }

    public String getCodiceGs1Gln() {
        return codiceGs1Gln;
    }

    public void setCodiceGs1Gln(String myCodiceGs1Gln) {
        this.codiceGs1Gln = myCodiceGs1Gln;
    }

    public String getCognomeRp() {
        return cognomeRp;
    }

    public void setCognomeRp(String myCognomeRp) {
        this.cognomeRp = myCognomeRp;
    }

    public String getNomeRp() {
        return nomeRp;
    }

    public void setNomeRp(String myNomeRp) {
        this.nomeRp = myNomeRp;
    }

    public String getCodiceFiscaleRp() {
        return codiceFiscaleRp;
    }

    public void setCodiceFiscaleRp(String myCodiceFiscaleRp) {
        this.codiceFiscaleRp = myCodiceFiscaleRp;
    }

    public String getMailRp() {
        return mailRp;
    }

    public void setMailRp(String myMailRp) {
        this.mailRp = myMailRp;
    }

    public String getTelefonoRp() {
        return telefonoRp;
    }

    public void setTelefonoRp(String myTelefonoRp) {
        this.telefonoRp = myTelefonoRp;
    }

    public String getCellulareRp() {
        return cellulareRp;
    }

    public void setCellulareRp(String myCellulareRp) {
        this.cellulareRp = myCellulareRp;
    }

    public String getTipoIntermediazione() {
        return tipoIntermediazione;
    }

    public void setTipoIntermediazione(String myTipoIntermediazione) {
        this.tipoIntermediazione = myTipoIntermediazione;
    }

    public String getDenominazioneIntermediarioPartner() {
        return denominazioneIntermediarioPartner;
    }

    public void setDenominazioneIntermediarioPartner(String myDenominazioneIntermediarioPartner) {
        this.denominazioneIntermediarioPartner = myDenominazioneIntermediarioPartner;
    }

    public String getCognomeRt() {
        return cognomeRt;
    }

    public void setCognomeRt(String myCognomeRt) {
        this.cognomeRt = myCognomeRt;
    }

    public String getNomeRt() {
        return nomeRt;
    }

    public void setNomeRt(String myNomeRt) {
        this.nomeRt = myNomeRt;
    }

    public String getCodiceFiscaleRt() {
        return codiceFiscaleRt;
    }

    public void setCodiceFiscaleRt(String myCodiceFiscaleRt) {
        this.codiceFiscaleRt = myCodiceFiscaleRt;
    }

    public String getMailRt() {
        return mailRt;
    }

    public void setMailRt(String myMailRt) {
        this.mailRt = myMailRt;
    }

    public String getTelefonoRt() {
        return telefonoRt;
    }

    public void setTelefonoRt(String myTelefonoRt) {
        this.telefonoRt = myTelefonoRt;
    }

    public String getCellulareRt() {
        return cellulareRt;
    }

    public void setCellulareRt(String myCellulareRt) {
        this.cellulareRt = myCellulareRt;
    }

    public Integer getStatoConnessione() {
        return statoConnessione;
    }

    public void setStatoConnessione(Integer myStatoConnessione) {
        this.statoConnessione = myStatoConnessione;
    }

    public Date getDataCollaudo() {
        return dataCollaudo;
    }

    public void setDataCollaudo(Date myDataCollaudo) {
        this.dataCollaudo = myDataCollaudo;
    }

    public Date getDataPreEsercizio() {
        return dataPreEsercizio;
    }

    public void setDataPreEsercizio(Date myDataPreEsercizio) {
        this.dataPreEsercizio = myDataPreEsercizio;
    }

    public Date getDataEsercizio() {
        return dataEsercizio;
    }

    public void setDataEsercizio(Date myDataEsercizio) {
        this.dataEsercizio = myDataEsercizio;
    }

    public Integer getAuxDigit() {
        return auxDigit;
    }

    public void setAuxDigit(Integer myAuxDigit) {
        this.auxDigit = myAuxDigit;
    }

    public String getCodiceSegregazione() {
        return codiceSegregazione;
    }

    public void setCodiceSegregazione(String myCodiceSegregazione) {
        this.codiceSegregazione = myCodiceSegregazione;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String myApplicationCode) {
        this.applicationCode = myApplicationCode;
    }

    public String getCodiceInterbancario() {
        return codiceInterbancario;
    }

    public void setCodiceInterbancario(String myCodiceInterbancario) {
        this.codiceInterbancario = myCodiceInterbancario;
    }

    public String getIdStazione() {
        return idStazione;
    }

    public void setIdStazione(String myIdStazione) {
        this.idStazione = myIdStazione;
    }

    public String getStatoAssociazione() {
        return statoAssociazione;
    }

    public void setStatoAssociazione(String myStatoAssociazione) {
        this.statoAssociazione = myStatoAssociazione;
    }

    public String getDataStatoAssociazione() {
        return dataStatoAssociazione;
    }

    public void setDataStatoAssociazione(String myDataStatoAssociazione) {
        this.dataStatoAssociazione = myDataStatoAssociazione;
    }
}
