package it.gov.pagopa.hubpa.support.mock;

import it.gov.pagopa.hubpa.support.entity.Support;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class SupportMock {
    public static Support getMock(){
        Support model = new Support();
        model.setId(1L);
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
        model.setMailSentDate(LocalDateTime.now());
        return model;
    }

    public static Support getMockCall(){
        Support model = new Support();
        model.setId(1L);
        model.setFiscalCodeRp("");
        model.setMunicipality("");
        model.setRegion("");
        model.setProvince("");
        model.setCertifiedMail("");
        model.setTelephoneMunicipality("");
        model.setFullName("");
        model.setEmail("");
        model.setTelephone("");
        model.setTypeContact("CALL");
        model.setPlatformCall("");
        model.setDateRequest(LocalDate.now());
        model.setTimeRequest("");
        model.setMailSentDate(LocalDateTime.now());
        return model;
    }
}
