package it.gov.pagopa.hubpa.payments.annotation.validation.implement;

import java.util.Calendar;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import it.gov.pagopa.hubpa.payments.annotation.validation.FiscalCode;

public class FiscalCodeValidator implements ConstraintValidator<FiscalCode, String> {

    private TreeMap<Character, Integer> map = new TreeMap<>();

    private Map<Character, Integer> digitConversionMap;

    @Override
    public void initialize(FiscalCode arg0) {
	// DONOTHING
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1) {
	if (value == null || value.length() != 16) {
	    return false;
	}
	return checkDigitCodiceFiscale(value.toUpperCase()) == 0;

    }

    private int checkDigitCodiceFiscale(String cf) {// Algoritmo preso dal sito
	// http://www.agenziaentrate.gov.it/wps/content/Nsilib/Nsi/Home/CosaDeviFare/Richiedere/Codice+fiscale+e+tessera+sanitaria/Richiesta+TS_CF/SchedaI/Informazioni+codificazione+pf/
	// tre caratteri alfabetici per il cognome;
	// tre caratteri alfabetici per il nome;
	// due caratteri numerici per l'anno di nascita;
	// un carattere alfabetico per il mese di nascita;
	// due caratteri numerici per il giorno di nascita ed il sesso;
	// quattro caratteri, di cui uno alfabetico e tre numerici per il comune
	// italiano o per lo Stato estero di nascita.
	// ultimo campo Checksum alfabeticomap.put('L', 0);
	map.put('M', 1);
	map.put('N', 2);
	map.put('P', 3);
	map.put('Q', 4);
	map.put('R', 5);
	map.put('S', 6);
	map.put('T', 7);
	map.put('U', 8);
	map.put('V', 9);
	if(cf==null) cf="";
	digitConversionMap = Collections.unmodifiableSortedMap(map);
	Calendar calendar = Calendar.getInstance();// Cancello eventuali spazi
	cf = cancellaSpazi(cf);
	// Controllo dei primi 6 caratteri (nome e cognome)
	for (int i = 0; i < 6; i++) {
	    if (!(Character.isLetter(cf.charAt(i)))) {
		return 2;
	    }
	} // Controllo 2 caratteri per l'anno
	try {
	    calendar.set(Calendar.YEAR, getAnno(cf.substring(6, 8)));
	} catch (Exception e) {
	    return 3;
	}
	// Controllo 1 carattere per il mese
	try {
	    Integer month = getMese(cf.charAt(8));
	    if (month != null) {
		calendar.set(Calendar.MONTH, month);
	    } else {
		return 4;
	    }
	} catch (Exception e) {
	    return 4;
	} // Validazione 2 caratteri del giorno e del sesso
	try {
	    String dd = cf.substring(9, 11);
	    int day = Integer.parseInt(getDigitPerOmonimia(dd.charAt(0)) + "" + getDigitPerOmonimia(dd.charAt(1)));
	    if (day > 40) {
		day -= 40;
	    }
	    calendar.set(Calendar.DAY_OF_MONTH, day);
	} catch (Exception e) {
	    return 5;
	}
	if (!(Character.isLetter(cf.charAt(11)))) {
	    return 6;
	}
	if (!(Character.isDigit(cf.charAt(12)))) {
	    return 6;
	}
	if (!(Character.isDigit(cf.charAt(13)))) {
	    return 6;
	}
	if (!(Character.isDigit(cf.charAt(14)))) {
	    return 6;
	}
	if (!(Character.isLetter(cf.charAt(15)))) {
	    return 7;
	}
	return 0;

    }

    private String cancellaSpazi(String str) {
	if (str == null || str.length() == 0) {
	    return str;
	}
	int sz = str.length();
	char[] chs = new char[sz];
	int count = 0;
	for (int i = 0; i < sz; i++) {
	    if (!Character.isWhitespace(str.charAt(i))) {
		chs[count++] = str.charAt(i);
	    }
	}
	if (count == sz) {
	    return str;
	}
	return new String(chs, 0, count);
    }

    private int getAnno(String yy) {
	if (yy == null || yy.length() != 2) {
	    throw new IllegalArgumentException("Anno non valido " + yy);
	} else {
	    yy = getDigitPerOmonimia(yy.charAt(0)) + "" + getDigitPerOmonimia(yy.charAt(1));

	    Calendar c = Calendar.getInstance();
	    int yyyy = c.get(Calendar.YEAR);

	    String yySup = String.valueOf(yyyy).substring(0, 2);
	    String yyInf = String.valueOf(yyyy).substring(2, 4);

	    yyyy = Integer.parseInt(yyInf);
	    if (yyyy > Integer.parseInt(yy)) {
		yyyy = Integer.parseInt(yySup + yy);
	    } else {
		yyyy = Integer.parseInt(yySup + "00") - 100 + Integer.parseInt(yy);
	    }

	    return yyyy;
	}

    }

    private int getDigitPerOmonimia(char character) {
	if (Character.isDigit(character)) {
	    return Integer.parseInt(String.valueOf(character));
	} else {
	    Integer result = digitConversionMap.get(Character.toUpperCase(character));
	    if (result == null) {
		throw new IllegalArgumentException("Invalid Digit " + character);
	    }
	    return result;
	}
    }

    private Integer getMese(char character) {
	Integer mm = null;
	switch ((int) character) {
	case (int) 'A': // gennaio
	    mm = Calendar.JANUARY;
	    break;
	case (int) 'B': // febbraio
	    mm = Calendar.FEBRUARY;
	    break;
	case (int) 'C': // marzo
	    mm = Calendar.MARCH;
	    break;
	case (int) 'D': // aprile
	    mm = Calendar.APRIL;
	    break;
	case (int) 'E': // maggio
	    mm = Calendar.MAY;
	    break;
	case (int) 'H': // giugno
	    mm = Calendar.JUNE;
	    break;
	case (int) 'L': // luglio
	    mm = Calendar.JULY;
	    break;
	case (int) 'M': // agosto
	    mm = Calendar.AUGUST;
	    break;
	case (int) 'P': // settembre
	    mm = Calendar.SEPTEMBER;
	    break;
	case (int) 'R': // ottobre
	    mm = Calendar.OCTOBER;
	    break;
	case (int) 'S': // novembre
	    mm = Calendar.NOVEMBER;
	    break;
	case (int) 'T': // dicembre
	    mm = Calendar.DECEMBER;
	    break;
	default:
	    break;
	}
	return mm;
    }
}
