package it.gov.pagopa.hubpa.payments.model.csv;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.gov.pagopa.hubpa.payments.annotation.validation.FiscalCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CsvRowModel implements Serializable {

    private static final long serialVersionUID = -8752446710569207804L;
    @Size(max=70)
    @NotEmpty
    private String name;
    @NotEmpty
    @Size(max=70)
    private String surname;
    @NotEmpty
    @FiscalCode
    private String fiscalCode;
    private Integer type;
    @NotEmpty
    @Size(max=19)
    private String phone;
    @NotEmpty
    @Size(max=70)
    private String address;
    @NotEmpty
    @Size(max=16)
    private String number;
    @NotEmpty
    @Size(max=35)
    private String area;
    @NotEmpty
    @Size(max=35)
    private String province;
    @NotEmpty
    @Size(max=16)
    private String cap;
    @NotEmpty
    @Size(max=2)
    private String country;
    @NotEmpty
    @Size(max=256)
    private String email;
    @Size(max=50)
    private String idTenant;
    @NotNull
    private BigDecimal amount;
    @NotEmpty
    @Size(max=60)
    private String reason;
  
}
