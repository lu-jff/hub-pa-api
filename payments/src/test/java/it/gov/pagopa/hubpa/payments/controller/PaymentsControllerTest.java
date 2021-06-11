package it.gov.pagopa.hubpa.payments.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import it.gov.pagopa.hubpa.payments.PaymentsApplication;
import it.gov.pagopa.hubpa.payments.config.DevCorsConfiguration;
import it.gov.pagopa.hubpa.payments.config.MappingsConfiguration;
import it.gov.pagopa.hubpa.payments.entity.Debitor;
import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;
import it.gov.pagopa.hubpa.payments.entity.Transfers;
import it.gov.pagopa.hubpa.payments.mock.CsvPositionModelMock;
import it.gov.pagopa.hubpa.payments.mock.DebitorMock;
import it.gov.pagopa.hubpa.payments.mock.DebitorModelMock;
import it.gov.pagopa.hubpa.payments.mock.FilterModelMock;
import it.gov.pagopa.hubpa.payments.mock.FindModelMock;
import it.gov.pagopa.hubpa.payments.mock.FindResponseModelMock;
import it.gov.pagopa.hubpa.payments.mock.PaymentJobMinimalModelMock;
import it.gov.pagopa.hubpa.payments.mock.PaymentMinimalModelMock;
import it.gov.pagopa.hubpa.payments.mock.PaymentPositionDetailModelMock;
import it.gov.pagopa.hubpa.payments.mock.PaymentsModelMock;
import it.gov.pagopa.hubpa.payments.mock.PublishModelMock;
import it.gov.pagopa.hubpa.payments.mock.TributeServiceModelMock;
import it.gov.pagopa.hubpa.payments.mock.UploadCsvModelMock;
import it.gov.pagopa.hubpa.payments.mock.UploadCsvPartialModelMock;
import it.gov.pagopa.hubpa.payments.model.BooleanResponseModel;
import it.gov.pagopa.hubpa.payments.model.CsvPositionModel;
import it.gov.pagopa.hubpa.payments.model.DebitorModel;
import it.gov.pagopa.hubpa.payments.model.FilterModel;
import it.gov.pagopa.hubpa.payments.model.FindModel;
import it.gov.pagopa.hubpa.payments.model.FindResponseModel;
import it.gov.pagopa.hubpa.payments.model.PaymentJobMinimalModel;
import it.gov.pagopa.hubpa.payments.model.PaymentMinimalModel;
import it.gov.pagopa.hubpa.payments.model.PaymentPositionDetailModel;
import it.gov.pagopa.hubpa.payments.model.PaymentsModel;
import it.gov.pagopa.hubpa.payments.model.PublishModel;
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
	TributeServiceModel tribute = TributeServiceModelMock.validationOKCase1();
	PaymentsModel paymentsModel = PaymentsModelMock.getMock();
	UploadCsvModel uploadCsvModel = UploadCsvModelMock.getMock();
	UploadCsvPartialModel uploadCsvPartialModel = UploadCsvPartialModelMock.getMock();

	when(restTemplate.getForObject(Mockito.anyString(), any())).thenReturn(tribute);
	when(modelMapperMock.map(any(DebitorModel.class), any())).thenReturn(debitor);
	when(modelMapperMock.map(any(UploadCsvPartialModel.class), any())).thenReturn(uploadCsvModel);
	when(paymentService.create(any())).thenReturn(PaymentJobMinimalModelMock.getMock());
	when(modelMapperMock.map(any(UploadCsvModel.class), any())).thenReturn(paymentsModel);

	PaymentJobMinimalModel result = paymentsController.createPayments(uploadCsvPartialModel);
	assertThat(result.getJobId()).isEqualTo(1);

    }

    @Test
    void getResultValidation() {

	ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
	Validator validator = validatorFactory.getValidator();
	UploadCsvPartialModel uploadCsvPartialModel = UploadCsvPartialModelMock.getMock();

	Set<ConstraintViolation<UploadCsvPartialModel>> violations = validator.validate(uploadCsvPartialModel);

	assertThat(violations).isNotEmpty();

    }

    @Test
    void mapperTest() {
	MappingsConfiguration mm = new MappingsConfiguration();
	ModelMapper modelMapper = mm.modelMapper();
	Debitor debitor1 = DebitorMock.getMock();
	PaymentPosition paymentPosition = debitor1.getPaymentPosition().get(0);
	DebitorModel modelMock = DebitorModelMock.createDebitor1();
	UploadCsvModel uploadCsvModelMock = UploadCsvModelMock.getMock();
	UploadCsvModel uploadCsvModelNoRateMock = UploadCsvModelMock.getMockNoRate();

	Debitor debitor = modelMapper.map(modelMock, Debitor.class);

	assertThat(debitor.getFiscalCode()).isEqualTo("MRDPLL54H17D542L");
	assertThat(debitor.getPaymentPosition().get(0).getPaymentOptions().get(0).getIsConclusive()).isTrue();
	
	PaymentPositionDetailModel ss = modelMapper.map(paymentPosition, PaymentPositionDetailModel.class);
	assertThat(ss.getPublishDate()).isNotNull();

	PaymentsModel paymentsModel = modelMapper.map(uploadCsvModelMock, PaymentsModel.class);
	assertThat(paymentsModel.getDebitors().get(0).getArea()).isEqualTo("Firenze");
	
	paymentsModel = modelMapper.map(uploadCsvModelNoRateMock, PaymentsModel.class);
	assertThat(paymentsModel.getDebitors().get(0).getArea()).isEqualTo("Firenze");

	PaymentMinimalModel paymentMinimalModel = modelMapper.map(paymentPosition, PaymentMinimalModel.class);
	assertThat(paymentMinimalModel.getSurname()).isEqualTo("Rossi");
	
	PaymentPositionDetailModel paymentPositionDetailModel = modelMapper.map(paymentPosition,
		PaymentPositionDetailModel.class);
	assertThat(paymentPositionDetailModel.getFiscalCode()).isEqualTo("MRDPLL54H17D542L");
	CsvPositionModel csvPositionModel = modelMapper.map(paymentPosition, CsvPositionModel.class);
	assertThat(csvPositionModel.getFiscalCode()).isEqualTo("MRDPLL54H17D542L");
	
	paymentPosition.getPaymentOptions().get(0).setTransfers(null);
	csvPositionModel = modelMapper.map(paymentPosition, CsvPositionModel.class);
	assertThat(csvPositionModel.getFiscalCode()).isEqualTo("MRDPLL54H17D542L");

	paymentPosition.getPaymentOptions().get(0).setTransfers(new ArrayList<>());
	csvPositionModel = modelMapper.map(paymentPosition, CsvPositionModel.class);
	assertThat(csvPositionModel.getFiscalCode()).isEqualTo("MRDPLL54H17D542L");

	paymentPosition.setPaymentOptions(new ArrayList<>());
	csvPositionModel = modelMapper.map(paymentPosition, CsvPositionModel.class);
	assertThat(csvPositionModel.getFiscalCode()).isEqualTo("MRDPLL54H17D542L");

	paymentPosition.setPaymentOptions(null);
	csvPositionModel = modelMapper.map(paymentPosition, CsvPositionModel.class);
	assertThat(csvPositionModel.getFiscalCode()).isEqualTo("MRDPLL54H17D542L");

	
	paymentPosition.setInformation("POSSIBLE_DUPLICATE");
	paymentPosition.setInsertDate(null);
	paymentMinimalModel = modelMapper.map(paymentPosition, PaymentMinimalModel.class);
	assertThat(paymentMinimalModel.getSurname()).isEqualTo("Rossi");
	
	
	

    }

    @Test
    void getPayments() {
	FindModel findModelMock = FindModelMock.getMock();
	PaymentMinimalModel paymentMinimalModel = PaymentMinimalModelMock.getMock();
	List<PaymentPosition> listPay = new ArrayList<>();
	PaymentPosition paymentPosition = DebitorMock.createPaymentPositionMock();
	listPay.add(paymentPosition);
	Page<PaymentPosition> pp = new PageImpl<>(listPay);

	when(paymentService.getPaymentsByFilters(Mockito.anyString(), Mockito.any(), Mockito.any())).thenReturn(pp);
	when(modelMapperMock.map(any(PaymentPosition.class), any())).thenReturn(paymentMinimalModel);

	FindResponseModel aa = paymentsController.getPayments(findModelMock);
	assertThat(aa.getPayments()).isNotNull();
    }

    @Test
    void getPaymentsByPaymentPositionId() {
	PaymentPosition paymentPosition = DebitorMock.createPaymentPositionMock();
	PaymentPositionDetailModel paymentPositionDetailModel = PaymentPositionDetailModelMock.getMock();
	when(paymentService.getPaymentByPaymentPositionId(Mockito.anyLong())).thenReturn(paymentPosition);
	when(modelMapperMock.map(any(PaymentPosition.class), any())).thenReturn(paymentPositionDetailModel);
	PaymentPositionDetailModel pay = paymentsController.getPaymentsByPaymentPositionId(1l);
	assertThat(pay.getFiscalCode()).isNotNull();
    }

    @Test
    void exportCsv() throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
	HttpServletResponse httpServletResponse = new MockHttpServletResponse();
	List<PaymentPosition> paymentPositionList = new ArrayList<>();
	paymentPositionList.add(DebitorMock.createPaymentPositionMock());
	CsvPositionModel csvPositionModel = CsvPositionModelMock.getMock();
	when(paymentService.getPaymentsByJobId(Mockito.anyLong())).thenReturn(paymentPositionList);
	when(modelMapperMock.map(any(PaymentPosition.class), any())).thenReturn(csvPositionModel);

	paymentsController.exportCsv(1l,"test.csv", httpServletResponse);
	assertThatNoException();
    }

    @Test
    void publishPayments() {
	
	List<PaymentPosition> paymentPositionList = new ArrayList<>();
	paymentPositionList.add(DebitorMock.createPaymentPositionMock());
	PublishModel publishModel = PublishModelMock.getMock();
	when(paymentService.updatePublishPayment(Mockito.any(),Mockito.any())).thenReturn(Boolean.TRUE);

	BooleanResponseModel aa = paymentsController.publishPayments(publishModel);
	assertThat(aa.getResult()).isEqualTo(Boolean.TRUE);
    }
    
    @Test
    void delete() {
	
	when(paymentService.deletePayment(Mockito.any(),Mockito.any())).thenReturn(Boolean.TRUE);

	BooleanResponseModel aa = paymentsController.delete(1l);
	assertThat(aa.getResult()).isEqualTo(Boolean.TRUE);
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
	RestTemplate restTemplate = mm.restTemplate();
	assertThat(restTemplate).isNotNull();
    }

    @Test
    void getDTO1() {
	Transfers transfer = DebitorMock.createTransfersMock1a();
	assertThat(transfer.getIban()).isNotNull();
	assertThat(transfer.getId()).isNull();
	assertThat(transfer.getOrganizationFiscalCode()).isNotNull();
	assertThat(transfer.getPartialAmount()).isNotNull();
	assertThat(transfer.getPaymentOptions()).isNull();
	assertThat(transfer.getReason()).isNotNull();
	assertThat(transfer.getTaxonomy()).isNotNull();

	FindModel findModel = FindModelMock.getMock();
	assertThat(findModel.getFilters()).isNotNull();
	assertThat(findModel.getPage()).isNotNull();
	assertThat(findModel.getSize()).isNotNull();
	assertThat(findModel.getFiscalCode()).isNotNull();

	FilterModel filterModel = FilterModelMock.getMock();
	assertThat(filterModel.getDateFrom()).isNotNull();
	assertThat(filterModel.getDateTo()).isNotNull();
	assertThat(filterModel.getStatus()).isNotNull();
	assertThat(filterModel.getTextSearch()).isNotNull();

	FindResponseModel findResponseModel = FindResponseModelMock.getMock();
	assertThat(findResponseModel.getCurrentPage()).isNotNull();
	assertThat(findResponseModel.getTotalItems()).isNotNull();
	assertThat(findResponseModel.getTotalPages()).isNotNull();
	assertThat(findResponseModel.getPayments()).isNotNull();

    }

    @Test
    void getDTO2() {

	CsvPositionModel csvPositionModel = CsvPositionModelMock.getMock();
	assertThat(csvPositionModel.getName()).isNotNull();
	assertThat(csvPositionModel.getAddress()).isNotNull();
	assertThat(csvPositionModel.getAmount()).isNotNull();
	assertThat(csvPositionModel.getArea()).isNotNull();
	assertThat(csvPositionModel.getCap()).isNotNull();
	assertThat(csvPositionModel.getCountry()).isNotNull();
	assertThat(csvPositionModel.getEmail()).isNotNull();
	assertThat(csvPositionModel.getFiscalCode()).isNotNull();
	assertThat(csvPositionModel.getIdTenant()).isNotNull();
	assertThat(csvPositionModel.getInformation()).isNotNull();
	assertThat(csvPositionModel.getName()).isNotNull();
	assertThat(csvPositionModel.getNumber()).isNotNull();
	assertThat(csvPositionModel.getPhone()).isNotNull();
	assertThat(csvPositionModel.getProvince()).isNotNull();
	assertThat(csvPositionModel.getSurname()).isNotNull();
	assertThat(csvPositionModel.getType()).isNotNull();

    }
    @Test
    void getDTO3() {

	PaymentMinimalModel csvPositionModel = PaymentMinimalModelMock.getMock();
	assertThat(csvPositionModel.getId()).isNotNull();
	assertThat(csvPositionModel.getFiscalCode()).isNotNull();
	assertThat(csvPositionModel.getName()).isNotNull();
	assertThat(csvPositionModel.getSurname()).isNotNull();
	assertThat(csvPositionModel.getDate()).isNotNull();
	assertThat(csvPositionModel.getStatus()).isNotNull();
	assertThat(csvPositionModel.getIsDuplicated()).isNotNull();


    }
    @Test
    void getDTO4() {

	PaymentPositionDetailModel payPosDet = PaymentPositionDetailModelMock.getMock();
	assertThat(payPosDet.getAddressLine1()).isNotNull();
	assertThat(payPosDet.getAddressLine2()).isNotNull();
	assertThat(payPosDet.getDescription()).isNotNull();
	assertThat(payPosDet.getFiscalCode()).isNotNull();
	assertThat(payPosDet.getNominative()).isNotNull();
	assertThat(payPosDet.getPublishDate()).isNotNull();
	assertThat(payPosDet.getStatus()).isNotNull();
	assertThat(payPosDet.getInstallments()).isNotNull();

    }
}
