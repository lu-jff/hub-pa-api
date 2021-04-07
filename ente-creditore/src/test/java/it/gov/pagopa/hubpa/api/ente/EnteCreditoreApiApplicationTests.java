package it.gov.pagopa.hubpa.api.ente;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.modelmapper.ModelMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.extension.ExtendWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.json.JsonMapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class EnteCreditoreApiApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EnteCreditoreService enteCreditoreService;

	private JsonMapper jsonMapper = new JsonMapper();
	private ModelMapper ecMapper = new ModelMapper();

	private EnteCreditoreDto builEnteCreditoreOK() throws ParseException {
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
		ecDto.setCodiceFiscaleRp("DNSPAA68L01A252P");
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

	@Test
	public void shouldGetEnte() throws Exception {
		this.mockMvc.perform(get("/ente/tttt")).andExpect(status().isOk());
	}

	@Test
	public void shouldPostEnte() throws Exception {
		EnteCreditoreDto goodEc = builEnteCreditoreOK();
		// Mock service response
		when(enteCreditoreService.create(any(EnteCreditoreEntity.class)))
				.thenReturn(ecMapper.map(goodEc, EnteCreditoreEntity.class));
		jsonMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		String myJson = jsonMapper.writeValueAsString(goodEc);
		this.mockMvc.perform(post("/ente").content(myJson).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void shouldNotGetEnte() throws Exception {
		this.mockMvc.perform(get("/entxe/tttt")).andExpect(status().is(404));
	}

}
