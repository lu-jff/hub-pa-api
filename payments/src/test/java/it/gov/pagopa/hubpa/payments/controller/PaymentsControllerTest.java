package it.gov.pagopa.hubpa.payments.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import it.gov.pagopa.hubpa.payments.PaymentsApplication;
import it.gov.pagopa.hubpa.payments.config.DevCorsConfiguration;
import it.gov.pagopa.hubpa.payments.config.MappingsConfiguration;
import it.gov.pagopa.hubpa.payments.entity.Debitor;
import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;
import it.gov.pagopa.hubpa.payments.entity.Transfers;
import it.gov.pagopa.hubpa.payments.mock.DebitorMock;
import it.gov.pagopa.hubpa.payments.mock.DebitorModelMock;
import it.gov.pagopa.hubpa.payments.mock.FilterModelMock;
import it.gov.pagopa.hubpa.payments.mock.FindModelMock;
import it.gov.pagopa.hubpa.payments.mock.FindResponseModelMock;
import it.gov.pagopa.hubpa.payments.mock.PaymentJobMinimalModelMock;
import it.gov.pagopa.hubpa.payments.mock.PaymentsModelMock;
import it.gov.pagopa.hubpa.payments.mock.TributeServiceModelMock;
import it.gov.pagopa.hubpa.payments.mock.UploadCsvModelMock;
import it.gov.pagopa.hubpa.payments.mock.UploadCsvPartialModelMock;
import it.gov.pagopa.hubpa.payments.model.CsvPositionModel;
import it.gov.pagopa.hubpa.payments.model.DebitorModel;
import it.gov.pagopa.hubpa.payments.model.FilterModel;
import it.gov.pagopa.hubpa.payments.model.FindModel;
import it.gov.pagopa.hubpa.payments.model.FindResponseModel;
import it.gov.pagopa.hubpa.payments.model.PaymentJobMinimalModel;
import it.gov.pagopa.hubpa.payments.model.PaymentMinimalModel;
import it.gov.pagopa.hubpa.payments.model.PaymentPositionDetailModel;
import it.gov.pagopa.hubpa.payments.model.PaymentsModel;
import it.gov.pagopa.hubpa.payments.model.UploadCsvModel;
import it.gov.pagopa.hubpa.payments.model.UploadCsvPartialModel;
import it.gov.pagopa.hubpa.payments.model.tribute.TributeServiceModel;
import it.gov.pagopa.hubpa.payments.service.PaymentService;
import springfox.documentation.spring.web.plugins.Docket;

@ExtendWith(MockitoExtension.class)
class PaymentsControllerTest {
    @InjectMocks
    private PaymentsController paymentsController;

    @Mock
    private ModelMapper modelMapperMock;

    @Mock
    private PaymentService paymentService;
    
    @Mock
    private RestTemplate restTemplate;

    @Test
    void createPayments() {
	
	ReflectionTestUtils.setField(paymentsController, "serviceManagementPath", "");
	
	Debitor debitor = DebitorMock.getMock();
	TributeServiceModel tribute=TributeServiceModelMock.validationOKCase1();
	PaymentsModel paymentsModel=PaymentsModelMock.getMock();
	UploadCsvModel uploadCsvModel=UploadCsvModelMock.getMock();
	UploadCsvPartialModel uploadCsvPartialModel=UploadCsvPartialModelMock.getMock();
	
	when(restTemplate.getForObject(Mockito.anyString(),any())).thenReturn(tribute);
	when(modelMapperMock.map(any(DebitorModel.class), any())).thenReturn(debitor);
	when(modelMapperMock.map(any(UploadCsvPartialModel.class), any())).thenReturn(uploadCsvModel);
	when(paymentService.create(any())).thenReturn(PaymentJobMinimalModelMock.getMock());
	when(modelMapperMock.map(any(UploadCsvModel.class), any())).thenReturn(paymentsModel);

	PaymentJobMinimalModel result = paymentsController.createPayments(uploadCsvPartialModel);
	assertThat(result.getJobId()).isEqualTo(1);
    }

