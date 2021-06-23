package it.gov.pagopa.hubpa.payments.generate.paymentnotice.business;

import java.io.IOException;
import java.util.HashMap;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.property.AreaBreakType;

import it.gov.pagopa.hubpa.payments.generate.debtposition.bean.DebtPosition;
import it.gov.pagopa.hubpa.payments.generate.paymentnotice.bean.PaymentNotice;
import it.gov.pagopa.hubpa.payments.generate.paymentnotice.business.printer.PdfPaymentNoticeManagement;
import it.gov.pagopa.hubpa.payments.generate.paymentnotice.business.printer.sections.BankingSection;
import it.gov.pagopa.hubpa.payments.generate.paymentnotice.business.printer.sections.BankingSectionThreeInstallment;
import it.gov.pagopa.hubpa.payments.generate.paymentnotice.business.printer.sections.BankingSectionTwoInstallment;
import it.gov.pagopa.hubpa.payments.generate.paymentnotice.business.printer.sections.HeaderSection;
import it.gov.pagopa.hubpa.payments.generate.paymentnotice.business.printer.sections.PaymentDetailSection;
import it.gov.pagopa.hubpa.payments.generate.paymentnotice.business.printer.sections.PaymentInfoSection;
import it.gov.pagopa.hubpa.payments.generate.paymentnotice.business.printer.sections.PostalSection;
import it.gov.pagopa.hubpa.payments.generate.paymentnotice.constants.PaymentNoticeConstants;
import it.gov.pagopa.hubpa.payments.iuvgenerator.common.ErrorMessages;

/**
 * Generates the pdf of the payment notice based on <code>debtPosition</code>
 * list
 */
public class PdfPaymentNoticeCreator {

    private PdfWriter pdfWriter = null;
    private PdfDocument pdfDocument = null;
    private Document document = null;
    private PageSize pageSize = null;
    private ByteArrayOutputStream stream = null;
    private PaymentNotice paymentNotice = null;
    private HashMap<Integer, DebtPosition> sortedDebtPositionHashMap = null;

    /**
     * Public constructor
     * 
     * @param paymentNotice
     * @throws Exception
     * @see PaymentNotice
     */
    public PdfPaymentNoticeCreator(PaymentNotice paymentNotice) {
	super();
	this.paymentNotice = paymentNotice;
	initialize();
    }

    /**
     * Initialization of pdf objects
     */
    private void initialize() {
	stream = new ByteArrayOutputStream();
	pdfWriter = new PdfWriter(stream);
	pdfDocument = new PdfDocument(pdfWriter);
	pageSize = new PageSize(PageSize.A4);
	document = new Document(pdfDocument, pageSize);
	document.setMargins(20, 15, 15, 10);
    }

    /**
     * Generates all the components of the pdf of payment notice:
     * <ul>
     * <li>createTemplate - selects the appropriate pdf template
     * <li>createHeadingSection - generates header section of the pdf containing the
     * subject of the payment and the logo of the Creditor Institution
     * <li>createPaymentInfoSection - generates the information section of the pdf
     * containing the details of the Creditor Institution and the Payer
     * <li>createAmountAndExpirySection - generates the information section of the
     * pdf containing the payment amount, the payment expiration date and the data
     * on where to pay
     * <li>createBankingSection - generates the banking section of the pdf
     * containing all the information to make a bank payment
     * <li>createPostalSection - generates the postal section of the pdf containing
     * all the information to make a postal payment
     * <li>creaInstallmentSections - generates the installments section of the pdf
     * containing all the information to make a payment by installments
     * </ul>
     * 
     * @throws Exception
     */
    public void createDocument() throws Exception {
	createTemplate();

	createHeadingSection();

	createPaymentInfoSection();

	createAmountAndExpirySection();

	if (paymentNotice.getDebtPositionList().size() == 1
		|| PaymentNoticeBusiness.hasSingleInstallment(paymentNotice)) {
	    createBankingSection();

	    if (paymentNotice.getCreditorInstitution().getPostalAuthorizationCode() != null) {
		createPostalSection();
	    }
	}

	if (paymentNotice.getDebtPositionList().size() > 1) {
	    creaInstallmentSections();
	}
    }

