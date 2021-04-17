package it.gov.pagopa.hubpa.uploadpayments.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class PaymentPositionModel implements Serializable {

    private static final long serialVersionUID = -2851461971637130474L;

    private String organizationFiscalCode;
    private String companyName;
    private String officeName;
    private Integer status;
    private String description;
    private Long jobId;
    
    private List<PaymentOptionsModel> paymentOptions=new ArrayList<>();
}
