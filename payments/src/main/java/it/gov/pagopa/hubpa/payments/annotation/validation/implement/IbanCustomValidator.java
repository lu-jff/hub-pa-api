package it.gov.pagopa.hubpa.payments.annotation.validation.implement;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.validator.routines.IBANValidator;

import it.gov.pagopa.hubpa.payments.annotation.validation.Iban;

public class IbanCustomValidator implements ConstraintValidator<Iban, String> {
  
    private static final IBANValidator ibanValidator = IBANValidator.getInstance();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {

        return value == null || value.length() == 0 ? Boolean.FALSE
                :  ibanValidator.isValid(value);
    }
}