    /**
     * Selects the appropriate pdf template
     * 
     * @throws Exception
     */
    private void createTemplate() throws Exception {
	PdfCanvas canvas = new PdfCanvas(pdfDocument.addNewPage());
	String template = "";
	if (paymentNotice.getCreditorInstitution().getPostalAuthorizationCode() != null) {
	    if (paymentNotice.getDebtPositionList().size() == 1) {
		template = PaymentNoticeConstants.BASE_TEMPLATE;
	    } else {
		if (PaymentNoticeBusiness.hasSingleInstallment(paymentNotice)) {
		    template = PaymentNoticeConstants.BASE_TEMPLATE;
		} else {
		    template = PaymentNoticeConstants.BASE_TEMPLATE_NO_POSTAL_SECTION_NO_SINGLE_INSTALLMENT;
		}
	    }
	} else {
	    if (paymentNotice.getDebtPositionList().size() == 1) {
		template = PaymentNoticeConstants.BASE_TEMPLATE_NO_POSTAL_SECTION;
	    } else {
		if (PaymentNoticeBusiness.hasSingleInstallment(paymentNotice)) {
		    template = PaymentNoticeConstants.BASE_TEMPLATE_NO_POSTAL_SECTION;
		} else {
		    template = PaymentNoticeConstants.BASE_TEMPLATE_NO_POSTAL_SECTION_NO_SINGLE_INSTALLMENT;
		}
	    }
	}
	canvas.addImage(PdfPaymentNoticeManagement.creaImgData(template), pageSize, false);
    }

    /**
     * Generates header section of the pdf containing the subject of the payment and
     * the logo of the Creditor Institution
     * 
     * @throws Exception
     * @see HeaderSection
     */
    private void createHeadingSection() throws Exception {
	HeaderSection headerSection = new HeaderSection(paymentNotice);
	document.add(headerSection.createHeaderSection());
    }

    /**
     * Generates the information section of the pdf containing the details of the
     * Creditor Institution and the Payer
     * 
     * @throws Exception
     * @see PaymentInfoSection
     */
    private void createPaymentInfoSection() throws Exception {
	PaymentInfoSection paymentInfoSection = new PaymentInfoSection(paymentNotice);
	document.add(paymentInfoSection.createFirstRow());
	document.add(paymentInfoSection.createSecondRow());
	document.add(paymentInfoSection.createThirdRow());
	document.add(paymentInfoSection.createFourthRow());
    }

    /**
     * Generates the information section of the pdf containing the payment amount,
     * the payment expiration date and the data on where to pay
     * 
     * @throws Exception
     * @see PaymentDetailSection
     */
    private void createAmountAndExpirySection() throws Exception {
	PaymentDetailSection paymentDetailSection = new PaymentDetailSection(paymentNotice);
	document.add(paymentDetailSection.createFirstRow());
	document.add(paymentDetailSection.createSecondRow());
	document.add(paymentDetailSection.createThirdRow());
    }

    /**
     * Generates the banking section of the pdf containing all the information to
     * make a bank payment
     * 
     * @throws Exception
     * @see BankingSection
     */
    private void createBankingSection() throws Exception {
	BankingSection bankingSection = new BankingSection(paymentNotice, pdfDocument);
	document.add(bankingSection.createFirstRow());
	document.add(bankingSection.createSecondRow());
    }

    /**
     * Generates the postal section of the pdf containing all the information to
     * make a postal payment
     * 
     * @throws Exception
     * @see PostalSection
     */
    private void createPostalSection() throws Exception {
	PostalSection postalSection = new PostalSection(paymentNotice, pdfDocument);
	document.add(postalSection.createFirstRow());
	document.add(postalSection.createSecondRow());
    }

