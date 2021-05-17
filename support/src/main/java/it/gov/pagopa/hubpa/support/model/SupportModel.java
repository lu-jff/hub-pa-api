package it.gov.pagopa.hubpa.support.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupportModel {

    private String fiscalCodeRp;
    private String municipality;
    private String region;
    private String province;
    private String certifiedmail;
    private String telephoneMunicipality;
    private String fullName;
    private String email;
    private String telephone;
    private String typeContact;
    private String platformCall;
    private LocalDate dateRequest;
    private String timeRequest;
}
