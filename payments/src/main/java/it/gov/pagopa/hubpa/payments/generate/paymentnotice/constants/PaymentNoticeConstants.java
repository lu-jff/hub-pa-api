package it.gov.pagopa.hubpa.payments.generate.paymentnotice.constants;

/**
 * All the constants related to pdf of the Payment Notice
 */
public class PaymentNoticeConstants {

    public static final String basePath = "resources";
    public static final String baseTemplate = basePath + "/templates/pdfPaymentNotice/baseTemplate.png";
    public static final String baseTemplate_NoPostalSection_NoSingleInstallment = basePath
            + "/templates/pdfPaymentNotice/baseTemplate_noPS_noSI.png";
    public static final String baseTemplate_NoPostalSection = basePath + "/templates/pdfPaymentNotice/baseTemplate_noPS.png";
    public static final String twoInstallmentsTemplate = basePath + "/templates/pdfPaymentNotice/twoInstallmentsTemplate.png";
    public static final String twoInstallmentsTemplate_noPostalSection = basePath
            + "/templates/pdfPaymentNotice/twoInstallmentsTemplate_noPS.png";
    public static final String threeInstallmentsTemplate = basePath + "/templates/pdfPaymentNotice/threeInstallmentsTemplate.png";
    public static final String threeInstallmentsTemplate_noPostalSection = basePath
            + "/templates/pdfPaymentNotice/threeInstallmentsTemplate_noPS.png";

    public static final String robotoFontRegular = basePath + "/fonts/RobotoMono-Regular.ttf";
    public static final String robotoFontBold = basePath + "/fonts/RobotoMono-Bold.ttf";
    public static final String titilliumWebRegular = basePath + "/fonts/TitilliumWeb-Regular.ttf";
    public static final String titilliumWebBold = basePath + "/fonts/TitilliumWeb-Bold.ttf";
    public static final String titilliumWebBlack = basePath + "/fonts/TitilliumWeb-Black.ttf";