    /**
     * Generates the installments section of the pdf containing all the information
     * to make a payment by installments
     * 
     * @throws Exception
     */
    private void creaInstallmentSections() throws Exception {
	sortedDebtPositionHashMap = PaymentNoticeBusiness
		.sortDebtPositionListByInstallmentNumberExcludingSingleInstallment(paymentNotice.getDebtPositionList());
	int installmentsNumber = sortedDebtPositionHashMap.size();
	int modulo3 = installmentsNumber % 3;
	int divisionBy2 = installmentsNumber / 2;
	int divisionBy3 = installmentsNumber / 3;

	if (modulo3 == 0) {
	    // CASI 3-6-9... RATE
	    // 3 rate a pagina
	    for (int i = 0; i < divisionBy3; i++) {
		createThreeInstallmentsPage(i * 3);
	    }
	} else {
	    if (modulo3 == 2) {
		// e' presente almeno 1 pagina da 2 rate
		if (divisionBy3 > 0) {
		    // CASI 5-8-11... RATE
		    // x pagine da 3 rate e l'ultima pagina da 2 rate
		    int i = 0;
		    for (i = 0; i < divisionBy3; i++) {
			createThreeInstallmentsPage(i * 3);
		    }
		    createTwoInstallmentsPage(i * 3);
		} else {
		    // CASO 2 RATE
		    for (int i = 0; i < divisionBy2; i++) {
			createTwoInstallmentsPage(i * 2);
		    }
		}
	    } else {
		// sono presenti almeno 2 pagine da 2 rate
		if (divisionBy3 > 0) {
		    // CASI 4-7-10... RATE
		    // x pagine da 3 rate e le ultime 2 pagine da 2 rate
		    int i = 0;
		    for (i = 0; i < divisionBy3 - 1; i++) {
			createThreeInstallmentsPage(i * 3);
		    }
		    createTwoInstallmentsPage(i * 3);
		    createTwoInstallmentsPage(i * 3 + 2);
		} else {
		    // CASO 1 RATA
		    throw new Exception(ErrorMessages.VALIDATION_ONLY_1_INSTALLMENT);
		}
	    }
	}
    }

    /**
     * Generates the installments section of the pdf if it has 3 installments
     * 
     * @param startingInstallmentNumber installmentNumber from which the subsequent
     *                                  number of installments are calculated
     * @throws Exception
     */
    private void createThreeInstallmentsPage(int startingInstallmentNumber) throws Exception {
	PdfCanvas canvas = new PdfCanvas(pdfDocument.addNewPage());
	canvas.addImage(
		PdfPaymentNoticeManagement
			.creaImgData(paymentNotice.getCreditorInstitution().getPostalAuthorizationCode() != null
				? PaymentNoticeConstants.THREE_INSTALLMENTS_TEMPLATE
				: PaymentNoticeConstants.THREE_INSTALLMENTS_TEMPLATE_NO_POSTAL_SECTION),
		pageSize, false);
	document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
	createInstallmentHeaderSection(PaymentNoticeConstants.PAGE_TYPE_THREE_INSTALLMENTS, startingInstallmentNumber);
	createThreeInstallmentsBankingSection(startingInstallmentNumber);
	if (paymentNotice.getCreditorInstitution().getPostalAuthorizationCode() != null)
	    createThreeInstallmentsPostalSection(startingInstallmentNumber);

    }

    /**
     * Generates the banking section of the pdf if it has 3 installments containing
     * all the information to make a bank payment by installments
     * 
     * @param startingInstallmentNumber installmentNumber from which the subsequent
     *                                  number of installments are calculated
     * @throws Exception
     * @see BankingSectionThreeInstallment
     */
    private void createThreeInstallmentsBankingSection(int startingInstallmentNumber) throws Exception {
	BankingSectionThreeInstallment bankingSectionThreeInstallment = new BankingSectionThreeInstallment(
		paymentNotice, pdfDocument, startingInstallmentNumber, sortedDebtPositionHashMap);
	document.add(bankingSectionThreeInstallment.createFirstRow());
	document.add(bankingSectionThreeInstallment.createSecondRow());
    }

    /**
     * Generates the postal section of the pdf if it has 3 installments containing
     * all the information to make a postal payment by installments
     * 
     * @param startingInstallmentNumber installmentNumber from which the subsequent
     *                                  number of installments are calculated
     * @throws Exception
     * @see PostalSection
     */
    private void createThreeInstallmentsPostalSection(int startingInstallmentNumber) throws Exception {
	for (int i = 0; i < 3; i++) {
	    int installmentNumber = startingInstallmentNumber + i + 1;
	    DebtPosition debtPosition = sortedDebtPositionHashMap.get(installmentNumber);
	    PostalSection postalSection = new PostalSection(paymentNotice, pdfDocument, debtPosition, installmentNumber,
		    true);
	    document.add(postalSection.createFirstRow());
	    document.add(postalSection.createSecondRow());
	}
    }

