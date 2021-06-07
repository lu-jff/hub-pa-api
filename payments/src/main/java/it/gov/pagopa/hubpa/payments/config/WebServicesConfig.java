package it.gov.pagopa.hubpa.payments.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import it.gov.pagopa.hubpa.payments.model.partner.ObjectFactory;

@EnableWs
@Configuration
public class WebServicesConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/partner/*");
    }

    @Bean(name = "partner")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema nodeSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("paForNode_PortType");
        wsdl11Definition.setLocationUri("/partner");
        wsdl11Definition.setTargetNamespace("http://pagopa-api.pagopa.gov.it/pa/paForNode.xsd");
        wsdl11Definition.setSchema(nodeSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema nodeSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/paForNode.xsd"));
    }

    @Bean
    public ObjectFactory factory() {
        return new ObjectFactory();
    }
}