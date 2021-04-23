package it.gov.pagopa.hubpa.payments.model.tribute;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstallmentModel {

    private LocalDate dueDate;
    private BigDecimal percentagePrimary;
    private BigDecimal percentageSecondary;
}
