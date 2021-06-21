package it.gov.pagopa.hubpa.qworker.converter;

import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.qpid.jms.message.JmsBytesMessage;
import org.apache.qpid.jms.provider.amqp.message.AmqpJmsMessageFacade;
import org.apache.qpid.proton.amqp.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.stereotype.Component;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Session;
import java.io.IOException;

@Component
public class CustomMessageConverter extends MappingJackson2MessageConverter {

    private static final String TYPE_ID_PROPERTY = "_type";
    private static final Symbol CONTENT_TYPE = Symbol.valueOf("application/json");
    private final Logger logger = LoggerFactory.getLogger(CustomMessageConverter.class);

    public CustomMessageConverter() {
        this.setTargetType(MessageType.TEXT);
        this.setTypeIdPropertyName(TYPE_ID_PROPERTY);
    }

    @Override
    protected BytesMessage mapToBytesMessage(Object object, Session session, ObjectWriter objectWriter)
            throws JMSException, IOException {

        final BytesMessage bytesMessage = super.mapToBytesMessage(object, session, objectWriter);
        JmsBytesMessage jmsBytesMessage = (JmsBytesMessage) bytesMessage;
        AmqpJmsMessageFacade facade = (AmqpJmsMessageFacade) jmsBytesMessage.getFacade();
        facade.setContentType(CONTENT_TYPE);

        return jmsBytesMessage;
    }
}