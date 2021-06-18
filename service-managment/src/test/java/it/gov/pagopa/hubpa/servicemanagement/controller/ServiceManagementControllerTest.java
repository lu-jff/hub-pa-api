package it.gov.pagopa.hubpa.servicemanagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import javax.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.client.RestTemplate;

import it.gov.pagopa.hubpa.servicemanagement.ServiceManagementApplication;
import it.gov.pagopa.hubpa.servicemanagement.config.DevCorsConfiguration;
import it.gov.pagopa.hubpa.servicemanagement.config.MappingsConfiguration;
import it.gov.pagopa.hubpa.servicemanagement.entity.Service;
import it.gov.pagopa.hubpa.servicemanagement.mock.ServiceMock;
import it.gov.pagopa.hubpa.servicemanagement.mock.TributeServiceModelMock;
import it.gov.pagopa.hubpa.servicemanagement.model.ServiceConfiguratedModel;
import it.gov.pagopa.hubpa.servicemanagement.model.TributeServiceModel;
import it.gov.pagopa.hubpa.servicemanagement.service.ServiceManagementService;
import it.gov.pagopa.hubpa.servicemanagement.validator.ServiceManagementValidator;
import springfox.documentation.spring.web.plugins.Docket;

@ExtendWith(MockitoExtension.class)
class ServiceManagementControllerTest {

    @InjectMocks
    private ServiceManagementController serviceManagementController;

    @Mock
    private ModelMapper modelMapperMock;

    @Mock
    private ServiceManagementService serviceManagementService;

    @InjectMocks
    private ServiceManagementValidator validator;

    @MockBean
    private BindingResult bindingResult;

    @Test
    void isServiceConfiguratedTest() throws ServletException {
	when(serviceManagementService.isServiceConfigurated(any(String.class))).thenReturn(Boolean.TRUE);
	ServiceConfiguratedModel serviceConfiguratedModel = serviceManagementController.isServiceConfigurated("12345678901");
	assertThat(serviceConfiguratedModel.getResult()).isTrue();
    }

    @Test
    void getServiceTest() throws ServletException {

	Service serviceMock = ServiceMock.getMock();
	TributeServiceModel modelMock = TributeServiceModelMock.validationOKCase1();
	when(serviceManagementService.getService(any(String.class))).thenReturn(serviceMock);
	when(modelMapperMock.map(any(Service.class), any())).thenReturn(modelMock);

	TributeServiceModel tributeServiceModel = serviceManagementController.getService("12345678901");
	when(serviceManagementService.getService(any(String.class))).thenReturn(null);
	TributeServiceModel tributeServiceModel2 = serviceManagementController.getService("12345678901");
	assertThat(tributeServiceModel.getDenomination()).isEqualTo("TariTefa2021");
	assertThat(tributeServiceModel2).isNull();
	
    }

    @Test
    void saveServiceTest() throws ServletException {
	TributeServiceModel modelMock = TributeServiceModelMock.validationOKCase1();
	Service serviceMock = ServiceMock.getMock();
	when(serviceManagementService.saveService(any(Service.class))).thenReturn(Boolean.TRUE);
	when(modelMapperMock.map(any(TributeServiceModel.class), any())).thenReturn(serviceMock);
	ServiceConfiguratedModel serviceConfiguratedModel = serviceManagementController.saveService(modelMock);
	assertThat(serviceConfiguratedModel.getResult()).isTrue();
    }

    @Test
 	void mapperTest() throws ServletException {
		MappingsConfiguration mm = new MappingsConfiguration();
		ModelMapper modelMapper = mm.modelMapper();
		Service serviceMock = ServiceMock.getMock();
		TributeServiceModel modelMock = TributeServiceModelMock.validationOKCase1();
		TributeServiceModel tributeServiceModel = modelMapper.map(serviceMock, TributeServiceModel.class);
		Service service = modelMapper.map(modelMock, Service.class);
		assertThat(service.getDenomination()).isEqualTo("TariTefa2021");
		assertThat(tributeServiceModel.getDenomination()).isEqualTo("TariTefa2021");
		assertThat(tributeServiceModel.getPostalIbanPrimary())
				.isEqualTo(serviceMock.getPaymentOptionTemplate().get(0).getTransferTemplate().get(0).getPostalIban());
		assertThat(tributeServiceModel.getPostalIbanSecondary())
				.isEqualTo(serviceMock.getPaymentOptionTemplate().get(0).getTransferTemplate().get(1).getPostalIban());		
		assertThat(tributeServiceModel.getPostalIbanHolderPrimary())
				.isEqualTo(serviceMock.getPaymentOptionTemplate().get(0).getTransferTemplate().get(0).getPostalIbanHolder());
		assertThat(tributeServiceModel.getPostalIbanHolderSecondary())
				.isEqualTo(serviceMock.getPaymentOptionTemplate().get(0).getTransferTemplate().get(1).getPostalIbanHolder());		
		assertThat(tributeServiceModel.getPostalAuthCodePrimary())
				.isEqualTo(serviceMock.getPaymentOptionTemplate().get(0).getTransferTemplate().get(0).getPostalAuthCode());
		assertThat(tributeServiceModel.getPostalAuthCodeSecondary())
				.isEqualTo(serviceMock.getPaymentOptionTemplate().get(0).getTransferTemplate().get(1).getPostalAuthCode());		
	}