    public final static String PAGE_TYPE_TWO_INSTALLMENTS = "2_INSTALLMENTS";
    public final static String PAGE_TYPE_THREE_INSTALLMENTS = "3_INSTALLMENTS";
    public final static String PDF_TEXT_NOTICE_PAYMENT = "AVVISO DI PAGAMENTO";
    public final static String PDF_TEXT_LOGO_EXTENSION = "png";
    public final static String PDF_TEXT_DESCRIPTION_PLACEHOLDER_ONE = "<nr1>";
    public final static String PDF_TEXT_DESCRIPTION_PLACEHOLDER_TWO = "<nr2>";
    public final static String PDF_TEXT_DESCRIPTION_PLACEHOLDER_THREE = "<nr3>";
    public final static String PDF_TEXT_DESCRIPTION_START = "Rate ";
    public final static String PDF_TEXT_DESCRIPTION_TWO_INSTALLMENTS = "nr°" + PDF_TEXT_DESCRIPTION_PLACEHOLDER_ONE
            + " e nr°" + PDF_TEXT_DESCRIPTION_PLACEHOLDER_TWO + "";
    public final static String PDF_TEXT_DESCRIPTION_THREE_INSTALLMENTS = "nr°" + PDF_TEXT_DESCRIPTION_PLACEHOLDER_ONE
            + ", nr°" + PDF_TEXT_DESCRIPTION_PLACEHOLDER_TWO + " e nr°" + PDF_TEXT_DESCRIPTION_PLACEHOLDER_THREE;
    public final static String PDF_TEXT_DESCRIPTION_SEPARATOR = " - ";
    public final static String PDF_TEXT_PAYMENT_CHANNEL = "Utilizza la porzione di avviso relativa alla rata ed al canale di pagamento che preferisci.";
    public final static String PDF_TEXT_CREDITOR_INSTITUTION = "ENTE CREDITORE   ";
    public final static String PDF_TEXT_FISCAL_CODE = "      Cod. Fiscale   ";
    public final static String PDF_TEXT_RECIPIENT_NOTICE = "DESTINATARIO AVVISO   ";
    public final static String PDF_TEXT_HOWMUCH_WHEN_TOPAY = "QUANTO E QUANDO PAGARE ?   ";
    public final static String PDF_TEXT_OPTIONAL_INSTALLMENT_PAYMENT = "    Puoi pagare anche a rate";
    public final static String PDF_TEXT_WHERE_TOPAY = "DOVE PAGARE ? ";
    public final static String PDF_TEXT_PAYMENT_CHANNELS_LIST = "Lista dei canali di pagamento su ";
    public final static String PDF_TEXT_PAGOPA_WEBSITE = "www.pagopa.it";
    public final static String PDF_TEXT_WEBSITE_TOPAY = "PAGA SUL SITO O CON LE APP";
    public final static String PDF_TEXT_EURO = " Euro ";
    public final static String PDF_TEXT_WITHIN = " entro il ";
    public final static String PDF_TEXT_AMOUNT_FORMAT = "%,.2f";
    public final static String PDF_TEXT_EXPIRATION_DATE_FORMAT = "dd/MM/yyyy";
    public final static String PDF_TEXT_YOU_CAN_PAY = "\r \rPuoi pagare";
    public final static String PDF_TEXT_WITH = " con";
    public final static String PDF_TEXT_SINGLE_INSTALLMENT = " una unica rata";
    public final static String PDF_TEXT_POINT = ".";
    public final static String PDF_TEXT_OR = " oppure";
    public final static String PDF_TEXT_DEBT_POSITION_LIST_SIZE_PLACEHOLDER = "<debtPositionListSize>";
    public final static String PDF_TEXT_INSTALLMENT_PAYMENT_INFO = " in " + PDF_TEXT_DEBT_POSITION_LIST_SIZE_PLACEHOLDER
            + " rate (vedi pagina seguente).\rLa rateizzazione non prevede costi aggiuntivi.";
    public final static String PDF_TEXT_AMOUNT_INFO_PART1 = "L'importo è aggiornato automaticamente dal sistema e potrebbe\r";
    public final static String PDF_TEXT_AMOUNT_INFO_PART2 = "subire variazioni per eventuali sgravi, note di credito, indennità di\r";
    public final static String PDF_TEXT_AMOUNT_INFO_PART3 = "mora, sanzioni o interessi, ecc. Un operatore, il sito o l'app che userai\r";
    public final static String PDF_TEXT_AMOUNT_INFO_PART4 = "ti potrebbero quindi chiedere una cifra diversa da quella qui indicata.";
    public final static String PDF_TEXT_YOUR_CREDITOR_INSTITUTION = "del tuo Ente Creditore ";
    public final static String PDF_TEXT_CI_WEBSITE_PLACEHOLDER = "<website>";
    public final static String PDF_TEXT_CI_WEBSITE = "(" + PDF_TEXT_CI_WEBSITE_PLACEHOLDER + ")";
    public final static String PDF_TEXT_COMMA = ", ";
    public final static String PDF_TEXT_POSTE_ITALIANE = "di Poste Italiane, ";
    public final static String PDF_TEXT_PAYMENT_INFO_PART1 = "della tua Banca o degli altri canali di pagamento. ";
    public final static String PDF_TEXT_PAYMENT_INFO_PART2 = "Potrai pagare con carte, conto corrente, CBILL.";
    public final static String PDF_TEXT_PAY_ON_TERRITORY = "PAGA SUL TERRITORIO";
    public final static String PDF_TEXT_WHERE_TOPAY_POSTAL = "\rin tutti gli Uffici Postali, in Banca, in Ricevitoria, dal";
    public final static String PDF_TEXT_WHERE_TOPAY_BANKING = "\rin Banca, in Ricevitoria, dal";
    public final static String PDF_TEXT_WHERE_TOPAY_PART1 = "\rTabaccaio, al Bancomat, al Supermercato.";
    public final static String PDF_TEXT_WHERE_TOPAY_PART2 = "\rPotrai pagare in contanti, con carte o conto corrente.";
    public final static String PDF_TEXT_HOW_TOPAY = "Utilizza la porzione di avviso relativa ";
    public final static String PDF_TEXT_TO_THE_INSTALLMENT = "alla rata ed ";
    public final static String PDF_TEXT_PREFERRED_PAYMENT_CHANNEL = "al canale di pagamento che preferisci.";
    public final static String PDF_TEXT_BANK_OTHER_CHANNEL = "BANCHE E ALTRI CANALI";
    public final static String PDF_TEXT_BANK_SINGLE_INSTALLMENT = "RATA UNICA";
    public final static String PDF_TEXT_BANK_WITHIN = "  entro il      ";
    public final static String PDF_TEXT_BANK_PAYMENT_CODES_PART1 = "Qui accanto trovi il codice ";
    public final static String PDF_TEXT_BANK_PAYMENT_CODES_PART2 = "QR";
    public final static String PDF_TEXT_BANK_PAYMENT_CODES_PART3 = " e\ril codice interbancario ";
    public final static String PDF_TEXT_BANK_PAYMENT_CODES_PART4 = "CBILL\r";
    public final static String PDF_TEXT_BANK_PAYMENT_CODES_PART5 = "per pagare attraverso il circuito\rbancario e gli altri canali di\rpagamento abilitati.";
    public final static String PDF_TEXT_BANK_RECIPIENT = "Destinatario";
    public final static String PDF_TEXT_BANK_EURO = "Euro ";
    public final static String PDF_TEXT_BANK_CREDITOR_INSTITUTION = "Ente creditore";
    public final static String PDF_TEXT_BANK_PAYMENT_SUBJECT = "Oggetto del pagamento";
    public final static String PDF_TEXT_BANK_CBILL_CODE = "Codice CBILL";
    public final static String PDF_TEXT_BANK_NOTICE_NUMBER = "Codice Avviso";
    public final static String PDF_TEXT_BANK_CI_FISCAL_CODE = "Cod.Fiscale Ente Creditore";
    public final static String PDF_TEXT_POSTAL = "BOLLETTINO POSTALE PA";
    public final static String PDF_TEXT_POSTAL_BANCO = "Banco";
    public final static String PDF_TEXT_POSTAL_POSTA = "Posta";
    public final static String PDF_TEXT_POSTAL_WITHIN = "  entro il      ";
    public final static String PDF_TEXT_POSTAL_INSTALLMENT_NUMBER = "° RATA";
    public final static String PDF_TEXT_POSTAL_SINGLE_INSTALLMENT = "RATA UNICA";
    public final static String PDF_TEXT_POSTAL_INFO_PART1 = "Bollettino Postale pagabile in tutti\r";
    public final static String PDF_TEXT_POSTAL_INFO_PART2 = "gli Uffici Postali e sui canali fisici o\r";
    public final static String PDF_TEXT_POSTAL_INFO_PART3 = "digitali abilitati di Poste Italiane e\r";
    public final static String PDF_TEXT_POSTAL_INFO_PART4 = "dell'Ente Creditore";
    public final static String PDF_TEXT_POSTAL_CC = "sul C/C n.  ";
    public final static String PDF_TEXT_POSTAL_EURO = "Euro ";
    public final static String PDF_TEXT_POSTAL_HEADED = "Intestato a";
    public final static String PDF_TEXT_POSTAL_RECIPIENT = "Destinatario";
    public final static String PDF_TEXT_POSTAL_PAYMENT_SUBJECT = "Oggetto del pagamento";
    public final static String PDF_TEXT_POSTAL_NOTICE_NUMBER = "Codice Avviso";
    public final static String PDF_TEXT_POSTAL_TYPE_LABEL = "Tipo";
    public final static String PDF_TEXT_POSTAL_CI_FISCAL_CODE = "Cod.Fiscale Ente Creditore";
    public final static String PDF_TEXT_POSTAL_TYPE_VALUE = "P1";
    public final static String PDF_TEXT_POSTAL_MATRIX_INDIRIZZAMENTO_FASE = "codfase=";
    public final static String PDF_TEXT_POSTAL_MATRIX_CODICE_FASE_ACCETTAZIONE = "NBPA";
    public final static String PDF_TEXT_POSTAL_MATRIX_SEPARATORE = ";";
    public final static String PDF_TEXT_POSTAL_MATRIX_NOTICE_NUMBER_LENGTH = "18";
    public final static String PDF_TEXT_POSTAL_MATRIX_CC_LENGTH = "12";
    public final static String PDF_TEXT_POSTAL_MATRIX_ACCOUNT_NUMBER_FORMAT = "%012d";
    public final static String PDF_TEXT_POSTAL_MATRIX_AMOUNT_LENGTH = "10";
    public final static String PDF_TEXT_POSTAL_MATRIX_AMOUNT_FORMAT = "%010d";
    public final static String PDF_TEXT_POSTAL_MATRIX_DOC_TYPE_LENGTH = "3";
    public final static String PDF_TEXT_POSTAL_MATRIX_DOC_VALUE = "896";
    public final static String PDF_TEXT_POSTAL_MATRIX_ID = "1";
    public final static String PDF_TEXT_POSTAL_MATRIX_PAYMENT_PHASE = "P1";
    public final static String PDF_TEXT_POSTAL_MATRIX_IDENTIFICATION_CODE_FORMAT = "%1$-16s";
    public final static String PDF_TEXT_POSTAL_MATRIX_PAYER_REGISTRY_FORMAT = "%1$-40s";
    public final static String PDF_TEXT_POSTAL_MATRIX_PAYMENT_SUBJECT_FORMAT = "%1$-110s";
    public final static String PDF_TEXT_POSTAL_MATRIX_FILLER_FORMAT = "%1$-12s";
    public final static String PDF_TEXT_POSTAL_MATRIX_FILLER_VALUE = " ";
    public final static String PDF_TEXT_POSTAL_MATRIX_FINAL_VALUE = "A";
    public final static String PDF_TEXT_BANK_INST_INSTALLMENT_NUMBER = "° RATA";
    public final static String PDF_TEXT_BANK_INST_WITHIN = " entro il ";
    public final static String PDF_TEXT_BANK_INST_EURO = "Euro\r";
    public final static String PDF_TEXT_BANK_INST_CREDITOR_INSTITUTION = "Ente creditore\r";
    public final static String PDF_TEXT_BANK_INST_PAYMENT_SUBJECT = "Oggetto del pagamento\r";
    public final static String PDF_TEXT_BANK_INST_CBILL_CODE = "Codice CBILL\r";
    public final static String PDF_TEXT_BANK_INST_PCI_FISCAL_CODE = "Cod.Fiscale Ente Creditore\r";
    public final static String PDF_TEXT_BANK_INST_NOTICE_NUMBER = "Codice Avviso\r";
    public final static String PDF_TEXT_BANK_INST_3_BANK_INFO_PART1 = "Qui sopra trovi il codice ";
    public final static String PDF_TEXT_BANK_INST_3_BANK_INFO_PART2 = "QR";
    public final static String PDF_TEXT_BANK_INST_3_BANK_INFO_PART3 = " e il codice interbancario\r";
    public final static String PDF_TEXT_BANK_INST_3_BANK_INFO_PART4 = "CBILL ";
    public final static String PDF_TEXT_BANK_INST_3_BANK_INFO_PART5 = "per pagare attraverso il circuito bancario e gli\raltri canali dipagamento abilitati.";
    public final static String PDF_TEXT_BANK_INST_2_BANK_INFO_PART1 = "Qui sopra trovi il codice ";
    public final static String PDF_TEXT_BANK_INST_2_BANK_INFO_PART2 = "QR";
    public final static String PDF_TEXT_BANK_INST_2_BANK_INFO_PART3 = " e il codice interbancario ";
    public final static String PDF_TEXT_BANK_INST_2_BANK_INFO_PART4 = "CBILL ";
    public final static String PDF_TEXT_BANK_INST_2_BANK_INFO_PART5 = "per pagare\rattraverso il circuito bancario e gli altri canali dipagamento abilitati.";
}
