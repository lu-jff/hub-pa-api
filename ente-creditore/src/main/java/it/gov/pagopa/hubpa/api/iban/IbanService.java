
package it.gov.pagopa.hubpa.api.iban;

import java.util.List;

import it.gov.pagopa.hubpa.api.enumeration.IbanModeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IbanService {

  @Value("${postalAbi}")
  private String postalAbi;

  @Autowired
  private IbanRepository ibanRepository;

  public List<IbanEntity> getByEnteCreditore(String codiceFiscaleEc, String ibanMode) {
    List<IbanEntity> ibanList = ibanRepository.findIbanByCodiceFiscale(codiceFiscaleEc);
    if (IbanModeEnum.BANKING.getValue().equals(ibanMode)) {
      ibanList.removeIf(p -> p.getIban().startsWith(postalAbi, 5));
    } else if(IbanModeEnum.POSTAL.getValue().equals(ibanMode)) {
      ibanList.removeIf(p -> !p.getIban().startsWith(postalAbi, 5));
    }
    return ibanList;
  }

  public IbanEntity create(IbanEntity ibanEntity) {
    return ibanRepository.save(ibanEntity);

  }
}
