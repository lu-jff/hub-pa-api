package it.gov.pagopa.hubpa.payments.annotation.validation.implement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import it.gov.pagopa.hubpa.payments.annotation.validation.Iban;

public class IbanValidator implements ConstraintValidator<Iban, String> {

    private static final String IBAN_ITALY = "IT\\d{2}[A-Z]{1}\\d{10}[A-Z0-9]{12}";
    private static final String IBAN_ANDORRA = "AD\\d{10}[A-Z0-9]{12}";
    private static final String IBAN_EMIRATES = "AE\\d{21}";
    private static final String IBAN_ALBANIA = "AL\\d{10}[A-Z0-9]{16}";

    private static final List<Pattern> IBAN_PATTERNS = new ArrayList<Pattern>(Arrays.asList(Pattern.compile(IBAN_ITALY),
            Pattern.compile(IBAN_ANDORRA), Pattern.compile(IBAN_EMIRATES), Pattern.compile(IBAN_ALBANIA)));

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {

        return value == null || value.length() == 0 ? false
                : IBAN_PATTERNS.stream().allMatch(pattern -> pattern.matcher(value).matches());
    }
}
