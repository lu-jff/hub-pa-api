package it.gov.pagopa.hubpa.payments.mapper;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import it.gov.pagopa.hubpa.payments.enumeration.PaymentOptionStatusEnum;
import it.gov.pagopa.hubpa.payments.enumeration.PaymentStatusEnum;
import it.gov.pagopa.hubpa.payments.model.DebitorModel;
import it.gov.pagopa.hubpa.payments.model.PaymentOptionsModel;
import it.gov.pagopa.hubpa.payments.model.PaymentPositionModel;
import it.gov.pagopa.hubpa.payments.model.PaymentsModel;
import it.gov.pagopa.hubpa.payments.model.TransfersModel;
import it.gov.pagopa.hubpa.payments.model.UploadCsvModel;
import it.gov.pagopa.hubpa.payments.model.csv.CsvRowModel;
import it.gov.pagopa.hubpa.payments.model.tribute.InstallmentModel;
import it.gov.pagopa.hubpa.payments.model.tribute.TributeServiceModel;

public class ConvertUploadCsvModelToPaymentsModel implements Converter<UploadCsvModel, PaymentsModel> {

    private static final String TAXONOMY_PRIMARY=System.getenv().get("TAXONOMY_PRIMARY");
    private static final String TAXONOMY_SECONDARY=System.getenv().get("TAXONOMY_SECONDARY");
    
    @Override
    public PaymentsModel convert(MappingContext<UploadCsvModel, PaymentsModel> context) {
	UploadCsvModel source = context.getSource();
	PaymentsModel destination = new PaymentsModel();

	for (CsvRowModel row : source.getCsv().getRows()) {
	    DebitorModel debitor = this.createDebitor(row, source.getTributeService(), source.getJobId());
	    destination.getDebitors().add(debitor);
	}

	return destination;
    }

    private DebitorModel createDebitor(CsvRowModel row, TributeServiceModel tributeServiceModel, Long jobId) {
	DebitorModel debitorModel = new DebitorModel();
	debitorModel.setFiscalCode(row.getFiscalCode());
	debitorModel.setType(row.getType());
	debitorModel.setName(row.getName());
	debitorModel.setSurname(row.getSurname());
	debitorModel.setPhone(row.getPhone());
	debitorModel.setAddress(row.getAddress());
	debitorModel.setNumber(row.getNumber());
	debitorModel.setArea(row.getArea());
	debitorModel.setProvince(row.getProvince());
	debitorModel.setCountry(row.getCountry());
	debitorModel.setCap(row.getCap());
	debitorModel.setEmail(row.getEmail());
	debitorModel.setIdTenant(row.getIdTenant());

	debitorModel.getPaymentPosition().add(createPaymentPosition(tributeServiceModel, jobId, row));

	return debitorModel;
    }

