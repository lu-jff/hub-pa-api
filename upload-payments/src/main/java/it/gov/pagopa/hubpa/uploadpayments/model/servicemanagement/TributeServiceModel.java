package it.gov.pagopa.hubpa.uploadpayments.model.servicemanagement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TributeServiceModel implements Serializable {

    private static final long serialVersionUID = 7425291399402275357L;
    
    private Long idPrimaryCreditor;
    private Long idSecondaryCreditor;
    private String fiscalCodePrimaryCreditor;
    private String fiscalCodeSecondaryCreditor;
    private String ibanPrimary;
    private String ibanSecondary;
    private String denomination;
    private BigDecimal percentageSecondary;
    private LocalDate dueDateUnique;
    List<InstallmentModel> installments;

}
