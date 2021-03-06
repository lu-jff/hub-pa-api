package it.gov.pagopa.hubpa.api.ente;

import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import it.gov.pagopa.hubpa.api.annotation.validation.FiscalCode;
import it.gov.pagopa.hubpa.api.annotation.validation.IvaCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnteCreditoreDto {

    private Long id;
    @NotEmpty
    private String denominazioneEnte;
    private String codAmm;
    @IvaCode
    @NotEmpty
    private String codiceFiscale;
    private Date dataAdesione;
    private String codiceGs1Gln;
    private String cognomeRp;
    private String nomeRp;
    @FiscalCode
    @NotEmpty
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
    private String modello;
    private Date dataCollaudo;
    private Date dataPreEsercizio;
    private Date dataEsercizio;
    private Integer auxDigit;
    private String codiceSegregazione;
    private String applicationCode;
    private String codiceInterbancario;
    private String idStazione;
    private String statoAssociazione;
    private Date dataStatoAssociazione;
    private LocalDateTime dataStatoAssociazione2;
}