    @Test
    void mapperTest() {
	MappingsConfiguration mm = new MappingsConfiguration();
	ModelMapper modelMapper = mm.modelMapper();
	Debitor debitor1=DebitorMock.getMock();
	PaymentPosition paymentPosition=debitor1.getPaymentPosition().get(0);
	DebitorModel modelMock = DebitorModelMock.createDebitor1();
	UploadCsvModel uploadCsvModelMock= UploadCsvModelMock.getMock();

	Debitor debitor = modelMapper.map(modelMock, Debitor.class);
	
	
	assertThat(debitor.getFiscalCode()).isEqualTo("MRDPLL54H17D542L");
	assertThat(debitor.getPaymentPosition().get(0).getPaymentOptions().get(0).getIsConclusive()).isTrue();
	
	
	PaymentsModel paymentsModel = modelMapper.map(uploadCsvModelMock, PaymentsModel.class);	
	assertThat(paymentsModel.getDebitors().get(0).getArea()).isEqualTo("Firenze");
	
	/*PaymentPosition paymentPosition=modelMapper.map(paymentMinimalModel,PaymentPosition.class);
	assertThat(paymentPosition.getDebitor().getSurname()).isEqualTo("ROSSI");*/
	PaymentMinimalModel paymentMinimalModel=modelMapper.map(paymentPosition,PaymentMinimalModel.class);
	assertThat(paymentMinimalModel.getSurname()).isEqualTo("Rossi");
	
	PaymentPositionDetailModel paymentPositionDetailModel=modelMapper.map(paymentPosition,PaymentPositionDetailModel.class);
	assertThat(paymentPositionDetailModel.getFiscalCode()).isEqualTo("MRDPLL54H17D542L");
	CsvPositionModel csvPositionModel=modelMapper.map(paymentPosition,CsvPositionModel.class);
	assertThat(csvPositionModel.getFiscalCode()).isEqualTo("MRDPLL54H17D542L");
	
    }

    @Test
    void applciationTest() {
	PaymentsApplication mm = new PaymentsApplication();
	Docket api = mm.api();
	assertThat(api).isNotNull();
    }

    @Test
    void devCorsTest() {
	DevCorsConfiguration mm = new DevCorsConfiguration();
	WebMvcConfigurer corsConfigurer = mm.corsConfigurer();
	CorsRegistry registry = new CorsRegistry();
	corsConfigurer.addCorsMappings(registry);
	RestTemplate restTemplate = mm.restTemplate();
	assertThat(corsConfigurer).isNotNull();
	assertThat(restTemplate).isNotNull();
    }

    @Test
    void getDTO() {
	Transfers transfer = DebitorMock.createTransfersMock1a();
	assertThat(transfer.getIban()).isNotNull();
	assertThat(transfer.getId()).isNull();
	assertThat(transfer.getOrganizationFiscalCode()).isNotNull();
	assertThat(transfer.getPartialAmount()).isNotNull();
	assertThat(transfer.getPaymentOptions()).isNull();
	assertThat(transfer.getReason()).isNotNull();
	assertThat(transfer.getTaxonomy()).isNotNull();
	
	FindModel findModel=FindModelMock.getMock();
	assertThat(findModel.getFilters()).isNotNull();
	assertThat(findModel.getPage()).isNotNull();
	assertThat(findModel.getSize()).isNotNull();
	assertThat(findModel.getFiscalCode()).isNotNull();
	
	FilterModel filterModel=FilterModelMock.getMock();
	assertThat(filterModel.getDateFrom()).isNotNull();
	assertThat(filterModel.getDateTo()).isNotNull();
	assertThat(filterModel.getStatus()).isNotNull();
	assertThat(filterModel.getTextSearch()).isNotNull();
	
	FindResponseModel findResponseModel=FindResponseModelMock.getMock();
	assertThat(findResponseModel.getCurrentPage()).isNotNull();
	assertThat(findResponseModel.getTotalItems()).isNotNull();
	assertThat(findResponseModel.getTotalPages()).isNotNull();
	assertThat(findResponseModel.getPayments()).isNotNull();
	
    }

}
