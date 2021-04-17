package it.gov.pagopa.hubpa.uploadpayments.mock;

import java.time.LocalDateTime;

import it.gov.pagopa.hubpa.uploadpayments.model.PaymentJobModel;


public class PaymentJobModelMock {
    public final static PaymentJobModel getMock() {
	PaymentJobModel mock=new PaymentJobModel();
	
	mock.setCreditorId(1l);
	mock.setFileName("testFileCsv20210409.csv");
	mock.setInsertDate(LocalDateTime.now());
	mock.setElaborationDate(LocalDateTime.now());
	mock.setNRecordAdded(10);
	mock.setNRecordFound(100);
	mock.setStatus(3);
	
	return mock;
    }
   
}
