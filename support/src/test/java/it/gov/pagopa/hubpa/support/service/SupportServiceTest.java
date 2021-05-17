package it.gov.pagopa.hubpa.support.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import it.gov.pagopa.hubpa.support.entity.Support;
import it.gov.pagopa.hubpa.support.mock.SupportMock;
import it.gov.pagopa.hubpa.support.repository.SupportRepository;

@ExtendWith(MockitoExtension.class)
class SupportServiceTest {
    @InjectMocks
    SupportService supportService;

    @Mock
    SupportRepository supportRepository;

    @Mock
    private MailService mailService;

    @Test
    void saveTest(){
        ReflectionTestUtils.setField(supportService, "mailSupportFrom", "from");
        ReflectionTestUtils.setField(supportService, "mailSupportTo", "to");
        ReflectionTestUtils.setField(mailService, "mailSmtpHost", "mailSmtpHost");
        ReflectionTestUtils.setField(mailService, "mailSmtpPort", "mailSmtpPort");
        ReflectionTestUtils.setField(mailService, "mailSmtpAuth", "mailSmtpAuth");
        ReflectionTestUtils.setField(mailService, "mailUser", "mailUser");
        ReflectionTestUtils.setField(mailService, "mailPassword", "mailPassword");
        Support support = SupportMock.getMock();
        Support supportCall = SupportMock.getMockCall();
        Boolean response = supportService.save(support);
        assertThat(response).isTrue();
        response = supportService.save(supportCall);
        assertThat(response).isTrue();
    }
}
