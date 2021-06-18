package it.gov.pagopa.hubpa.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import it.gov.pagopa.hubpa.api.enumeration.IbanModeEnum;
import it.gov.pagopa.hubpa.api.iban.IbanDto;
import it.gov.pagopa.hubpa.api.iban.IbanEntity;
import it.gov.pagopa.hubpa.api.iban.IbanRepository;
import it.gov.pagopa.hubpa.api.iban.IbanService;

@ExtendWith(MockitoExtension.class)
class ApiServiceTest {

	
	@Mock
	private IbanRepository ibanRepository;
	
	@InjectMocks
	private IbanService ibanService;

	private ModelMapper modelMapper = new ModelMapper();

	@Test
	void shouldGetIban1() throws Exception {
	    ReflectionTestUtils.setField(ibanService, "postalAbi", "07601");
	    List<IbanEntity> listIban=new ArrayList<>();
	    	listIban.add(buildIbanEntity());
	    when(ibanRepository.findIbanByCodiceFiscale(any(String.class))).thenReturn(listIban);
	    List<IbanEntity> aa = ibanService.getByEnteCreditore("12345678901", IbanModeEnum.FULL.getValue());
	    assertThat(aa).isNotEmpty();
	}
	
	@Test
	void shouldGetIban2() throws Exception {
	    ReflectionTestUtils.setField(ibanService, "postalAbi", "07601");
	    List<IbanEntity> listIban=new ArrayList<>();
	    	listIban.add(buildIbanEntity());
	    when(ibanRepository.findIbanByCodiceFiscale(any(String.class))).thenReturn(listIban);
	    List<IbanEntity> aa = ibanService.getByEnteCreditore("12345678901", IbanModeEnum.POSTAL.getValue());
	    assertThat(aa).isEmpty();
	}
	
	@Test
	void shouldGetIban3() throws Exception {
	    ReflectionTestUtils.setField(ibanService, "postalAbi", "07601");
	    List<IbanEntity> listIban=new ArrayList<>();
	    	listIban.add(buildIbanEntity());
	    when(ibanRepository.findIbanByCodiceFiscale(any(String.class))).thenReturn(listIban);
	    List<IbanEntity> aa = ibanService.getByEnteCreditore("12345678901", IbanModeEnum.BANKING.getValue());
	    assertThat(aa).isNotEmpty();
	}
	
	@Test
	void shouldGetIban4() throws Exception {
	    ReflectionTestUtils.setField(ibanService, "postalAbi", "07601");
	    List<IbanEntity> listIban=new ArrayList<>();
	    	listIban.add(buildIbanEntity2());
	    when(ibanRepository.findIbanByCodiceFiscale(any(String.class))).thenReturn(listIban);
	    List<IbanEntity> aa = ibanService.getByEnteCreditore("12345678901", IbanModeEnum.POSTAL.getValue());
	    assertThat(aa).isNotEmpty();
	}
	
	@Test
	void shouldPostIban() throws Exception {
	    
	    IbanEntity ibanEntity=buildIbanEntity();
	    when(ibanRepository.save(any(IbanEntity.class))).thenReturn(ibanEntity);
	    IbanEntity iIbanEntitySaved = ibanService.create(ibanEntity);
	    assertThat(iIbanEntitySaved.getDenominazioneEnte()).isEqualTo("Citta' Metropolitana di Palermo");
	}

	private IbanEntity buildIbanEntity() throws ParseException {
	    	IbanDto myIbanDto = buildIbanOK();
	    	IbanEntity myIbanEntity = modelMapper.map(myIbanDto, IbanEntity.class);

		return myIbanEntity;
	}
	private IbanEntity buildIbanEntity2() throws ParseException {
	    	IbanDto myIbanDto = buildIbanOK2();
	    	IbanEntity myIbanEntity = modelMapper.map(myIbanDto, IbanEntity.class);

		return myIbanEntity;
	}

	private IbanDto buildIbanOK() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		IbanDto ibanDto = new IbanDto();
		ibanDto.setId(0L);
		ibanDto.setDenominazioneEnte("Citta' Metropolitana di Palermo");
		ibanDto.setCodiceFiscale("80021470820");
		ibanDto.setCodAmm("p_pa");
		ibanDto.setIban("IT85Z0891104600000018459909");
		ibanDto.setIdSellerBank("CT000159");
		ibanDto.setStato("ATTIVO");
		ibanDto.setDataAttivazione(format.parse("2016-12-31"));
		ibanDto.setDescrizione("Inserito_AgID");

		return ibanDto;

	}
	private IbanDto buildIbanOK2() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		IbanDto ibanDto = new IbanDto();
		ibanDto.setId(0L);
		ibanDto.setDenominazioneEnte("Citta' Metropolitana di Palermo");
		ibanDto.setCodiceFiscale("80021470820");
		ibanDto.setCodAmm("p_pa");
		ibanDto.setIban("IT85Z0760104600000018459909");
		ibanDto.setIdSellerBank("CT000159");
		ibanDto.setStato("ATTIVO");
		ibanDto.setDataAttivazione(format.parse("2016-12-31"));
		ibanDto.setDescrizione("Inserito_AgID");

		return ibanDto;

	}

	

	

	

}
