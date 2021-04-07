
package it.gov.pagopa.hubpa.api.iban;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IbanService {

  @Autowired
  private IbanRepository ibanRepository;

  public List<IbanEntity> getByEnteCreditore(String codiceFiscaleEc) {
    return ibanRepository.findIbanByCodiceFiscale(codiceFiscaleEc);
  }

  public IbanEntity create(IbanEntity ibanEntity) {
    return ibanRepository.save(ibanEntity);

  }
}