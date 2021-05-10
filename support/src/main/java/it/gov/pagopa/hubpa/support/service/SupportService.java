package it.gov.pagopa.hubpa.support.service;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.gov.pagopa.hubpa.support.entity.Support;
import it.gov.pagopa.hubpa.support.repository.SupportRepository;

@Service
public class SupportService {

    @Autowired
    private MailService mailService;

    @Autowired
    SupportRepository supportRepository;

    @Value("${mail.support.from}")
    private String mailSupportFrom;

    @Value("${mail.support.to}")
    private String mailSupportTo;

    @Transactional
    public Boolean save(Support support) {
	if (sendMail(support)) {
	    supportRepository.saveAndFlush(support);
	    return true;
	}

	return false;
    }

    private boolean sendMail(Support support) {
	try {
	    int type = 1;
	    if (support.getTypeContact().equalsIgnoreCase("CALL")) {
		type = 2;
	    }

	    // recupero il body
	    String body = "";
	    String subject = "";
	    body = replaceBodyPlaceholderReservation(support, body, type);
	    subject = replaceSubjectPlaceholderReservation(support, subject, type);

	    mailService.send(mailSupportFrom, support.getEmail(), null, subject, body, true);

	    // mando email per l'assistenza e recupero il template dei dati aggiuntivi
	    String bodyExtra = "";
	    body += replaceBodyPlaceholderReservation(support, bodyExtra, 3);

	    mailService.send(mailSupportFrom, mailSupportTo, null, subject, body, true);

	    return true;
	} catch (Exception e) {
	    return false;
	}
    }

    private String replaceBodyPlaceholderReservation(Support req, String textBody, int type) {
	String body = textBody;
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	if (1 == type) {
	    body = body.replace("#nomeCognome#", req.getFullName());
	    body = body.replace("#dataRichiesta#", req.getDateRequest().format(dateTimeFormatter));
	    body = body.replace("#fasciaOraria#", req.getTimeRequest());

	} else if (2 == type) {
	    body = body.replace("#nomeCognome#", req.getFullName());
	    body = body.replace("#dataRichiesta#", req.getDateRequest().format(dateTimeFormatter));
	    body = body.replace("#fasciaOraria#", req.getTimeRequest());
	    body = body.replace("#piattaforma#",
		    req.getPlatformCall().equalsIgnoreCase("GOOGLE") ? "GOOGLE MEET" : req.getPlatformCall());

	} else if (3 == type) {
	    body = body.replace("#comune#", req.getMunicipality());
	    body = body.replace("#regione#", req.getRegion());
	    body = body.replace("#provincia#", req.getProvince());
	    body = body.replace("#pec#", req.getCertifiedmail());
	    body = body.replace("#telefono#", req.getTelephone() == null ? "" : req.getTelephone());

	    body = body.replace("#nominativo#", req.getFullName());
	    body = body.replace("#email#", req.getEmail());
	    body = body.replace("#cell#", req.getTelephone() == null ? "" : req.getTelephone());
	}

	return body;
    }

    private String replaceSubjectPlaceholderReservation(Support req, String textSubject, int type) {
	String body = textSubject;
	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	if (1 == type) {
	    body = body.replace("#dataRichiesta#", req.getDateRequest().format(dateTimeFormatter));
	    body = body.replace("#fasciaOraria#", req.getTimeRequest());

	} else if (2 == type) {
	    body = body.replace("#dataRichiesta#", req.getDateRequest().format(dateTimeFormatter));
	    body = body.replace("#fasciaOraria#", req.getTimeRequest());
	    body = body.replace("#piattaforma#",
		    req.getPlatformCall().equalsIgnoreCase("GOOGLE") ? "GOOGLE MEET" : req.getPlatformCall());

	}

	return body;
    }
}
