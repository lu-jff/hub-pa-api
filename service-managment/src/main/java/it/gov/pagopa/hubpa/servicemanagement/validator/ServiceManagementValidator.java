package it.gov.pagopa.hubpa.servicemanagement.validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
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
    
    @Value("${tefa.date.check.enable}")
    private Boolean checkDateEnable;
    
    @Value("${tefa.date.min}")
    private String dateMinTefaStr;

	private static final Pattern IBAN_PATTERN = Pattern.compile("^(?:(?:IT|SM)\\d{2}[A-Z]\\d{22}|CY\\d{2}[A-Z]\\d{23}|NL\\d{2}[A-Z]{4}\\d{10}|LV\\d{2}[A-Z]{4}\\d{13}|(?:BG|BH|GB|IE)\\d{2}[A-Z]{4}\\d{14}|GI\\d{2}[A-Z]{4}\\d{15}|RO\\d{2}[A-Z]{4}\\d{16}|KW\\d{2}[A-Z]{4}\\d{22}|MT\\d{2}[A-Z]{4}\\d{23}|NO\\d{13}|(?:DK|FI|GL|FO)\\d{16}|MK\\d{17}|(?:AT|EE|KZ|LU|XK)\\d{18}|(?:BA|HR|LI|CH|CR)\\d{19}|(?:GE|DE|LT|ME|RS)\\d{20}|IL\\d{21}|(?:AD|CZ|ES|MD|SA)\\d{22}|PT\\d{23}|(?:BE|IS)\\d{24}|(?:FR|MR|MC)\\d{25}|(?:AL|DO|LB|PL)\\d{26}|(?:AZ|HU)\\d{27}|(?:GR|MU)\\d{28})$");

    @Override
    public boolean supports(Class<?> clazz) {
	return TributeServiceModel.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
	TributeServiceModel tributeServiceModel = (TributeServiceModel) target;
	
	LocalDate dateMinTefa=LocalDate.parse(dateMinTefaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "denomination", "denomination","Denominazione obbligatoria");
	ValidationUtils.rejectIfEmpty(errors, "percentageSecondary", "percentageSecondary", "Percentuale TEFA obbligatoria");
	ValidationUtils.rejectIfEmpty(errors, "fiscalCodePrimaryCreditor", "fiscalCodePrimaryCreditor", "Codice fiscale ente primario obbligatorio");
	ValidationUtils.rejectIfEmpty(errors, "fiscalCodeSecondaryCreditor", "fiscalCodeSecondaryCreditor", "Codice fiscale ente secondario obbligatorio");

	if (errors.hasErrors()) {
	    return;
	}

	if (!this.checkIbanExist(tributeServiceModel)) {
	    errors.reject("ibanExistence", "Iban postale o bancario configurati parzialmente o assenti");
	}
	if (!this.ibanPredicate(tributeServiceModel.getIbanPrimary())) {
		errors.reject("ibanPrimary", "IBAN primario non valido");
	}
	if (!this.ibanPredicate(tributeServiceModel.getIbanSecondary())) {
		errors.reject("ibanSecondary", "IBAN secondario non valido");
	}
	if (!this.ibanPredicate(tributeServiceModel.getPostalIbanPrimary())) {
	    errors.reject("postalIbanPrimary", "IBAN postale primario non valido");
	}
	if (!this.ibanPredicate(tributeServiceModel.getPostalIbanSecondary())) {
	    errors.reject("postalIbanSecondary", "IBAN postale secondario non valido");
	}
	
	LocalDate duoDateUnique = tributeServiceModel.getDueDateUnique();
	List<InstallmentModel> installmentList = tributeServiceModel.getInstallments();
	if (ObjectUtils.isEmpty(installmentList) && ObjectUtils.isEmpty(duoDateUnique)) {
	    errors.reject("installment", "Inserire almeno una rata o soluzione unica");
	} else {
	    BigDecimal totalePercentageSecondary = tributeServiceModel.getPercentageSecondary();
	    if (checkDateEnable.booleanValue() && !ObjectUtils.isEmpty(duoDateUnique) && duoDateUnique.isBefore(dateMinTefa)
		    && totalePercentageSecondary.doubleValue() > 0) {
		errors.reject("installment",
			"La data scadenza della soluzione unica deve essere maggiore del "+dateMinTefa.minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	    }
	    if (!ObjectUtils.isEmpty(installmentList)) {

		validateInstallments(installmentList, totalePercentageSecondary, errors, dateMinTefa);
	    }
	}

    }

    private void validateInstallments(List<InstallmentModel> installmentList, BigDecimal totalePercentageSecondary,
	    Errors errors,LocalDate dateMinTefa) {

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
	    } else if (checkDateEnable.booleanValue() && !ObjectUtils.isEmpty(percentageSecondary) && percentageSecondary.doubleValue() > 0 && duoDate.isBefore(LocalDate.of(2021, 7, 1))) {
		errors.reject(this.errors,
			installmentDesc + count + " data scadenza deve essere maggiore del "+dateMinTefa.minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
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

	private boolean checkIbanExist(TributeServiceModel tributeServiceModel) {

		String bIbanP = tributeServiceModel.getIbanPrimary();
		String pIbanP = tributeServiceModel.getPostalIbanPrimary();
		String pHoldP = tributeServiceModel.getPostalIbanHolderPrimary();
		String pAuthP = tributeServiceModel.getPostalAuthCodePrimary();

		String bIbanS = tributeServiceModel.getIbanSecondary();
		String pIbanS = tributeServiceModel.getPostalIbanSecondary();
		String pHoldS = tributeServiceModel.getPostalIbanHolderSecondary();
		String pAuthS = tributeServiceModel.getPostalAuthCodeSecondary();

		boolean checkP = pIbanP != null && pHoldP != null && pAuthP != null
				|| bIbanP != null && pIbanP == null && pHoldP == null && pAuthP == null;

		boolean checkS = pIbanS != null && pHoldS != null && pAuthS != null
				|| bIbanS != null && pIbanS == null && pHoldS == null && pAuthS == null;

		return checkP && checkS;
	}

	private boolean ibanPredicate(String iban){

		return iban == null || IBAN_PATTERN.matcher(iban).matches();
	}
}
