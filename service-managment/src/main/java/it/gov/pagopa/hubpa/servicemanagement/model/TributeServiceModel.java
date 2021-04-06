package it.gov.pagopa.hubpa.servicemanagement.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TributeServiceModel {
    private Long idPrimaryCreditor;
    private Long idSecondaryCreditor;
    private String ibanPrimary;
    private String ibanSecondary;
    private String denomination;
    private Integer percentageSecondary;
    private LocalDate dueDateUnique;
    List<InstallmentModel> installments;

}