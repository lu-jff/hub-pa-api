package it.gov.pagopa.hubpa.payments.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import it.gov.pagopa.hubpa.payments.mock.PaGetPaymentReqMock;
import it.gov.pagopa.hubpa.payments.mock.PaSendRTReqMock;
import it.gov.pagopa.hubpa.payments.mock.PaVerifyPaymentNoticeReqMock;

import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeRes;

@ExtendWith(MockitoExtension.class)
class PartnerServiceTest {

	@InjectMocks
	private PartnerService partnerService;

	@Test
	void paVerifyPaymentNoticeTest() {

		// Test preconditions
		PaVerifyPaymentNoticeReq requestBody = PaVerifyPaymentNoticeReqMock.getMock();

		// Test execution
		PaVerifyPaymentNoticeRes responseBody = partnerService.paVerifyPaymentNotice(requestBody);

		// Test postcondiction
		assertThat(responseBody.getFiscalCodePA()).isEqualTo("77777777777");
		assertThat(responseBody.getOfficeName()).isEqualTo("officeName");
		assertThat(responseBody.getPaymentDescription()).isEqualTo("payment");
	}

	@Test
	void paGetPaymentTest() {

		// Test preconditions
		PaGetPaymentReq requestBody = PaGetPaymentReqMock.getMock();

		// Test execution
		PaGetPaymentRes responseBody = partnerService.paGetPayment(requestBody);

		// Test postcondiction
		assertThat(responseBody.getData().getCompanyName()).isEqualTo("company name");
		assertThat(responseBody.getData().getCreditorReferenceId()).isEqualTo("id");
	}

	@Test
	void paSendRTTest() {

		// Test preconditions
		PaSendRTReq requestBody = PaSendRTReqMock.getMock();

		// Test execution
		PaSendRTRes responseBody = partnerService.paSendRT(requestBody);

		// Test postcondiction
		assertThat(responseBody.getOutcome()).isEqualTo("OK");
	}

}
