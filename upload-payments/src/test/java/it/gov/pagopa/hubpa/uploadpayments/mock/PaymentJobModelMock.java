package it.gov.pagopa.hubpa.uploadpayments.mock;

import java.time.LocalDateTime;
import java.time.ZoneId;

import it.gov.pagopa.hubpa.uploadpayments.model.PaymentJobModel;


public class PaymentJobModelMock {
    public final static PaymentJobModel getMock() {
	PaymentJobModel mock=new PaymentJobModel();
	
	mock.setFiscalCode("12345678901");
	mock.setFileName("testFileCsv20210409.csv");
	mock.setInsertDate(LocalDateTime.now(ZoneId.of("Europe/Paris")));
	mock.setElaborationDate(LocalDateTime.now(ZoneId.of("Europe/Paris")));
	mock.setNRecordAdded(10);
	mock.setNRecordFound(100);
	mock.setStatus(3);
	
	return mock;
    }
   
}