    @Test
    void validationOK() throws ServletException {
	ServiceManagementValidator serviceManagementValidator = new ServiceManagementValidator();
	ReflectionTestUtils.setField(serviceManagementValidator, "checkDateEnable", true);
	ReflectionTestUtils.setField(serviceManagementValidator, "dateMinTefaStr", "2021-07-01");
	TributeServiceModel modelMock = TributeServiceModelMock.validationOKCase1();

	DataBinder binder = new DataBinder(modelMock);
	binder.setValidator(serviceManagementValidator);

	// validate the target object
	binder.validate();

	// get BindingResult that includes any validation errors
	BindingResult results = binder.getBindingResult();
	assertThat(results.hasErrors()).isFalse();

	results = getResultValidation(TributeServiceModelMock.validationOKCase22());
	assertThat(results.hasErrors()).isFalse();

	results = getResultValidation(TributeServiceModelMock.validationOKCase23());
	assertThat(results.hasErrors()).isFalse();

    }

    @Test
    void validationKO() throws ServletException {
	BindingResult results;

	results = getResultValidation(TributeServiceModelMock.validationKOCase1());
	assertThat(results.hasErrors()).isTrue();
	results = getResultValidation(TributeServiceModelMock.validationKOCase2());
	assertThat(results.hasErrors()).isTrue();
	results = getResultValidation(TributeServiceModelMock.validationKOCase3());
	assertThat(results.hasErrors()).isTrue();
	results = getResultValidation(TributeServiceModelMock.validationKOCase4());
	assertThat(results.hasErrors()).isTrue();
	results = getResultValidation(TributeServiceModelMock.validationKOCase5());
	assertThat(results.hasErrors()).isTrue();
	results = getResultValidation(TributeServiceModelMock.validationKOCase6());
	assertThat(results.hasErrors()).isTrue();
	results = getResultValidation(TributeServiceModelMock.validationKOCase7());
	assertThat(results.hasErrors()).isTrue();
	results = getResultValidation(TributeServiceModelMock.validationKOCase8());
	assertThat(results.hasErrors()).isTrue();

	results = getResultValidation(TributeServiceModelMock.validationOKCase2());
	assertThat(results.hasErrors()).isFalse();
	results = getResultValidation(TributeServiceModelMock.validationOKCase3());
	assertThat(results.hasErrors()).isFalse();

    }

	@Test
	void validationTributeWithInvalidIBan() throws ServletException {
		BindingResult results;

		results = getResultValidation(TributeServiceModelMock.validationKOCase9());
		assertThat(results.hasErrors()).isTrue();

		results = getResultValidation(TributeServiceModelMock.validationKOCase10());
		assertThat(results.hasErrors()).isTrue();

		results = getResultValidation(TributeServiceModelMock.validationKOCase11());
		assertThat(results.hasErrors()).isTrue();

		results = getResultValidation(TributeServiceModelMock.validationKOCase12());
		assertThat(results.hasErrors()).isTrue();

		results = getResultValidation(TributeServiceModelMock.validationKOCase13());
		assertThat(results.hasErrors()).isTrue();

		results = getResultValidation(TributeServiceModelMock.validationKOCase14());
		assertThat(results.hasErrors()).isTrue();

		results = getResultValidation(TributeServiceModelMock.validationKOCase15());
		assertThat(results.hasErrors()).isTrue();

		results = getResultValidation(TributeServiceModelMock.validationKOCase16());
		assertThat(results.hasErrors()).isTrue();

		results = getResultValidation(TributeServiceModelMock.validationKOCase17());
		assertThat(results.hasErrors()).isTrue();

		results = getResultValidation(TributeServiceModelMock.validationKOCase18());
		assertThat(results.hasErrors()).isTrue();

		results = getResultValidation(TributeServiceModelMock.validationKOCase19());
		assertThat(results.hasErrors()).isTrue();

		results = getResultValidation(TributeServiceModelMock.validationKOCase20());
		assertThat(results.hasErrors()).isTrue();

		results = getResultValidation(TributeServiceModelMock.validationKOCase21());
		assertThat(results.hasErrors()).isTrue();

	}

    private BindingResult getResultValidation(TributeServiceModel modelMock) {
	ServiceManagementValidator serviceManagementValidator = new ServiceManagementValidator();
	ReflectionTestUtils.setField(serviceManagementValidator, "checkDateEnable", true);
	ReflectionTestUtils.setField(serviceManagementValidator, "dateMinTefaStr", "2021-07-01");

	DataBinder binder = new DataBinder(modelMock);
	binder.setValidator(serviceManagementValidator);

	// validate the target object
	binder.validate();

	// get BindingResult that includes any validation errors
	return binder.getBindingResult();
    }
    @Test
    void applciationTest() {
	ServiceManagementApplication mm = new ServiceManagementApplication();
	Docket api = mm.api();
	assertThat(api).isNotNull();
    }

    @Test
    void devCorsTest() {
	DevCorsConfiguration mm = new DevCorsConfiguration();
	RestTemplate restTemplate = mm.restTemplate();
	assertThat(restTemplate).isNotNull();
    }

}
