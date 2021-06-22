package it.gov.pagopa.hubpa.payments.model.ente;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    private Date dataStatoAssociazione;
}
