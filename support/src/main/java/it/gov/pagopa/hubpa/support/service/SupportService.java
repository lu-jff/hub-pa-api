package it.gov.pagopa.hubpa.support.service;

import it.gov.pagopa.hubpa.support.entity.Support;
import it.gov.pagopa.hubpa.support.repository.SupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

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
    
    private static final String PLACEHOLDER_DATA_RICHIESTA="#dataRichiesta#";
    private static final String PLACEHOLDER_FASCIAORARIA="#fasciaOraria#";

    @Transactional
    public Boolean save(Support support) {
        if (sendMail(support)) {
            supportRepository.saveAndFlush(support);
            return true;
        }
        return false;
    }

    private String getFileFromResourceAsStream(String fileName) throws IOException {
        String result;
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("file not found! " + fileName);
            } else {
                result = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
            }
        }
        return result;
    }

    private boolean sendMail(Support support) {
        try {
            int type = 1;
            if (support.getTypeContact().equalsIgnoreCase("CALL")) {
                type = 2;
            }

            String subject = getFileFromResourceAsStream(type == 1 ? "subject_telefonico.txt" : "subject_call.txt");
            String body = getFileFromResourceAsStream(type == 1 ? "supporto_telefonico.txt" : "supporto_call.txt");
            String bodyExtra = getFileFromResourceAsStream("supporto_assistenza_testo_aggiuntivo.txt");

            subject = replaceSubjectPlaceholderReservation(support, subject, type);
            body = replaceBodyPlaceholderReservation(support, body, type);
            bodyExtra = body + replaceBodyPlaceholderReservation(support, bodyExtra, 3);

            mailService.send(mailSupportFrom, support.getEmail(), null, subject, body, true);
            mailService.send(mailSupportFrom, mailSupportTo, null, subject, bodyExtra, true);

            support.setMailSentDate(LocalDateTime.now());

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
            body = body.replace(PLACEHOLDER_DATA_RICHIESTA, req.getDateRequest().format(dateTimeFormatter));
            body = body.replace(PLACEHOLDER_FASCIAORARIA, req.getTimeRequest());

        } else if (2 == type) {
            body = body.replace("#nomeCognome#", req.getFullName());
            body = body.replace(PLACEHOLDER_DATA_RICHIESTA, req.getDateRequest().format(dateTimeFormatter));
            body = body.replace(PLACEHOLDER_FASCIAORARIA, req.getTimeRequest());
            body = body.replace("#piattaforma#",
                    req.getPlatformCall().equalsIgnoreCase("GOOGLE") ? "GOOGLE MEET" : req.getPlatformCall());

        } else if (3 == type) {
            body = body.replace("#comune#", req.getMunicipality());
            body = body.replace("#regione#", req.getRegion());
            body = body.replace("#provincia#", req.getProvince());
            body = body.replace("#pec#", req.getCertifiedMail());
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
            body = body.replace(PLACEHOLDER_DATA_RICHIESTA, req.getDateRequest().format(dateTimeFormatter));
            body = body.replace(PLACEHOLDER_FASCIAORARIA, req.getTimeRequest());

        } else if (2 == type) {
            body = body.replace(PLACEHOLDER_DATA_RICHIESTA, req.getDateRequest().format(dateTimeFormatter));
            body = body.replace(PLACEHOLDER_FASCIAORARIA, req.getTimeRequest());
            body = body.replace("#piattaforma#",
                    req.getPlatformCall().equalsIgnoreCase("GOOGLE") ? "GOOGLE MEET" : req.getPlatformCall());

        }

        return body;
    }
}
