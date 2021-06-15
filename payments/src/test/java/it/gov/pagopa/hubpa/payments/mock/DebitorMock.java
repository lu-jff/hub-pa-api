package it.gov.pagopa.hubpa.payments.mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import it.gov.pagopa.hubpa.payments.entity.Debitor;
import it.gov.pagopa.hubpa.payments.entity.PaymentOptions;
import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;
import it.gov.pagopa.hubpa.payments.entity.Transfers;

public class DebitorMock {
    public final static Debitor getMock() {
	Debitor mock = new Debitor();
	mock.setFiscalCode("MRDPLL54H17D542L");
	mock.setType(1);
	mock.setName("Mario");
	mock.setSurname("Rossi");
	mock.setPhone("3330987654");
	mock.setAddress("Via di novoli");
	mock.setNumber("50/2");
	mock.setArea("Firenze");
	mock.setProvince("FI");
	mock.setCountry("IT");
	mock.setEmail("mario@firenze.it");
	mock.setIdTenant("abcd");

	mock.addPaymentPosition(createPaymentPositionMock());

	return mock;
    }

    public static PaymentPosition createPaymentPositionMock() {

	PaymentPosition mock = new PaymentPosition();
	mock.setAmount(new BigDecimal(200));
	mock.setCompanyName(null);
	mock.setDescription(null);
	mock.setInformation("Warning");
	mock.setInsertDate(LocalDateTime.now(ZoneId.of("Europe/Paris")));
	mock.setJobId(1l);
	mock.setOfficeName(null);
	mock.setOrganizationFiscalCode("12345678901");
	mock.setStatus(1);
	mock.setPublishDate(LocalDate.now());

	mock.addPaymentOptions(createPaymentOptionsMock1());
	mock.addPaymentOptions(createPaymentOptionsMock2());
	mock.addPaymentOptions(createPaymentOptionsMock3());

	mock.setTotalOptions(mock.getPaymentOptions().size());
	mock.setPaidOptions(0);
	mock.setReportedOptions(0);

	return mock;
    }

    private static PaymentOptions createPaymentOptionsMock1() {

	PaymentOptions mock = new PaymentOptions();
	mock.setAmount(new BigDecimal(200));
	mock.setDuoDate(LocalDate.now(ZoneId.of("Europe/Paris")));
	mock.setFiscalCode("12345678901");
	mock.setIsConclusive(Boolean.TRUE);
	mock.setMetadata(null);
	mock.setNotificationCode("02252556125612");
	mock.setPaymentDate(null);
	mock.setRetentionDate(null);
	mock.setStatus(1);

	mock.addTransfers(createTransfersMock1a());
	mock.addTransfers(createTransfersMock1b());

	return mock;
    }

    private static PaymentOptions createPaymentOptionsMock2() {

	PaymentOptions mock = new PaymentOptions();
	mock.setAmount(new BigDecimal(120));
	mock.setDuoDate(LocalDate.now(ZoneId.of("Europe/Paris")));
	mock.setFiscalCode("12345678901");
	mock.setIsConclusive(Boolean.FALSE);
	mock.setMetadata(null);
	mock.setNotificationCode("02252556125612");
	mock.setPaymentDate(null);
	mock.setRetentionDate(null);
	mock.setStatus(1);

	mock.addTransfers(createTransfersMock2a());

	return mock;
    }

    private static PaymentOptions createPaymentOptionsMock3() {

	PaymentOptions mock = new PaymentOptions();
	mock.setAmount(new BigDecimal(80));
	mock.setDuoDate(LocalDate.now(ZoneId.of("Europe/Paris")));
	mock.setFiscalCode("12345678901");
	mock.setIsConclusive(Boolean.FALSE);
	mock.setMetadata(null);
	mock.setNotificationCode("02252556125612");
	mock.setPaymentDate(null);
	mock.setRetentionDate(null);
	mock.setStatus(1);

	mock.addTransfers(createTransfersMock3a());
	mock.addTransfers(createTransfersMock3b());

	return mock;
    }

    public static Transfers createTransfersMock1a() {
	Transfers mock = new Transfers();
	mock.setIban("IT12345677889");
	mock.setOrganizationFiscalCode("12345678901");
	mock.setPartialAmount(new BigDecimal(150));
	mock.setReason("causale tari tefa");
	mock.setTaxonomy("10/22252/20");
	mock.setCcPostal("000050570131");

	return mock;
    }

    private static Transfers createTransfersMock2a() {
	Transfers mock = new Transfers();
	mock.setIban("IT12345677889");
	mock.setOrganizationFiscalCode("12345678901");
	mock.setPartialAmount(new BigDecimal(120));
	mock.setReason("causale tari tefa");
	mock.setTaxonomy("10/22252/20");

	return mock;
    }

    private static Transfers createTransfersMock3a() {
	Transfers mock = new Transfers();
	mock.setIban("IT12345677889");
	mock.setOrganizationFiscalCode("12345678901");
	mock.setPartialAmount(new BigDecimal(75));
	mock.setReason("causale tari tefa");
	mock.setTaxonomy("10/22252/20");

	return mock;
    }

    private static Transfers createTransfersMock1b() {
	Transfers mock = new Transfers();
	mock.setIban("IT989999999");
	mock.setOrganizationFiscalCode("99999999999");
	mock.setPartialAmount(new BigDecimal(50));
	mock.setReason("causale tari tefa");
	mock.setTaxonomy("20/22252/20");

	return mock;
    }

    private static Transfers createTransfersMock3b() {
	Transfers mock = new Transfers();
	mock.setIban("IT989999999");
	mock.setOrganizationFiscalCode("99999999999");
	mock.setPartialAmount(new BigDecimal(50));
	mock.setReason("causale tari tefa");
	mock.setTaxonomy("20/22252/20");

	return mock;
    }

}
