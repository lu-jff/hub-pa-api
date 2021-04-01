package it.gov.pagopa.hubpa.servicemanagement.mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.gov.pagopa.hubpa.servicemanagement.model.InstallmentModel;
import it.gov.pagopa.hubpa.servicemanagement.model.TributeServiceModel;

public class TributeServiceModelMock {

    public final static TributeServiceModel getMock() {
	TributeServiceModel tributeServiceModel=new TributeServiceModel();
	tributeServiceModel.setDenomination("TariTefa2021");
	tributeServiceModel.setDueDateUnique(LocalDate.now().plusMonths(4));
	tributeServiceModel.setIbanPrimary("IT67P0300203280575369338247");
	tributeServiceModel.setIbanSecondary("IT76N0300203280879483594963");
	tributeServiceModel.setIdPrimaryCreditor(1L);
	tributeServiceModel.setIdSecondaryCreditor(2L);
	tributeServiceModel.setPercentageSecondary(5);
	List<InstallmentModel> listInstallment = new ArrayList<>();
	
	InstallmentModel installmentModel=new InstallmentModel();
	installmentModel.setDueDate(LocalDate.now().plusMonths(2));
	installmentModel.setPercentagePrimary(20);
	installmentModel.setPercentageSecondary(0);
	listInstallment.add(installmentModel);
	
	installmentModel=new InstallmentModel();
	installmentModel.setDueDate(LocalDate.now().plusMonths(3));
	installmentModel.setPercentagePrimary(50);
	installmentModel.setPercentageSecondary(20);
	listInstallment.add(installmentModel);
	
	installmentModel=new InstallmentModel();
	installmentModel.setDueDate(LocalDate.now().plusMonths(4));
	installmentModel.setPercentagePrimary(30);
	installmentModel.setPercentageSecondary(80);
	listInstallment.add(installmentModel);
	
	tributeServiceModel.setInstallments(listInstallment);
	
	return tributeServiceModel;
    }
    
}
