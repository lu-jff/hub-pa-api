package it.gov.pagopa.hubpa.servicemanagement.mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.gov.pagopa.hubpa.servicemanagement.model.InstallmentModel;
import it.gov.pagopa.hubpa.servicemanagement.model.TributeServiceModel;

public class TributeServiceModelMock {

    public final static TributeServiceModel validationOKCase1() {
	TributeServiceModel tributeServiceModel = new TributeServiceModel();
	tributeServiceModel.setDenomination("TariTefa2021");
	tributeServiceModel.setDueDateUnique(LocalDate.now().plusMonths(4));
	tributeServiceModel.setIbanPrimary("IT67P0300203280575369338247");
	tributeServiceModel.setIbanSecondary("IT76N0300203280879483594963");
	tributeServiceModel.setIdPrimaryCreditor(1L);
	tributeServiceModel.setIdSecondaryCreditor(2L);
	tributeServiceModel.setPercentageSecondary(5);
	List<InstallmentModel> listInstallment = new ArrayList<>();

	InstallmentModel installmentModel = new InstallmentModel();
	installmentModel.setDueDate(LocalDate.now().plusMonths(2));
	installmentModel.setPercentagePrimary(20);
	installmentModel.setPercentageSecondary(0);
	listInstallment.add(installmentModel);

	installmentModel = new InstallmentModel();
	installmentModel.setDueDate(LocalDate.now().plusMonths(3));
	installmentModel.setPercentagePrimary(50);
	installmentModel.setPercentageSecondary(20);
	listInstallment.add(installmentModel);

	installmentModel = new InstallmentModel();
	installmentModel.setDueDate(LocalDate.now().plusMonths(4));
	installmentModel.setPercentagePrimary(30);
	installmentModel.setPercentageSecondary(80);
	listInstallment.add(installmentModel);

	tributeServiceModel.setInstallments(listInstallment);

	return tributeServiceModel;
    }

    public final static TributeServiceModel validationKOCase1() {
	TributeServiceModel modelMock = validationOKCase1();
	modelMock.setIbanPrimary("");
	return modelMock;
    }

    public final static TributeServiceModel validationKOCase2() {
	TributeServiceModel modelMock = TributeServiceModelMock.validationOKCase1();
	modelMock.setInstallments(null);
	modelMock.setDueDateUnique(null);
	return modelMock;
    }

    public final static TributeServiceModel validationKOCase3() {
	TributeServiceModel modelMock = TributeServiceModelMock.validationOKCase1();
	modelMock.getInstallments().get(0).setDueDate(LocalDate.of(2021, 1, 1));
	modelMock.getInstallments().get(0).setPercentageSecondary(5);
	return modelMock;
    }

    public final static TributeServiceModel validationKOCase4() {
	TributeServiceModel modelMock = TributeServiceModelMock.validationOKCase1();
	modelMock.getInstallments().get(0).setPercentagePrimary(100);
	modelMock.getInstallments().get(2).setPercentageSecondary(110);
	return modelMock;
    }

    public final static TributeServiceModel validationKOCase5() {
	TributeServiceModel modelMock = TributeServiceModelMock.validationOKCase1();
	modelMock.setInstallments(null);
	modelMock.setDueDateUnique(LocalDate.of(2021, 1, 1));
	return modelMock;
    }

    public final static TributeServiceModel validationKOCase6() {
	TributeServiceModel modelMock = TributeServiceModelMock.validationOKCase1();
	modelMock.setPercentageSecondary(0);
	return modelMock;
    }

    public final static TributeServiceModel validationKOCase7() {
	TributeServiceModel modelMock = TributeServiceModelMock.validationOKCase1();
	modelMock.setPercentageSecondary(0);
	modelMock.setDueDateUnique(LocalDate.of(2021, 1, 1));
	modelMock.getInstallments().get(0).setDueDate(null);
	modelMock.getInstallments().get(1).setPercentagePrimary(null);
	modelMock.getInstallments().get(2).setPercentageSecondary(null);

	return modelMock;
    }

    public final static TributeServiceModel validationKOCase8() {
	TributeServiceModel modelMock = validationOKCase1();
	modelMock.setIbanPrimary("IT0121221212121212121212121122121212121");
	modelMock.setIbanSecondary("IT0121221212121212121212121122121212121");
	return modelMock;
    }

    public final static TributeServiceModel validationOKCase2() {
	TributeServiceModel modelMock = TributeServiceModelMock.validationOKCase1();
	modelMock.setDueDateUnique(null);
	modelMock.setPercentageSecondary(0);
	modelMock.getInstallments().get(0).setPercentageSecondary(0);
	modelMock.getInstallments().get(1).setPercentageSecondary(0);
	modelMock.getInstallments().get(2).setPercentageSecondary(0);
	return modelMock;
    }

    public final static TributeServiceModel validationOKCase3() {
	TributeServiceModel modelMock = TributeServiceModelMock.validationOKCase1();
	modelMock.setInstallments(null);
	modelMock.setDueDateUnique(LocalDate.of(2021, 1, 1));
	modelMock.setPercentageSecondary(0);
	return modelMock;
    }
}
