package it.gov.pagopa.hubpa.uploadpayments.model.servicemanagement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstallmentModel implements Serializable {

    private static final long serialVersionUID = -701132329016520327L;
    
    private LocalDate dueDate;
    private BigDecimal percentagePrimary;
    private BigDecimal percentageSecondary;
}
