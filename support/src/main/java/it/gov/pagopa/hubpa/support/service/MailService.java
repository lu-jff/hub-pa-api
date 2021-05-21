package it.gov.pagopa.hubpa.support.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final Properties properties = new Properties();

    @Value("${global.mail.smtp.host}")
    private String mailSmtpHost;

    @Value("${global.mail.smtp.port}")
    private String mailSmtpPort;

    @Value("${global.mail.smtp.auth}")
    private String mailSmtpAuth;

    @Value("${global.mail.user}")
    private String mailUser;

    @Value("${global.mail.password}")
    private String mailPassword;

    private boolean smtpAuth = false;

    @Autowired
    private TransportService transportService;

    private void initProperties() {
	properties.put("mail.smtp.host", mailSmtpHost);
	properties.put("mail.smtp.port", mailSmtpPort);
	properties.put("mail.smtp.starttls.enable", "true"); 
	String smtpAuthVal = mailSmtpAuth;
	if ("true".equals(smtpAuthVal)) {
	    this.smtpAuth = true;
	}
	properties.put("mail.smtp.auth", smtpAuthVal);
    }

    public void send(String from, String to, String cc, String subject, String message, boolean html) throws MessagingException {
	this.initProperties();
	Session session = Session.getInstance(properties);
	Message mail = new MimeMessage(session);
	mail.setFrom(new InternetAddress(from));
	mail.setSentDate(new Date());
	mail.addRecipients(RecipientType.TO, InternetAddress.parse(to));
	if (cc != null && !cc.isEmpty()) {
	    mail.addRecipients(RecipientType.CC, InternetAddress.parse(cc));
	}
	mail.setSubject(subject);

	MimeBodyPart messageBodyPart = new MimeBodyPart();
	if (html) {
	    messageBodyPart.setText(message, "utf-8", "html");
	} else {
	    messageBodyPart.setText(message);
	}
	// per file deposistati in un path raggiungibile dall'applicazione
	Multipart multipart = new MimeMultipart();
	multipart.addBodyPart(messageBodyPart);
	mail.setContent(multipart);

	if (smtpAuth) {
	    transportService.send(mail, mailUser, mailPassword);
	} else {
	    transportService.send(mail);
	}
    }
}
