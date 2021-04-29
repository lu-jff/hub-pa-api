package it.gov.pagopa.hubpa.payments.mock;

import java.time.LocalDate;

import it.gov.pagopa.hubpa.payments.model.FilterModel;

public class FilterModelMock {
    public final static FilterModel getMock() {
	FilterModel mock = new FilterModel();

	mock.setTextSearch("MRC");
	mock.setStatus(1);
	mock.setDateTo(LocalDate.now());
	mock.setDateFrom(LocalDate.now());

	return mock;
    }
}
