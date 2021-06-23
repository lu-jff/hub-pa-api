package it.gov.pagopa.hubpa.payments.config;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import it.gov.pagopa.hubpa.payments.endpoints.validation.SoapMessageDispatcher;
import it.gov.pagopa.hubpa.payments.endpoints.validation.SoapValidatingInterceptor;
import it.gov.pagopa.hubpa.payments.model.partner.ObjectFactory;

@EnableWs
@Configuration
public class WebServicesConfiguration extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext, SoapMessageDispatcher servlet) {
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/partner/*");
    }

    @Bean(name = "partner")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema nodeSchema) {

        Properties soapActions = new Properties();
        soapActions.put("paVerifyPaymentNotice", "paVerifyPaymentNotice");
        soapActions.put("paGetPayment", "paGetPayment");
        soapActions.put("paSendRT", "paSendRT");

        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("PartnerPort");
        wsdl11Definition.setLocationUri("/partner");
        wsdl11Definition.setTargetNamespace("http://pagopa-api.pagopa.gov.it/partner");

        // Required to make the naming schema compatible to xsd definition
        wsdl11Definition.setRequestSuffix("Req");
        wsdl11Definition.setResponseSuffix("Res");

        wsdl11Definition.setSchema(nodeSchema);
        wsdl11Definition.setSoapActions(soapActions);

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

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {

        SoapValidatingInterceptor validatingInterceptor = new SoapValidatingInterceptor();
        validatingInterceptor.setValidateRequest(true);
        validatingInterceptor.setValidateResponse(false);
        validatingInterceptor.setXsdSchema(nodeSchema());
        interceptors.add(validatingInterceptor);
    }
}