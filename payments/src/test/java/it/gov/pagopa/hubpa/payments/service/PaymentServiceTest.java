package it.gov.pagopa.hubpa.payments.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipOutputStream;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;

import it.gov.pagopa.hubpa.payments.entity.Debitor;
import it.gov.pagopa.hubpa.payments.entity.IncrementalIuvNumber;
import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;
import it.gov.pagopa.hubpa.payments.iuvgenerator.IuvCodeBusiness;
import it.gov.pagopa.hubpa.payments.iuvgenerator.exception.ValidationException;
import it.gov.pagopa.hubpa.payments.mock.DebitorMock;
import it.gov.pagopa.hubpa.payments.mock.EnteCreditoreMinimalDtoMock;
import it.gov.pagopa.hubpa.payments.mock.FilterModelMock;
import it.gov.pagopa.hubpa.payments.mock.IncrementalIuvNumberMock;
import it.gov.pagopa.hubpa.payments.mock.PaDtoMock;
import it.gov.pagopa.hubpa.payments.model.FilterModel;
import it.gov.pagopa.hubpa.payments.model.PaymentJobMinimalModel;
import it.gov.pagopa.hubpa.payments.model.ente.EnteCreditoreMinimalDto;
import it.gov.pagopa.hubpa.payments.model.ente.PaDto;
import it.gov.pagopa.hubpa.payments.repository.DebitorRepository;
import it.gov.pagopa.hubpa.payments.repository.IncrementalIuvNumberRepository;
import it.gov.pagopa.hubpa.payments.repository.PaymentPositionRepository;
import it.gov.pagopa.hubpa.payments.repository.specification.PaymentPositionWithDateFrom;
import it.gov.pagopa.hubpa.payments.repository.specification.PaymentPositionWithDateTo;
import it.gov.pagopa.hubpa.payments.repository.specification.PaymentPositionWithFiscalCode;
import it.gov.pagopa.hubpa.payments.repository.specification.PaymentPositionWithStatus;
import it.gov.pagopa.hubpa.payments.repository.specification.PaymentPositionWithTextSearch;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    DebitorRepository debitorRepository;

    @Mock
    PaymentPositionRepository paymentPositionRepository;

    @Mock
    IncrementalIuvNumberRepository incrementalIuvNumberRepository;
    @Mock
    CriteriaBuilder criteriaBuilderMock;
    @Mock
    CriteriaQuery criteriaQueryMock;
    @Mock
    Root<PaymentPosition> personRootMock;
    @Mock
    Join<Object, Object> joinMock;

    @Test
    void create() throws ServletException {
	ReflectionTestUtils.setField(paymentService, "auxDigit", 3);
	ReflectionTestUtils.setField(paymentService, "segregationCode", 82);
	Debitor debitor = DebitorMock.getMock();
	Debitor debitor2 = DebitorMock.getMock();
	Debitor debitor3 = DebitorMock.getMock();
	IncrementalIuvNumber incrementalIuvNumberMock = IncrementalIuvNumberMock.getMock();
	List<Debitor> debitorList = new ArrayList<>();
	debitorList.add(debitor);
	debitorList.add(debitor2);
	PaymentPosition paymentPositionMock = DebitorMock.createPaymentPositionMock();
	paymentPositionMock.setId(1l);

	when(debitorRepository.findByFiscalCode(any(String.class))).thenReturn(debitor3);
	when(incrementalIuvNumberRepository.findByIdDominioPaAndAnno(Mockito.anyString(), Mockito.anyInt()))
		.thenReturn(incrementalIuvNumberMock);
	when(debitorRepository.saveAndFlush(any(Debitor.class))).thenReturn(debitor);
	when(debitorRepository.countByFiscalCodeAndPaymentPositionOrganizationFiscalCodeAndPaymentPositionAmount(
		Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenReturn(0l);

	when(paymentPositionRepository.saveAndFlush(any(PaymentPosition.class))).thenReturn(paymentPositionMock);
	PaymentJobMinimalModel result = paymentService.create(debitorList);
	assertThat(result.getNRecordFound()).isEqualTo(2);

	when(incrementalIuvNumberRepository.findByIdDominioPaAndAnno(Mockito.anyString(), Mockito.anyInt()))
		.thenReturn(null);
	when(debitorRepository.countByFiscalCodeAndPaymentPositionOrganizationFiscalCodeAndPaymentPositionAmount(
		Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenReturn(1l);
	when(debitorRepository.findByFiscalCode(any(String.class))).thenReturn(null);
	result = paymentService.create(debitorList);
	assertThat(result.getNRecordFound()).isEqualTo(2);

	when(paymentPositionRepository.saveAndFlush(any(PaymentPosition.class))).thenThrow(new NullPointerException());
	debitorList.get(0).setPaymentPosition(new ArrayList<>());
	debitorList.get(1).getPaymentPosition().get(0).setPaymentOptions(null);
	result = paymentService.create(debitorList);
	assertThat(result.getNRecordFound()).isEqualTo(1);
    }

    @Test
    void updatePublishPayment() throws ServletException {

	PaymentPosition paymentPositionMock0 = DebitorMock.createPaymentPositionMock();
	PaymentPosition paymentPositionMock = DebitorMock.createPaymentPositionMock();
	paymentPositionMock.setId(1l);

	when(paymentPositionRepository.findById(any(Long.class))).thenReturn(Optional.of(paymentPositionMock0));
	when(paymentPositionRepository.saveAndFlush(any(PaymentPosition.class))).thenReturn(paymentPositionMock);

	Boolean result = paymentService.updatePublishPayment(1l, LocalDate.now());
	assertThat(result).isTrue();

	paymentPositionMock0.setStatus(2);
	when(paymentPositionRepository.findById(any(Long.class))).thenReturn(Optional.of(paymentPositionMock0));
	result = paymentService.updatePublishPayment(1l, LocalDate.now());
	assertThat(result).isFalse();

	when(paymentPositionRepository.findById(any(Long.class))).thenReturn(Optional.empty());
	result = paymentService.updatePublishPayment(1l, LocalDate.now());
	assertThat(result).isFalse();

    }

    @Test
    void getPaymentsByFilters() {
	Pageable pageable = PageRequest.of(0, 8);
	FilterModel filterModel = FilterModelMock.getMock();
	PaymentPosition paymentPosition = DebitorMock.createPaymentPositionMock();
	List<PaymentPosition> listPay = new ArrayList<>();
	listPay.add(paymentPosition);
	Page<PaymentPosition> page = new PageImpl<>(listPay);

	when(personRootMock.join(any(String.class))).thenReturn(joinMock);

	Specification<PaymentPosition> spec2 = Specification
		.where(new PaymentPositionWithDateFrom(filterModel.getDateFrom().atStartOfDay()))
		.and(new PaymentPositionWithDateTo(filterModel.getDateTo().atTime(23, 59, 59, 999999999)))
		.and(new PaymentPositionWithFiscalCode("1212"))
		.and(new PaymentPositionWithStatus(filterModel.getStatus()))
		.and(new PaymentPositionWithTextSearch(filterModel.getTextSearch()));

	when(paymentPositionRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
	Page<PaymentPosition> result = paymentService.getPaymentsByFilters("ABC123", filterModel, pageable);
	assertThat(result.getSize()).isEqualTo(1);

	filterModel = FilterModelMock.getMock2();
	Specification<PaymentPosition> spec = Specification.where(new PaymentPositionWithDateFrom(null))
		.and(new PaymentPositionWithDateTo(null)).and(new PaymentPositionWithFiscalCode(null))
		.and(new PaymentPositionWithStatus(null)).and(new PaymentPositionWithTextSearch(null))
		.and(new PaymentPositionWithTextSearch("")).or(new PaymentPositionWithTextSearch("a"));
	spec.toPredicate(personRootMock, criteriaQueryMock, criteriaBuilderMock);

	spec2.toPredicate(personRootMock, criteriaQueryMock, criteriaBuilderMock);
	when(paymentPositionRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
	result = paymentService.getPaymentsByFilters("", filterModel, pageable);
	assertThat(result.getSize()).isEqualTo(1);

    }

    @Test
    void getPaymentByPaymentPositionId() {

	when(paymentPositionRepository.findById(any(Long.class)))
		.thenReturn(Optional.of(DebitorMock.createPaymentPositionMock()));
	PaymentPosition pay = paymentService.getPaymentByPaymentPositionId(1l);
	assertThat(pay.getJobId()).isEqualTo(1);
	when(paymentPositionRepository.findById(any(Long.class))).thenReturn(Optional.empty());
	pay = paymentService.getPaymentByPaymentPositionId(1l);
	assertThat(pay).isNull();
    }

    @Test
    void deletePayment() {

	when(paymentPositionRepository.findByIdAndStatus(any(Long.class), any(Integer.class)))
		.thenReturn(DebitorMock.createPaymentPositionMock());
	doNothing().when(paymentPositionRepository).delete(any(PaymentPosition.class));

	Boolean res = paymentService.deletePayment(1l, 1);
	assertThat(res).isTrue();

	when(paymentPositionRepository.findByIdAndStatus(any(Long.class), any(Integer.class))).thenReturn(null);
	res = paymentService.deletePayment(1l, 1);
	assertThat(res).isFalse();
    }

    @Test
    void getPaymentsByJobId() {
	when(paymentPositionRepository.findAllByJobId(any(Long.class))).thenReturn(null);
	List<PaymentPosition> pay = paymentService.getPaymentsByJobId(1l);
	assertThat(pay).isNull();
    }

    @Test
    void getPaymentPositionsByIds() {
	List<Long> ids = new ArrayList<>();
	ids.add(1l);
	when(paymentPositionRepository.findAllById(any())).thenReturn(null);
	List<PaymentPosition> pay = paymentService.getPaymentPositionsByIds(ids);
	assertThat(pay).isNull();
    }

    @Test
    void generatePaymentNotice() throws Exception {
	ReflectionTestUtils.setField(paymentService, "auxDigit", 3);
	ReflectionTestUtils.setField(paymentService, "segregationCode", 82);
	EnteCreditoreMinimalDto enteDto = EnteCreditoreMinimalDtoMock.getMock();
	PaDto paDto = PaDtoMock.getMock();
	List<PaymentPosition> paymentPositionList = new ArrayList<>();
	PaymentPosition posMock = DebitorMock.createPaymentPositionMock();
	posMock.setDebitor(DebitorMock.getMock());
	paymentPositionList.add(posMock);

	byte[] bb = paymentService.generatePaymentNotice(paymentPositionList, enteDto, paDto, null);
	assertThat(bb).isNotNull();

	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	try (ZipOutputStream zos = new ZipOutputStream(baos)) {

	    paymentService.generatePaymentNotice(paymentPositionList, enteDto, paDto, zos);
	    assertThat(zos).isNotNull();
	} finally {
	    baos.close();
	}
	assertThatNoException();
    }

    @Test
    void iuvValidation() {
	IuvCodeGenerator iuvCodeGenerator = new IuvCodeGenerator.Builder().setAuxDigit(3).setSegregationCode(1).build();
	IuvCodeBusiness.validate(iuvCodeGenerator);
	assertThatNoException();

	iuvCodeGenerator = new IuvCodeGenerator.Builder().setAuxDigit(2).setSegregationCode(1).build();
	IuvCodeBusiness.validate(iuvCodeGenerator);
	assertThatNoException();

	IuvCodeGenerator iuvCodeGenerator3 = new IuvCodeGenerator.Builder().setAuxDigit(2).setSegregationCode(111)
		.build();
	assertThatThrownBy(() -> IuvCodeBusiness.validate(iuvCodeGenerator3)).isInstanceOf(ValidationException.class);
	assertThatNoException();

	IuvCodeGenerator iuvCodeGenerator2 = new IuvCodeGenerator.Builder().setAuxDigit(3).setSegregationCode(null)
		.build();
	assertThatThrownBy(() -> IuvCodeBusiness.validate(iuvCodeGenerator2)).isInstanceOf(ValidationException.class);
    }

}
