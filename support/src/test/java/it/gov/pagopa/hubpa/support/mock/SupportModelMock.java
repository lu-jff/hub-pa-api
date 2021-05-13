package it.gov.pagopa.hubpa.support.mock;

import it.gov.pagopa.hubpa.support.model.SupportModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SupportModelMock {
    public static SupportModel getMock(){
        SupportModel model = new SupportModel();
        model.setFiscalCodeRp("");
        model.setMunicipality("");
        model.setRegion("");
        model.setProvince("");
        model.setCertifiedMail("");
        model.setTelephoneMunicipality("");
        model.setFullName("");
        model.setEmail("");
        model.setTelephone("");
        model.setTypeContact("");
        model.setPlatformCall("");
        model.setDateRequest(LocalDate.now());
        model.setTimeRequest("");
        return model;
    }
}
