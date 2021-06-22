package it.gov.pagopa.hubpa.payments.mock;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import it.gov.pagopa.hubpa.payments.model.ente.EnteCreditoreMinimalDto;

public class EnteCreditoreMinimalDtoMock {
    public final static EnteCreditoreMinimalDto getMock() throws ParseException{
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	EnteCreditoreMinimalDto ecDto = new EnteCreditoreMinimalDto();
	ecDto.setDenominazioneEnte("2 Circolo Didattico ''San Giovanni Bosco''");
	ecDto.setCodiceFiscale("80012320729");
	ecDto.setDataAdesione(format.parse("2015-12-14"));
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
}
