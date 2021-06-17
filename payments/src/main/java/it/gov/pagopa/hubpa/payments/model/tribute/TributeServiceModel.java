package it.gov.pagopa.hubpa.payments.model.tribute;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TributeServiceModel implements Serializable {

    private static final long serialVersionUID = -9090133850672769597L;
    private static final String ibanRegex = "^(?:(?:IT|SM)\\d{2}[A-Z]\\d{22}|CY\\d{2}[A-Z]\\d{23}|NL\\d{2}[A-Z]{4}\\d{10}|LV\\d{2}[A-Z]{4}\\d{13}|(?:BG|BH|GB|IE)\\d{2}[A-Z]{4}\\d{14}|GI\\d{2}[A-Z]{4}\\d{15}|RO\\d{2}[A-Z]{4}\\d{16}|KW\\d{2}[A-Z]{4}\\d{22}|MT\\d{2}[A-Z]{4}\\d{23}|NO\\d{13}|(?:DK|FI|GL|FO)\\d{16}|MK\\d{17}|(?:AT|EE|KZ|LU|XK)\\d{18}|(?:BA|HR|LI|CH|CR)\\d{19}|(?:GE|DE|LT|ME|RS)\\d{20}|IL\\d{21}|(?:AD|CZ|ES|MD|SA)\\d{22}|PT\\d{23}|(?:BE|IS)\\d{24}|(?:FR|MR|MC)\\d{25}|(?:AL|DO|LB|PL)\\d{26}|(?:AZ|HU)\\d{27}|(?:GR|MU)\\d{28})$";

    private String fiscalCodePrimaryCreditor;
    private String fiscalCodeSecondaryCreditor;
    @Pattern(regexp = ibanRegex)
    private String ibanPrimary;
    @Pattern(regexp = ibanRegex)
    private String ibanSecondary;
    private String denomination;
    private BigDecimal percentageSecondary;
    private LocalDate dueDateUnique;
    private List<InstallmentModel> installments;
    @Pattern(regexp = ibanRegex)
    private String postalIbanPrimary;
    private String postalIbanHolderPrimary;
    private String postalAuthCodePrimary;
    @Pattern(regexp = ibanRegex)
    private String postalIbanSecondary;
    private String postalIbanHolderSecondary;
    private String postalAuthCodeSecondary;
}
