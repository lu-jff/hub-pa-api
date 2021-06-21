package it.gov.pagopa.hubpa.qworker.converter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.qpid.jms.message.JmsBytesMessage;
import org.apache.qpid.jms.provider.amqp.message.AmqpJmsBytesMessageFacade;
import org.apache.qpid.proton.amqp.Symbol;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class CustomMessageConverterTest {

  @InjectMocks
  private CustomMessageConverter customMessageConverter;

  @Mock
  MappingJackson2MessageConverter converter;

  @Test
  void shouldConvertTheMessage() throws JMSException, IOException {
    ObjectMapper mapper = new ObjectMapper();

    Session mockSession = mock(Session.class);

    AmqpJmsBytesMessageFacade facade = mock(AmqpJmsBytesMessageFacade.class);
    JmsBytesMessage msg = mock(JmsBytesMessage.class);
    when(msg.getFacade()).thenReturn(facade);
    doNothing().when(msg).writeBytes(any());

    when(mockSession.createBytesMessage()).thenReturn(msg);
    facade.setContentType(Symbol.valueOf("application/json"));
    customMessageConverter.mapToBytesMessage("fake message", mockSession, mapper.writer());

    verify(msg, times(1)).getFacade();

  }

}
