package it.gov.pagopa.hubpa.payments.mock;

import java.time.LocalDate;

import it.gov.pagopa.hubpa.payments.model.PaymentMinimalModel;

public class PaymentMinimalModelMock {
    public final static PaymentMinimalModel getMock() {
	PaymentMinimalModel mock = new PaymentMinimalModel();

	mock.setId(1l);
	mock.setFiscalCode("MCFKLO45454545");
	mock.setName("MARIO");
	mock.setSurname("ROSSI");
	mock.setDate(LocalDate.now());
	mock.setStatus(2);
	
	return mock;
    }
}
