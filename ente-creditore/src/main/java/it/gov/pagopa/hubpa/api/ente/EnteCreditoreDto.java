package it.gov.pagopa.hubpa.api.ente;

import java.sql.Date;

import javax.persistence.*;

public class EnteCreditoreDto {

    private Long id;
    private String denominazioneEnte;
    private String codAmm;
    private String codiceFiscale;
    private Date dataAdesione;
    private String codiceGs1Gln;
    private String cognomeRp;
    private String nomeRp;
    private String codiceFiscaleRp;
    private String mailRp;
    private String telefonoRp;
    private String cellulareRp;
    private String tipoIntermediazione;
    private String denominazioneIntermediarioPartner;
    private String cognomeRt;
    private String nomeRt;
    private String codiceFiscaleRt;
    private String mailRt;
    private String telefonoRt;
    private String cellulareRt;
    private Integer statoConnessione;
    private Date dataCollaudo;
    private Date dataPreEsercizio;
    private Date dataEsercizio;
    private Integer auxDigit;
    private String codiceSegregazione;
    private String applicationCode;
    private String codiceInterbancario;
    private String idStazione;
    private String statoAssociazione;
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
