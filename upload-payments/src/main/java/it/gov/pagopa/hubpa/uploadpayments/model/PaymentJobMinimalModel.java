package it.gov.pagopa.hubpa.uploadpayments.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentJobMinimalModel {

    private Integer nRecordFound;
    private Integer nRecordAdded;
    private Integer nRecordWarning;
    private Integer status;
}
