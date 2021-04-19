package it.gov.pagopa.hubpa.uploadpayments.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentJobModel {

    private String fiscalCode;
    private String fileName;
    private LocalDateTime insertDate;
    private LocalDateTime elaborationDate;
    private Integer nRecordFound;
    private Integer nRecordAdded;
    private Integer status;
}
