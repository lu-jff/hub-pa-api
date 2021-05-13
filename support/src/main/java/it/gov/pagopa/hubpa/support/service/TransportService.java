package it.gov.pagopa.hubpa.support.service;

import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

@Service
public class TransportService {

    public void send(Message mail, String mailUser, String mailPassword) throws MessagingException {
        Transport.send(mail, mailUser, mailPassword);
    }

    public void send(Message mail) throws MessagingException {
        Transport.send(mail);
    }
}
