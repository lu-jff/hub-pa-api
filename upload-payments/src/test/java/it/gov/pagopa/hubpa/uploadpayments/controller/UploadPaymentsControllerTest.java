package it.gov.pagopa.hubpa.uploadpayments.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import it.gov.pagopa.hubpa.uploadpayments.UploadPaymentsApplication;
import it.gov.pagopa.hubpa.uploadpayments.config.DevCorsConfiguration;
import it.gov.pagopa.hubpa.uploadpayments.config.MappingsConfiguration;
import it.gov.pagopa.hubpa.uploadpayments.entity.PaymentJob;
import it.gov.pagopa.hubpa.uploadpayments.mock.PaymentJobMock;
import it.gov.pagopa.hubpa.uploadpayments.mock.PaymentJobModelMock;
import it.gov.pagopa.hubpa.uploadpayments.mock.UploadCsvModelMock;
import it.gov.pagopa.hubpa.uploadpayments.model.BooleanResponseModel;
import it.gov.pagopa.hubpa.uploadpayments.model.PaymentJobModel;
import it.gov.pagopa.hubpa.uploadpayments.model.PaymentsModel;
import it.gov.pagopa.hubpa.uploadpayments.model.UploadCsvModel;
import it.gov.pagopa.hubpa.uploadpayments.service.PaymentJobService;
import springfox.documentation.spring.web.plugins.Docket;

@ExtendWith(MockitoExtension.class)
class UploadPaymentsControllerTest {
    @InjectMocks
    private UploadPaymentsController uploadPaymentsController;

    @Mock
    private ModelMapper modelMapperMock;

    @Mock
    private PaymentJobService paymentJobService;

    @Test
    void isJobStatusChanged() {

	BooleanResponseModel esito = null;
	when(paymentJobService.countByIdsAndStatusNot(Mockito.anyList(), Mockito.anyInt())).thenReturn(1l);
	esito = uploadPaymentsController.isJobStatusChanged(new ArrayList<Long>());
	assertThat(esito.getResult()).isTrue();

	when(paymentJobService.countByIdsAndStatusNot(Mockito.anyList(), Mockito.anyInt())).thenReturn(0l);
	esito = uploadPaymentsController.isJobStatusChanged(new ArrayList<Long>());
	assertThat(esito.getResult()).isFalse();

    }
    @Test
    void isPaymentJobAvailable() {

	BooleanResponseModel esito = null;
	when(paymentJobService.countByFiscalCodeAndStatusNot(Mockito.anyString(), Mockito.anyInt())).thenReturn(1l);
	esito = uploadPaymentsController.isPaymentJobAvailable("12345678901");
	assertThat(esito.getResult()).isTrue();

	when(paymentJobService.countByFiscalCodeAndStatusNot(Mockito.anyString(), Mockito.anyInt())).thenReturn(0l);
	esito = uploadPaymentsController.isPaymentJobAvailable("12345678901");
	assertThat(esito.getResult()).isFalse();

    }

    @Test
    void getAllJob() {
	PaymentJob paymentJob = PaymentJobMock.getMock();
	PaymentJobModel paymentJobModel = PaymentJobModelMock.getMock();

	when(modelMapperMock.map(any(PaymentJob.class), any())).thenReturn(paymentJobModel);
	List<PaymentJob> listPaymentJob = new ArrayList<>();
	listPaymentJob.add(paymentJob);
	listPaymentJob.add(paymentJob);
	when(paymentJobService.getAll(Mockito.anyString())).thenReturn(listPaymentJob);
	List<PaymentJobModel> result = uploadPaymentsController.getAllJob("12345678901");
	assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void createPaymentJob() {
	PaymentJob paymentJob = PaymentJobMock.getMock();
	PaymentJobModel paymentJobModel = PaymentJobModelMock.getMock();
	when(modelMapperMock.map(any(PaymentJobModel.class), any())).thenReturn(paymentJob);

	List<PaymentJob> listPaymentJob = new ArrayList<>();
	listPaymentJob.add(PaymentJobMock.getMock());
	listPaymentJob.add(PaymentJobMock.getMock());
	when(paymentJobService.create(any(PaymentJob.class))).thenReturn(Boolean.TRUE);

	BooleanResponseModel result = uploadPaymentsController.createJobRecord(paymentJobModel);
	assertThat(result.getResult()).isTrue();
    }

    @Test
    void mapperTest() {
	MappingsConfiguration mm = new MappingsConfiguration();
	ModelMapper modelMapper = mm.modelMapper();
	PaymentJob serviceMock = PaymentJobMock.getMock();
	PaymentJobModel modelMock = PaymentJobModelMock.getMock();
	PaymentJobModel paymentJob = modelMapper.map(modelMock, PaymentJobModel.class);
	PaymentJobModel paymentJobModel = modelMapper.map(serviceMock, PaymentJobModel.class);
	UploadCsvModel uploadCsvModelMock= UploadCsvModelMock.getMock();
	PaymentsModel paymentsModel = modelMapper.map(uploadCsvModelMock, PaymentsModel.class);
	PaymentJob paymentJob2 = modelMapper.map(uploadCsvModelMock, PaymentJob.class);
	
	
	assertThat(paymentJobModel.getFileName()).isEqualTo("testFileCsv20210409.csv");
	assertThat(paymentJob.getFileName()).isEqualTo("testFileCsv20210409.csv");
	assertThat(paymentsModel.getDebitors().get(0).getArea()).isEqualTo("Firenze");
	assertThat(paymentJob2.getFileName()).isEqualTo("fileProva.csv");

    }

    @Test
    void applciationTest() {
	UploadPaymentsApplication mm = new UploadPaymentsApplication();
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
    void testGetModel() {
	PaymentJobModel mock = PaymentJobModelMock.getMock();
	assertThat(mock.getFiscalCode()).isNotNull();
	assertThat(mock.getFileName()).isNotNull();
	assertThat(mock.getInsertDate()).isNotNull();
	assertThat(mock.getElaborationDate()).isNotNull();
	assertThat(mock.getNRecordAdded()).isNotNull();
	assertThat(mock.getNRecordFound()).isNotNull();
	assertThat(mock.getStatus()).isNotNull();
    }

    @Test
    void testGetEntity() {
	PaymentJob mock = PaymentJobMock.getMock();
	assertThat(mock.getJobId()).isNotNull();
	assertThat(mock.getFiscalCode()).isNotNull();
	assertThat(mock.getFileName()).isNotNull();
	assertThat(mock.getInsertDate()).isNotNull();
	assertThat(mock.getElaborationDate()).isNotNull();
	assertThat(mock.getNRecordAdded()).isNotNull();
	assertThat(mock.getNRecordFound()).isNotNull();
	assertThat(mock.getStatus()).isNotNull();
    }

}
