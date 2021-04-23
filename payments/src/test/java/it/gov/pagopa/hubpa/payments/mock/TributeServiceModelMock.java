package it.gov.pagopa.hubpa.payments.mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.gov.pagopa.hubpa.payments.model.tribute.InstallmentModel;
import it.gov.pagopa.hubpa.payments.model.tribute.TributeServiceModel;

public class TributeServiceModelMock {

    public final static TributeServiceModel validationOKCase1() {
	TributeServiceModel tributeServiceModel = new TributeServiceModel();
	tributeServiceModel.setDenomination("TariTefa2021");
	tributeServiceModel.setDueDateUnique(LocalDate.now().plusMonths(4));
	tributeServiceModel.setIbanPrimary("IT67P0300203280575369338247");
	tributeServiceModel.setIbanSecondary("IT76N0300203280879483594963");
	tributeServiceModel.setFiscalCodePrimaryCreditor("VBMPGR31H03F993U");
	tributeServiceModel.setFiscalCodeSecondaryCreditor("FMMFLN90H13E027F");
	tributeServiceModel.setPercentageSecondary(BigDecimal.valueOf(5));
	List<InstallmentModel> listInstallment = new ArrayList<>();

	InstallmentModel installmentModel = new InstallmentModel();
	installmentModel.setDueDate(LocalDate.now().plusMonths(2));
	installmentModel.setPercentagePrimary(BigDecimal.valueOf(20));
	installmentModel.setPercentageSecondary(BigDecimal.valueOf(0));
	listInstallment.add(installmentModel);

	installmentModel = new InstallmentModel();
	installmentModel.setDueDate(LocalDate.now().plusMonths(3));
	installmentModel.setPercentagePrimary(BigDecimal.valueOf(50));
	installmentModel.setPercentageSecondary(BigDecimal.valueOf(20));
	listInstallment.add(installmentModel);

	installmentModel = new InstallmentModel();
	installmentModel.setDueDate(LocalDate.now().plusMonths(4));
	installmentModel.setPercentagePrimary(BigDecimal.valueOf(30));
	installmentModel.setPercentageSecondary(BigDecimal.valueOf(80));
	listInstallment.add(installmentModel);

	tributeServiceModel.setInstallments(listInstallment);

	return tributeServiceModel;
    }

}
