package it.gov.pagopa.hubpa.servicemanagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TributeServiceModel {

    private String fiscalCodePrimaryCreditor;
    private String fiscalCodeSecondaryCreditor;
    private String ibanPrimary;
    private String ibanSecondary;
    private String denomination;
    private BigDecimal percentageSecondary;
    private LocalDate dueDateUnique;
    List<InstallmentModel> installments;
    private String postalIbanPrimary;
    private String postalIbanHolderPrimary;
    private String postalAuthCodePrimary;
    private String postalIbanSecondary;
    private String postalIbanHolderSecondary;
    private String postalAuthCodeSecondary;
}
