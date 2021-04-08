package it.gov.pagopa.hubpa.servicemanagement.validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.gov.pagopa.hubpa.servicemanagement.model.InstallmentModel;
import it.gov.pagopa.hubpa.servicemanagement.model.TributeServiceModel;

@Component
public class ServiceManagementValidator implements Validator {

    private String errors = "errors";
    private String installmentDesc = "Rata ";

    @Override
    public boolean supports(Class<?> clazz) {
	return TributeServiceModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
	TributeServiceModel tributeServiceModel = (TributeServiceModel) target;

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "denomination", "denomination","Denominazione obbligatoria");
	ValidationUtils.rejectIfEmpty(errors, "idPrimaryCreditor", "idPrimaryCreditor", "Ente primario obbligatorio");
	ValidationUtils.rejectIfEmpty(errors, "idSecondaryCreditor", "idSecondaryCreditor", "Ente secondario obbligatorio");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ibanPrimary", "ibanPrimary_code", "Selezionare un IBAN primario");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ibanSecondary", "ibanSecondary_code", "Selezionare un IBAN secondario");
	ValidationUtils.rejectIfEmpty(errors, "percentageSecondary", "percentageSecondary", "Percentuale TEFA obbligatoria");
	if (errors.hasErrors()) {
	    return;
	}
	if(tributeServiceModel.getIbanPrimary().length()>27) {
	    errors.reject("ibanPrimary", "IBAN primario deve essere di 27 caratteri");
	}
	if(tributeServiceModel.getIbanSecondary().length()>27) {
	    errors.reject("ibanSecondary", "IBAN secondario deve essere di 27 caratteri");
	}
	LocalDate duoDateUnique = tributeServiceModel.getDueDateUnique();
	List<InstallmentModel> installmentList = tributeServiceModel.getInstallments();
	if (ObjectUtils.isEmpty(installmentList) && ObjectUtils.isEmpty(duoDateUnique)) {
	    errors.reject("installment", "Inserire almeno una rata o soluzione unica");
	} else {
	    BigDecimal totalePercentageSecondary = tributeServiceModel.getPercentageSecondary();
	    if (!ObjectUtils.isEmpty(duoDateUnique) && duoDateUnique.isBefore(LocalDate.of(2021, 7, 1))
		    && totalePercentageSecondary.doubleValue() > 0) {
		errors.reject("installment",
			"La data scadenza della soluzione unica deve essere maggiore del 30/06/2021");
	    }
	    if (!ObjectUtils.isEmpty(installmentList)) {

		validateInstallments(installmentList, totalePercentageSecondary, errors);
	    }
	}

    }

    private void validateInstallments(List<InstallmentModel> installmentList, BigDecimal totalePercentageSecondary,
	    Errors errors) {

	BigDecimal totalPrimary = BigDecimal.ZERO;
	BigDecimal totalSecondary = BigDecimal.ZERO;
	int count = 0;
	for (InstallmentModel installmentModel : installmentList) {
	    count++;
	    LocalDate duoDate = installmentModel.getDueDate();
	    BigDecimal percentagePrimary = installmentModel.getPercentagePrimary();
	    BigDecimal percentageSecondary = installmentModel.getPercentageSecondary();
	    if (ObjectUtils.isEmpty(duoDate)) {
		errors.reject(this.errors, installmentDesc + count + " data scadenza obbligatoria");
	    } else if (!ObjectUtils.isEmpty(percentageSecondary) && percentageSecondary.doubleValue() > 0 && duoDate.isBefore(LocalDate.of(2021, 7, 1))) {
		errors.reject(this.errors,
			installmentDesc + count + " data scadenza deve essere maggiore del 30/06/2021");
	    }
	    if (ObjectUtils.isEmpty(percentagePrimary)) {
		errors.reject(this.errors, installmentDesc + count + " percentuale TARI obbligatoria");
	    } else {
		totalPrimary=totalPrimary.add(percentagePrimary);
	    }
	    if (ObjectUtils.isEmpty(percentageSecondary)) {
		errors.reject(this.errors, installmentDesc + count + " percentuale TEFA obbligatoria");
	    } else {
		totalSecondary=totalSecondary.add(percentageSecondary);
	    }
	}
	
	checkInstallmentsTotals(totalPrimary,totalSecondary, totalePercentageSecondary,errors);
	
    }
    private void checkInstallmentsTotals(BigDecimal totalPrimary,BigDecimal totalSecondary,BigDecimal totalePercentageSecondary, Errors errors) {
	if (totalPrimary.doubleValue() != 100) {
	    errors.reject(this.errors, "La somma delle percentuali della TARI deve essere 100");
	}
	if (totalSecondary.doubleValue() != 100 && totalePercentageSecondary.doubleValue() > 0) {
	    errors.reject(this.errors, "La somma delle percentuali della TEFA deve essere 100");
	} else if (totalSecondary.doubleValue() != 0 && totalePercentageSecondary.doubleValue() == 0) {
	    errors.reject(this.errors, "La percentuale della TEFA deve essere 0");
	}
    }
}
