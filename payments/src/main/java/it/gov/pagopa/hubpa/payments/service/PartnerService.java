
package it.gov.pagopa.hubpa.payments.service;

import org.springframework.stereotype.Service;

import it.gov.pagopa.hubpa.payments.model.partner.CtPaymentPA;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaGetPaymentRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTRes;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeReq;
import it.gov.pagopa.hubpa.payments.model.partner.PaVerifyPaymentNoticeRes;
import it.gov.pagopa.hubpa.payments.model.partner.StOutcome;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PartnerService {

	public PaVerifyPaymentNoticeRes paVerifyPaymentNotice(PaVerifyPaymentNoticeReq request) {

		log.debug(String.format("paVerifyPaymentNotice %s", request.getIdPA()));

		// mock response
		PaVerifyPaymentNoticeRes result = new PaVerifyPaymentNoticeRes();
		result.setCompanyName("company name");
		result.setOfficeName("officeName");
		result.setFiscalCodePA("77777777777");
		result.setPaymentDescription("payment");
		result.setOfficeName("officeName");
		return result;
	}

	public PaGetPaymentRes paGetPayment(PaGetPaymentReq request) {

		log.debug(String.format("paGetPayment %s", request.getIdPA()));

		// mock response
		PaGetPaymentRes result = new PaGetPaymentRes();
		CtPaymentPA data = new CtPaymentPA();
		data.setCompanyName("company name");
		data.setCreditorReferenceId("id");
		result.setData(data);
		return result;
	}

	public PaSendRTRes paSendRT(PaSendRTReq request) {

		log.debug(String.format("paSendRT %s", request.getIdPA()));

		// mock response
		PaSendRTRes result = new PaSendRTRes();
		result.setOutcome(StOutcome.OK);
		return result;
	}

}
