package it.gov.pagopa.hubpa.uploadpayments.mock;

import java.time.LocalDateTime;

import it.gov.pagopa.hubpa.uploadpayments.entity.PaymentJob;


public class PaymentJobMock {
    public final static PaymentJob getMock() {
	PaymentJob mock=new PaymentJob();
	mock.setJobId(1l);
	mock.setFiscalCode("12345678901");
	mock.setFileName("testFileCsv20210409.csv");
	mock.setInsertDate(LocalDateTime.now());
	mock.setElaborationDate(LocalDateTime.now());
	mock.setNRecordAdded(10);
	mock.setNRecordFound(100);
	mock.setStatus(3);
	
	return mock;
    }
   
}
