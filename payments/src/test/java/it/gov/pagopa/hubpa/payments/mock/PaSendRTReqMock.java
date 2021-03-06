package it.gov.pagopa.hubpa.payments.mock;

import it.gov.pagopa.hubpa.payments.model.partner.CtReceipt;
import it.gov.pagopa.hubpa.payments.model.partner.PaSendRTReq;
import it.gov.pagopa.hubpa.payments.model.partner.StOutcome;

public class PaSendRTReqMock {

    public final static PaSendRTReq getMock() {

        CtReceipt receipt = new CtReceipt();
        receipt.setReceiptId("c110729d258c4ab1b765fe902aae41d6");
        receipt.setNoticeNumber("311111111112222222");
        receipt.setFiscalCode("77777777777");
        receipt.setOutcome(StOutcome.OK);
        receipt.setCreditorReferenceId("11111111112222222");

        PaSendRTReq mock = new PaSendRTReq();
        mock.setIdBrokerPA("77777777777");
        mock.setIdPA("77777777777");
        mock.setIdStation("77777777777_01");
        mock.setReceipt(receipt);

        return mock;
    }
}
