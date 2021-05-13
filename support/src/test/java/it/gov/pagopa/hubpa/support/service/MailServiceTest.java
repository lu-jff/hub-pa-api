package it.gov.pagopa.hubpa.support.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class MailServiceTest {
    @InjectMocks
    MailService mailService;

    @Mock
    TransportService transportService;

    @SneakyThrows
    @Test
    public void sendTest(){
        ReflectionTestUtils.setField(mailService, "mailSmtpHost", "mailSmtpHost");
        ReflectionTestUtils.setField(mailService, "mailSmtpPort", "mailSmtpPort");
        ReflectionTestUtils.setField(mailService, "mailSmtpAuth", "mailSmtpAuth");
        ReflectionTestUtils.setField(mailService, "mailUser", "mailUser");
        ReflectionTestUtils.setField(mailService, "mailPassword", "mailPassword");
        mailService.send("user@host.domain", "user@host.domain", "cc", "subject", "message", false);
    }

    @SneakyThrows
    @Test
    public void sendWithAuthTest(){
        ReflectionTestUtils.setField(mailService, "mailSmtpHost", "mailSmtpHost");
        ReflectionTestUtils.setField(mailService, "mailSmtpPort", "mailSmtpPort");
        ReflectionTestUtils.setField(mailService, "mailSmtpAuth", "true");
        ReflectionTestUtils.setField(mailService, "mailUser", "mailUser");
        ReflectionTestUtils.setField(mailService, "mailPassword", "mailPassword");
        mailService.send("user@host.domain", "user@host.domain", "cc", "subject", "message", false);
    }

    @SneakyThrows
    @Test
    public void sendTestWithHtml(){
        ReflectionTestUtils.setField(mailService, "mailSmtpHost", "mailSmtpHost");
        ReflectionTestUtils.setField(mailService, "mailSmtpPort", "mailSmtpPort");
        ReflectionTestUtils.setField(mailService, "mailSmtpAuth", "mailSmtpAuth");
        ReflectionTestUtils.setField(mailService, "mailUser", "mailUser");
        ReflectionTestUtils.setField(mailService, "mailPassword", "mailPassword");
        mailService.send("user@host.domain", "user@host.domain", "cc", "subject", "message", true);
    }
}
