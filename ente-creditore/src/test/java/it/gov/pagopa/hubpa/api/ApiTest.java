package it.gov.pagopa.hubpa.api;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import it.gov.pagopa.hubpa.api.config.Config;
import it.gov.pagopa.hubpa.api.ente.EnteCreditoreDto;
import it.gov.pagopa.hubpa.api.ente.EnteCreditoreEntity;
import it.gov.pagopa.hubpa.api.ente.EnteCreditoreService;
import it.gov.pagopa.hubpa.api.ente.RefpIntrospect;
import it.gov.pagopa.hubpa.api.ente.RefpSpid;
import it.gov.pagopa.hubpa.api.iban.IbanDto;
import it.gov.pagopa.hubpa.api.iban.IbanEntity;
import it.gov.pagopa.hubpa.api.iban.IbanService;
import it.gov.pagopa.hubpa.api.pa.PaDto;
import it.gov.pagopa.hubpa.api.pa.PaEntity;
import it.gov.pagopa.hubpa.api.pa.PaService;
import it.gov.pagopa.hubpa.api.privacy.PrivacyEntity;
import it.gov.pagopa.hubpa.api.privacy.PrivacyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.modelmapper.ModelMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.json.JsonMapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = { EnteCreditoreMicroService.class, Config.class })
class ApiTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RestTemplate restTemplate;

	@MockBean
	private EnteCreditoreService enteCreditoreService;

	@MockBean
	private IbanService ibanService;

	@MockBean
	private PaService paService;

	@MockBean
	private PrivacyService privacyService;

	@Value("${auth.introspect.path}")
	private String introspectPath;

	private JsonMapper jsonMapper = new JsonMapper();
	private ModelMapper modelMapper = new ModelMapper();

	@Test
	void shouldGetEnte() throws Exception {
		mockMvc.perform(get("/ente/refp/tttt")).andExpect(status().isOk());
	}

	@Test
	void shouldNotGetEnte() throws Exception {
		mockMvc.perform(get("/ente/xxx/tttt")).andExpect(status().is(404));
	}

	@Test
	void shouldPostEnte() throws Exception {
		EnteCreditoreDto goodEc = buildEnteCreditoreOK();
		// Mock service response
		when(enteCreditoreService.create(any(EnteCreditoreEntity.class)))
				.thenReturn(modelMapper.map(goodEc, EnteCreditoreEntity.class));
		jsonMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		String myJson = jsonMapper.writeValueAsString(goodEc);
		mockMvc.perform(post("/ente").content(myJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void shouldGetIban() throws Exception {
		mockMvc.perform(get("/ente/tttt/1/iban")).andExpect(status().isOk());
	}
	
	@Test
	void shouldGetIbanNull() throws Exception {
	    	when(ibanService.getByEnteCreditore(any(String.class),any(String.class))).thenReturn(null);
		mockMvc.perform(get("/ente/tttt/1/iban")).andExpect(status().isOk());
	}

	@Test
	void shouldNotGetIban() throws Exception {
		mockMvc.perform(get("/ente/tttt/ibpn")).andExpect(status().is(404));
	}
	@Test
	void shouldGetIban2() throws Exception {
	    	List<IbanEntity> listIban=new ArrayList<>();
	    	listIban.add(buildIbanEntity());
	    	when(ibanService.getByEnteCreditore(any(String.class),any(String.class))).thenReturn(listIban);
		mockMvc.perform(get("/ente/tttt/1/iban")).andExpect(status().isOk());
	}
	
	
	@Test
	void shouldPostIban() throws Exception {
		IbanDto goodIban = buildIbanOK();
		// Mock service response
		when(ibanService.create(any(IbanEntity.class))).thenReturn(modelMapper.map(goodIban, IbanEntity.class));
		jsonMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		String myJson = jsonMapper.writeValueAsString(goodIban);
		System.out.println(myJson);
		mockMvc.perform(post("/ente/iban").content(myJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void shouldGetPa() throws Exception {
		mockMvc.perform(get("/ente/pa")).andExpect(status().isOk());
	}

	@Test
	void shouldNotGetPa() throws Exception {
		mockMvc.perform(get("/ente/pax")).andExpect(status().is(404));
	}

	@Test
	void shouldPostPa() throws Exception {
		PaDto goodPa = buildPaOK();
		// Mock service response
		when(paService.create(any(PaEntity.class))).thenReturn(modelMapper.map(goodPa, PaEntity.class));
		String myJson = jsonMapper.writeValueAsString(goodPa);
		System.out.println(myJson);
		mockMvc.perform(post("/ente/pa").content(myJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void shouldGetPrivacy() throws Exception {
		mockMvc.perform(get("/privacy/refp/tttt")).andExpect(status().isOk());
		when(privacyService.countByRefP(any(String.class))).thenReturn(1l);
		mockMvc.perform(get("/privacy/refp/tttt")).andExpect(status().isOk());

	}

	@Test
	void shouldNotGetPrivacy() throws Exception {
		mockMvc.perform(get("/privacy/refpp/tttt")).andExpect(status().is(404));
	}

	@Test
	void shouldPostPrivacy() throws Exception {
		PaDto goodPa = buildPaOK();
		// Mock service response
		when(privacyService.create(any(PrivacyEntity.class))).thenReturn(buildPrivacyOK());
		String myJson = jsonMapper.writeValueAsString(goodPa);

		mockMvc.perform(post("/privacy/MRCFPFPFP").content(myJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void shouldNotPostPrivacy() throws Exception {
		PaDto goodPa = buildPaOK();
		// Mock service response
		when(privacyService.create(any(PrivacyEntity.class))).thenReturn(null);
		String myJson = jsonMapper.writeValueAsString(goodPa);

		mockMvc.perform(post("/privacy/MRCFPFPFP").content(myJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	void shouldRetrieveRbacAuth() throws Exception {

		RefpIntrospect myRefpIntro = buildGoodRefpFromIntrospect();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("fake-token");

		when(restTemplate.exchange(ArgumentMatchers.eq(introspectPath), ArgumentMatchers.eq(HttpMethod.POST),
				ArgumentMatchers.any(), ArgumentMatchers.<Class<RefpIntrospect>>any()))
						.thenReturn(new ResponseEntity<RefpIntrospect>(myRefpIntro, HttpStatus.OK));
		when(enteCreditoreService.getByRefP(any(String.class))).thenReturn(buildEnteCreditoreEntity());

		mockMvc.perform(get("/rbac").headers(headers)).andExpect(status().is2xxSuccessful());

	}

	@Test
	void shouldRetrieveRbacNotAuthWhenIntrospectFails() throws Exception {

		RefpIntrospect myRefpIntro = buildGoodRefpFromIntrospect();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("fake-token");

		when(restTemplate.exchange(ArgumentMatchers.eq(introspectPath), ArgumentMatchers.eq(HttpMethod.POST),
				ArgumentMatchers.any(), ArgumentMatchers.<Class<RefpIntrospect>>any()))
						.thenReturn(new ResponseEntity<RefpIntrospect>(myRefpIntro, HttpStatus.NOT_FOUND));

		mockMvc.perform(get("/rbac").headers(headers)).andExpect(status().isUnauthorized());

	}

	@Test
	void shouldRetrieveRbacNotAuthWhenAllowedIsFalse() throws Exception {

		RefpIntrospect myRefpIntro = buildGoodRefpFromIntrospect();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("fake-token");

		when(restTemplate.exchange(ArgumentMatchers.eq(introspectPath), ArgumentMatchers.eq(HttpMethod.POST),
				ArgumentMatchers.any(), ArgumentMatchers.<Class<RefpIntrospect>>any()))
						.thenReturn(new ResponseEntity<RefpIntrospect>(myRefpIntro, HttpStatus.NOT_FOUND));

		EnteCreditoreEntity EcNotAllowed = buildEnteCreditoreEntity();
		EcNotAllowed.setAllowed(false);
		when(enteCreditoreService.getByRefP(any(String.class))).thenReturn(EcNotAllowed);

		mockMvc.perform(get("/rbac").headers(headers)).andExpect(status().isUnauthorized());

	}

	private EnteCreditoreEntity buildEnteCreditoreEntity() throws ParseException {
		EnteCreditoreDto myEcDto = buildEnteCreditoreOK();
		EnteCreditoreEntity myEcEntity = modelMapper.map(myEcDto, EnteCreditoreEntity.class);
		myEcEntity.setAllowed(true);
		return myEcEntity;
	}

	private IbanEntity buildIbanEntity() throws ParseException {
	    	IbanDto myIbanDto = buildIbanOK();
	    	IbanEntity myIbanEntity = modelMapper.map(myIbanDto, IbanEntity.class);

		return myIbanEntity;
	}
	
	private EnteCreditoreDto buildEnteCreditoreOK() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		EnteCreditoreDto ecDto = new EnteCreditoreDto();
		ecDto.setId(0L);
		ecDto.setDenominazioneEnte("2 Circolo Didattico ''San Giovanni Bosco''");
		ecDto.setCodAmm("istsc_baee15800a");
		ecDto.setCodiceFiscale("80012320729");
		ecDto.setDataAdesione(format.parse("2015-12-14"));
		ecDto.setCodiceGs1Gln("x");
		ecDto.setCognomeRp("De Santis");
		ecDto.setNomeRp("Paolo");
		ecDto.setCodiceFiscaleRp("DNSPAA68L01A252M");
		ecDto.setMailRp("paolo.desanti@istruzione.it");
		ecDto.setTelefonoRp("0658492389");
		ecDto.setCellulareRp("3403608985");
		ecDto.setTipoIntermediazione("INTERMEDIATA");
		ecDto.setDenominazioneIntermediarioPartner("MIUR");
		ecDto.setCognomeRt("De Santis");
		ecDto.setNomeRt("Paolo");
		ecDto.setCodiceFiscaleRt("DNSPAA68L01A252P");
		ecDto.setMailRt("paolo.desanti@istruzione.it");
		ecDto.setTelefonoRt("0658492389");
		ecDto.setCellulareRt("3403608985");
		ecDto.setStatoConnessione(400);
		ecDto.setModello("MODELLO 3");
		ecDto.setDataCollaudo(format.parse("2016-12-01"));
		ecDto.setDataPreEsercizio(format.parse("2016-12-01"));
		ecDto.setDataEsercizio(format.parse("2016-12-01"));
		ecDto.setAuxDigit(0);
		ecDto.setCodiceSegregazione("x");
		ecDto.setApplicationCode("01");
		ecDto.setCodiceInterbancario("AAABB");
		ecDto.setIdStazione("80185250588_01");
		ecDto.setStatoAssociazione("Attiva");
		ecDto.setDataStatoAssociazione(format.parse("2018-07-19"));

		return ecDto;

	}

	private IbanDto buildIbanOK() throws ParseException {
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

	private PaDto buildPaOK() throws ParseException {
		PaDto paDto = new PaDto();

		paDto.setId(0L);
		paDto.setCodAmm("p_mt");
		paDto.setDesAmm("Provincia di Matera");
		paDto.setTipologiaIstat("Province e loro Consorzi e Associazioni");
		paDto.setCodiceFiscale("80000970774");
		paDto.setIndirizzo("Via Ridola, 60");
		paDto.setComune("Matera");
		paDto.setCap("75100");
		paDto.setProvincia("MT");
		paDto.setEmailCertificata("provincia.matera@cert.ruparbasilicata.it");
		paDto.setSitoIstituzionale("www.provincia.matera.it");

		return paDto;

	}

	private PrivacyEntity buildPrivacyOK() throws ParseException {
		PrivacyEntity privacy = new PrivacyEntity();

		privacy.setCodiceFiscaleRefP("MFKDFKD");
		privacy.setId(1l);
		privacy.setDataAccettazione(LocalDateTime.now(ZoneId.of("Europe/Paris")));

		return privacy;

	}

	private RefpIntrospect buildGoodRefpFromIntrospect() {
		RefpIntrospect myRefpIntro = new RefpIntrospect();
		myRefpIntro.setActive(true);
		myRefpIntro.setLevel("L2");
		myRefpIntro.setUser(new RefpSpid());
		myRefpIntro.getUser().setEmail("pppp@ppp.op");
		myRefpIntro.getUser().setName("ssssss");
		myRefpIntro.getUser().setFamily_name("ddddd");
		myRefpIntro.getUser().setFiscal_number("AAAGGG89U76G666T");
		myRefpIntro.getUser().setMobile_phone("666666666");
		return myRefpIntro;
	}

}
