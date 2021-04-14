package it.gov.pagopa.hubpa.servicemanagement.mock;

import java.math.BigDecimal;
import java.time.LocalDate;

import it.gov.pagopa.hubpa.servicemanagement.entity.PaymentOptionTemplate;
import it.gov.pagopa.hubpa.servicemanagement.entity.Service;
import it.gov.pagopa.hubpa.servicemanagement.entity.TransferTemplate;

public class ServiceMock {
    public final static Service getMock() {
	Service ss = new Service();
	ss.setCreditorId(1L);
	ss.setDenomination("TariTefa2021");
	ss.setPercentage(BigDecimal.valueOf(5));
	ss.setSecondaryCreditorId(2L);
	ss.setTotalInstallments(3);
	ss.setFiscalCodePrimaryCreditor("VBMPGR31H03F993U");
	ss.setFiscalCodeSecondaryCreditor("FMMFLN90H13E027F");

	// installment unique
	PaymentOptionTemplate pp = new PaymentOptionTemplate();
	pp.setDueDate(LocalDate.now().plusMonths(4));
	pp.setInstallmentNumber(0);
	pp.setIsFinal(true);

	TransferTemplate tt = new TransferTemplate();
	tt.setIban("IT67P0300203280575369338247");
	tt.setIsSecondaryCreditor(false);
	tt.setPercentage(BigDecimal.valueOf(100));
	pp.addTransferTemplate(tt);
	tt = new TransferTemplate();
	tt.setIban("IT76N0300203280879483594963");
	tt.setIsSecondaryCreditor(true);
	tt.setPercentage(BigDecimal.valueOf(100));
	pp.addTransferTemplate(tt);
	ss.addPaymentOptionTemplate(pp);

	// installment 1
	pp = new PaymentOptionTemplate();
	pp.setDueDate(LocalDate.now().plusMonths(2));
	pp.setInstallmentNumber(1);
	pp.setIsFinal(false);

	tt = new TransferTemplate();
	tt.setIban("IT67P0300203280575369338247");
	tt.setIsSecondaryCreditor(false);
	tt.setPercentage(BigDecimal.valueOf(20));
	pp.addTransferTemplate(tt);
	ss.addPaymentOptionTemplate(pp);

	// installment 2
	pp = new PaymentOptionTemplate();
	pp.setDueDate(LocalDate.now().plusMonths(3));
	pp.setInstallmentNumber(2);
	pp.setIsFinal(false);

	tt = new TransferTemplate();
	tt.setIban("IT67P0300203280575369338247");
	tt.setIsSecondaryCreditor(false);
	tt.setPercentage(BigDecimal.valueOf(50));
	pp.addTransferTemplate(tt);
	tt = new TransferTemplate();
	tt.setIban("IT76N0300203280879483594963");
	tt.setIsSecondaryCreditor(true);
	tt.setPercentage(BigDecimal.valueOf(20));
	pp.addTransferTemplate(tt);
	ss.removePaymentOptionTemplate(pp);
	ss.addPaymentOptionTemplate(pp);

	// installment 3
	pp = new PaymentOptionTemplate();
	pp.setDueDate(LocalDate.now().plusMonths(4));
	pp.setInstallmentNumber(3);
	pp.setIsFinal(false);

	tt = new TransferTemplate();
	tt.setIban("IT67P0300203280575369338247");
	tt.setIsSecondaryCreditor(false);
	tt.setPercentage(BigDecimal.valueOf(30));
	pp.addTransferTemplate(tt);
	tt = new TransferTemplate();
	tt.setIban("IT76N0300203280879483594963");
	tt.setIsSecondaryCreditor(true);
	tt.setPercentage(BigDecimal.valueOf(80));
	pp.removeTransferTemplate(tt);
	pp.addTransferTemplate(tt);
	ss.addPaymentOptionTemplate(pp);

	return ss;
    }

}