    private PaymentPositionModel createPaymentPosition(TributeServiceModel tributeServiceModel, Long jobId,
	    CsvRowModel row) {

	BigDecimal amount = row.getAmount();
	String reason = row.getReason();
	LocalDate dueDateUnique = tributeServiceModel.getDueDateUnique();

	PaymentPositionModel paymentPositionModel = new PaymentPositionModel();
	paymentPositionModel.setOrganizationFiscalCode(tributeServiceModel.getFiscalCodePrimaryCreditor());
	paymentPositionModel.setCompanyName(null);
	paymentPositionModel.setOfficeName(null);
	paymentPositionModel.setStatus(PaymentStatusEnum.BOZZA.getStatus());
	paymentPositionModel.setDescription(tributeServiceModel.getDenomination());
	paymentPositionModel.setJobId(jobId);
	paymentPositionModel.setAmount(row.getAmount());

	List<InstallmentModel> installments = tributeServiceModel.getInstallments();
	installments=this.getArrayListIfNull(installments);

	BigDecimal totalPercentageInstallmentPrimary = new BigDecimal(100);
	BigDecimal totalPercentageInstallmentSecondary = new BigDecimal(100);

	BigDecimal totalAmountPrimary = amount;
	BigDecimal totalAmountSecondary = BigDecimal.ZERO;
	BigDecimal totalPercentageSecondary = tributeServiceModel.getPercentageSecondary();
	if (totalPercentageSecondary.doubleValue() > 0) {
	    totalAmountSecondary = this.percentage(amount, totalPercentageSecondary)
		    .round(new MathContext(4, RoundingMode.HALF_UP));
	    totalAmountPrimary = amount.subtract(totalAmountSecondary);
	}

	BigDecimal residualAmountPrimary = new BigDecimal(totalAmountPrimary.toString());
	BigDecimal residualAmountSecondary = new BigDecimal(totalAmountSecondary.toString());

	if (dueDateUnique != null) {
	    paymentPositionModel.getPaymentOptions().add(this.createPaymentPositionUnique(tributeServiceModel, row,
		    totalAmountPrimary, totalAmountSecondary));
	}

	for (InstallmentModel installment : installments) {

	    PaymentOptionsModel paymentOptionsModel = new PaymentOptionsModel();
	    paymentOptionsModel.setFiscalCode(tributeServiceModel.getFiscalCodePrimaryCreditor());
	    paymentOptionsModel.setDuoDate(installment.getDueDate());
	    paymentOptionsModel.setRetentionDate(null);
	    paymentOptionsModel.setIsConclusive(Boolean.FALSE);
	    paymentOptionsModel.setMetadata(null);
	    paymentOptionsModel.setStatus(PaymentOptionStatusEnum.NON_PAGATO.getStatus());
	    

	    BigDecimal percentagePrimary = installment.getPercentagePrimary();
	    BigDecimal percentageSecondary = installment.getPercentageSecondary();
	    BigDecimal installmentAmount = BigDecimal.ZERO;

	    if (percentagePrimary != null && percentagePrimary.doubleValue() > 0) {
		BigDecimal partialAmount;
		totalPercentageInstallmentPrimary = totalPercentageInstallmentPrimary.subtract(percentagePrimary);
		partialAmount = new BigDecimal(residualAmountPrimary.toString());
		if (totalPercentageInstallmentPrimary.compareTo(BigDecimal.ZERO) > 0) {
		    partialAmount = this.percentage(totalAmountPrimary, percentagePrimary)
			    .round(new MathContext(4, RoundingMode.HALF_UP));
		}

		TransfersModel transfersModel = new TransfersModel();
		transfersModel.setPartialAmount(partialAmount);
		transfersModel.setIban(tributeServiceModel.getIbanPrimary());
		transfersModel.setOrganizationFiscalCode(tributeServiceModel.getFiscalCodePrimaryCreditor());
		transfersModel.setReason(reason);
		transfersModel.setTaxonomy(TAXONOMY_PRIMARY);

		paymentOptionsModel.getTransfers().add(transfersModel);

		installmentAmount = installmentAmount.add(partialAmount);

		residualAmountPrimary = residualAmountPrimary.subtract(partialAmount);
	    }
	    if (percentageSecondary != null && percentageSecondary.doubleValue() > 0) {
		BigDecimal partialAmount = new BigDecimal(residualAmountSecondary.toString());
		if (totalPercentageInstallmentSecondary.compareTo(BigDecimal.ZERO) > 0) {
		    partialAmount = this.percentage(totalAmountSecondary, percentageSecondary)
			    .round(new MathContext(4, RoundingMode.HALF_UP));
		}

		totalPercentageInstallmentSecondary = totalPercentageInstallmentSecondary.subtract(percentageSecondary);

		TransfersModel transfersModel = new TransfersModel();
		transfersModel.setPartialAmount(partialAmount);
		transfersModel.setIban(tributeServiceModel.getIbanSecondary());
		transfersModel.setOrganizationFiscalCode(tributeServiceModel.getFiscalCodeSecondaryCreditor());
		transfersModel.setReason(reason);
		transfersModel.setTaxonomy(TAXONOMY_SECONDARY);

		paymentOptionsModel.getTransfers().add(transfersModel);

		installmentAmount = installmentAmount.add(partialAmount);

		residualAmountSecondary = residualAmountSecondary.subtract(partialAmount);
	    }
	    paymentOptionsModel.setAmount(installmentAmount);
	    paymentPositionModel.getPaymentOptions().add(paymentOptionsModel);
	}
	

	return paymentPositionModel;
    }

    private List<InstallmentModel> getArrayListIfNull(List<InstallmentModel> installments) {
	if(installments==null) { 
	    installments=new ArrayList<>();	
	}
	return installments;

    }

    private PaymentOptionsModel createPaymentPositionUnique(TributeServiceModel tributeServiceModel, CsvRowModel row,
	    BigDecimal totalAmountPrimary, BigDecimal totalAmountSecondary) {
	PaymentOptionsModel paymentOptionsModel = new PaymentOptionsModel();
	paymentOptionsModel.setFiscalCode(tributeServiceModel.getFiscalCodePrimaryCreditor());
	paymentOptionsModel.setDuoDate(tributeServiceModel.getDueDateUnique());
	paymentOptionsModel.setRetentionDate(null);
	paymentOptionsModel.setIsConclusive(Boolean.TRUE);
	paymentOptionsModel.setMetadata(null);
	paymentOptionsModel.setStatus(PaymentOptionStatusEnum.NON_PAGATO.getStatus());

	TransfersModel transfersModel = new TransfersModel();
	transfersModel.setPartialAmount(totalAmountPrimary);
	transfersModel.setIban(tributeServiceModel.getIbanPrimary());
	transfersModel.setOrganizationFiscalCode(tributeServiceModel.getFiscalCodePrimaryCreditor());
	transfersModel.setReason(row.getReason());
	transfersModel.setTaxonomy(TAXONOMY_PRIMARY);

	paymentOptionsModel.getTransfers().add(transfersModel);

	if (totalAmountSecondary.doubleValue() > 0) {
	    transfersModel = new TransfersModel();
	    transfersModel.setPartialAmount(totalAmountSecondary);
	    transfersModel.setIban(tributeServiceModel.getIbanSecondary());
	    transfersModel.setOrganizationFiscalCode(tributeServiceModel.getFiscalCodeSecondaryCreditor());
	    transfersModel.setReason(row.getReason());
	    transfersModel.setTaxonomy(TAXONOMY_SECONDARY);

	    paymentOptionsModel.getTransfers().add(transfersModel);
	}
	paymentOptionsModel.setAmount(totalAmountPrimary.add(totalAmountSecondary));
	return paymentOptionsModel;
    }

    private BigDecimal percentage(BigDecimal base, BigDecimal pct) {
	return base.multiply(pct).divide(new BigDecimal(100));
    }

}
