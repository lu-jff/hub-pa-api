package it.gov.pagopa.hubpa.payments.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentsModel implements Serializable {

    private static final long serialVersionUID = -2296175704908103176L;
    
    private List<DebitorModel> debitors=new ArrayList<>();

}
