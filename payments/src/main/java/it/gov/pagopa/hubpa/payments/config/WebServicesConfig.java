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

@EnableWs
@Configuration
public class WebServicesConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/paForNode_Service/*");
    }

    @Bean(name = "node")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema nodeSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("NodePort");
        wsdl11Definition.setLocationUri("/paForNode_Service");
        wsdl11Definition.setTargetNamespace("http://pagopa-api.pagopa.gov.it/pa/paForNode.xsd");
        wsdl11Definition.setSchema(nodeSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema nodeSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/paForNode.xsd"));
    }
}