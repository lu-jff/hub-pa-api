package it.gov.pagopa.hubpa.uploadpayments.mock;

import java.time.LocalDateTime;
import java.time.ZoneId;

import it.gov.pagopa.hubpa.uploadpayments.entity.PaymentJob;


public class PaymentJobMock {
    public final static PaymentJob getMock() {
	PaymentJob mock=new PaymentJob();
	mock.setJobId(1l);
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
