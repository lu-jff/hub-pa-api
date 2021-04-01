package it.gov.pagopa.hubpa.servicemanagement.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstallmentModel {

    private LocalDate dueDate;
    private Integer percentagePrimary;
    private Integer percentageSecondary;
}
