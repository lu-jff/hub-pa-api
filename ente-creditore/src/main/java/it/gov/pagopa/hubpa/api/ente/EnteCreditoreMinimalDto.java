package it.gov.pagopa.hubpa.api.ente;

import java.sql.Date;

public class EnteCreditoreMinimalDto {

    // denominazioneEnte 2 Circolo Didattico ''San Giovanni Bosco''
    private String denominazioneEnte;

    // codiceFiscale 80012320729
    private String codiceFiscale;

    // dataAdesione 2015-12-14
    private Date dataAdesione;

    // dataEsercizio 2016-12-01
    private Date dataEsercizio;

    // auxDigit 0
    private Integer auxDigit;

    // codiceSegregazione NULL
    private String codiceSegregazione;

    // applicationCode 01
    private String applicationCode;

    // codiceInterbancario BMHKP
    private String codiceInterbancario;

    // idStazione 80185250588_01
    private String idStazione;

    // statoAssociazione Attiva
    private String statoAssociazione;

    // dataStatoAssociazione 2018-07-19
    private String dataStatoAssociazione;

    public String getDenominazioneEnte() {
        return denominazioneEnte;
    }

    public void setDenominazioneEnte(String myDenominazioneEnte) {
        this.denominazioneEnte = myDenominazioneEnte;
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
