package it.gov.pagopa.hubpa.servicemanagement.model;

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