    /**
     * Generates the installments section of the pdf if it has 2 installments
     * 
     * @param startingInstallmentNumber installmentNumber from which the subsequent
     *                                  number of installments are calculated
     * @throws Exception
     */
    private void createTwoInstallmentsPage(int startingInstallmentNumber) throws Exception {
	PdfCanvas canvas = new PdfCanvas(pdfDocument.addNewPage());
	canvas.addImage(PdfPaymentNoticeManagement
		.creaImgData(paymentNotice.getCreditorInstitution().getPostalAuthorizationCode() != null
			? PaymentNoticeConstants.TWO_INSTALLMENTS_TEMPLATE
			: PaymentNoticeConstants.TWO_INSTALLMENTS_TEMPLATE_NO_POSTAL_SECTION),
		pageSize, false);
	document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
	createInstallmentHeaderSection(PaymentNoticeConstants.PAGE_TYPE_TWO_INSTALLMENTS, startingInstallmentNumber);
	createTwoInstallmentsBankingSection(startingInstallmentNumber);
	if (paymentNotice.getCreditorInstitution().getPostalAuthorizationCode() != null)
	    createTwoInstallmentsPostalSection(startingInstallmentNumber);
    }

    /**
     * Generates the banking section of the pdf if it has 2 installments containing
     * all the information to make a bank payment by installments
     * 
     * @param startingInstallmentNumber installmentNumber from which the subsequent
     *                                  number of installments are calculated
     * @throws Exception
     * @see BankingSectionTwoInstallment
     */
    private void createTwoInstallmentsBankingSection(int startingInstallmentNumber) throws Exception {
	BankingSectionTwoInstallment bankingSectionTwoInstallment = new BankingSectionTwoInstallment(paymentNotice,
		pdfDocument, startingInstallmentNumber, sortedDebtPositionHashMap);
	document.add(bankingSectionTwoInstallment.createFirstRow());
	document.add(bankingSectionTwoInstallment.createSecondRow());
    }

    /**
     * Generates the postal section of the pdf if it has 2 installments containing
     * all the information to make a postal payment by installments
     * 
     * @param startingInstallmentNumber installmentNumber from which the subsequent
     *                                  number of installments are calculated
     * @throws Exception
     * @see PostalSection
     */
    private void createTwoInstallmentsPostalSection(int startingInstallmentNumber) throws Exception {
	for (int i = 0; i < 2; i++) {
	    int installmentNumber = startingInstallmentNumber + i + 1;
	    DebtPosition debtPosition = sortedDebtPositionHashMap.get(installmentNumber);
	    PostalSection postalSection = new PostalSection(paymentNotice, pdfDocument, debtPosition, installmentNumber,
		    false);
	    document.add(postalSection.createFirstRow());
	    document.add(postalSection.createSecondRow());
	}
    }

    /**
     * Generates header section of the pdf if it has installments containing the
     * subject of the payment and the logo of the Creditor Institution
     * 
     * @param installmentPageType       whether the page has 2 or 3 installments
     * @param startingInstallmentNumber installmentNumber from which the subsequent
     *                                  number of installments are calculated
     * @throws Exception
     * @see HeaderSection
     */
    private void createInstallmentHeaderSection(String installmentPageType, int startingInstallmentNumber)
	    throws Exception {
	HeaderSection headerSection = new HeaderSection(paymentNotice, true, installmentPageType,
		startingInstallmentNumber);
	document.add(headerSection.createHeaderSection());
    }

    /**
     * @throws IOException 
     * @throws Exception
     */
    public void closeStreams() throws IOException {

	if (pdfWriter != null && !pdfWriter.isCloseStream())
	    pdfWriter.close();

	if (pdfDocument != null && !pdfDocument.isClosed())
	    pdfDocument.close();

	if (document != null)
	    document.close();

	if (stream != null)
	    stream.close();

    }

    /**
     * @return byteArray of the pdf of notice payment
     */
    public byte[] getDocumentInBytes() {
	return stream.toByteArray();
    }
}
