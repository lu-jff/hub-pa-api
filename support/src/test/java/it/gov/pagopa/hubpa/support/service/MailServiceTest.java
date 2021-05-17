package it.gov.pagopa.hubpa.support.service;

import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {
    @InjectMocks
    MailService mailService;

    @Mock
    TransportService transportService;

    @Test
    void sendTest(){
        ReflectionTestUtils.setField(mailService, "mailSmtpHost", "mailSmtpHost");
        ReflectionTestUtils.setField(mailService, "mailSmtpPort", "mailSmtpPort");
        ReflectionTestUtils.setField(mailService, "mailSmtpAuth", "mailSmtpAuth");
        ReflectionTestUtils.setField(mailService, "mailUser", "mailUser");
        ReflectionTestUtils.setField(mailService, "mailPassword", "mailPassword");
        //Test1
        assertThatNoException().isThrownBy(() -> { 
            mailService.send("user@host.domain", "user@host.domain", "cc", "subject", "message", Boolean.FALSE);
        });
        //test2
        assertThatNoException().isThrownBy(() -> { 
            mailService.send("user@host.domain", "user@host.domain", "cc", "subject", "message", Boolean.TRUE);
        });
        //Test3
        ReflectionTestUtils.setField(mailService, "mailSmtpAuth", "true");
        assertThatNoException().isThrownBy(() -> { 
            mailService.send("user@host.domain", "user@host.domain", "cc", "subject", "message", Boolean.FALSE);
        });
    }
}
