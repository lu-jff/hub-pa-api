package it.gov.pagopa.hubpa.uploadpayments.mock;

import it.gov.pagopa.hubpa.commons.model.PaymentJobMinimalModel;

public class PaymentJobMinimalModelMock {
	public final static PaymentJobMinimalModel getMock() {
		PaymentJobMinimalModel mock = new PaymentJobMinimalModel();

		mock.setNRecordWarning(5);
		mock.setNRecordAdded(10);
		mock.setNRecordFound(100);
		mock.setStatus(3);

		return mock;
	}

}
