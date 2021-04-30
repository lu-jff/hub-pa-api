package it.gov.pagopa.hubpa.payments.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
import it.gov.pagopa.hubpa.payments.mock.DebitorMock;
import it.gov.pagopa.hubpa.payments.mock.FilterModelMock;
import it.gov.pagopa.hubpa.payments.mock.IncrementalIuvNumberMock;
import it.gov.pagopa.hubpa.payments.model.FilterModel;
import it.gov.pagopa.hubpa.payments.model.PaymentJobMinimalModel;
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

    /*
     * @Test void countByIdsandStatusNot() throws ServletException { List<Long> ids
     * = new ArrayList<>(); ids.add(1l);
     * 
     * when(paymentJobRepository.countByJobIdInAndStatusNot(Mockito.anyList(),
     * Mockito.anyInt())).thenReturn(2L); Long result =
     * paymentJobService.countByIdsAndStatusNot(ids, 1);
     * assertThat(result).isGreaterThan(1);
     * 
     * }
     * 
     * @Test void isPaymentJobAvailable() throws ServletException {
     * 
     * String id = "12345678901";
     * 
     * when(paymentJobRepository.countByFiscalCodeAndStatusNot(Mockito.anyString(),
     * Mockito.anyInt())).thenReturn(2L); Long result =
     * paymentJobService.countByFiscalCodeAndStatusNot(id, 1);
     * assertThat(result).isGreaterThan(1);
     * 
     * }
     */

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

    }

    @Test
    void getPaymentsByFilters() {
	Pageable pageable = PageRequest.of(0, 8);
	FilterModel filterModel = FilterModelMock.getMock();
	PaymentPosition paymentPosition = DebitorMock.createPaymentPositionMock();
	List<PaymentPosition> listPay = new ArrayList<>();
	listPay.add(paymentPosition);
	Page<PaymentPosition> page = new PageImpl<>(listPay);

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
		.and(new PaymentPositionWithStatus(null)).and(new PaymentPositionWithTextSearch(null));
	spec.toPredicate(personRootMock, criteriaQueryMock, criteriaBuilderMock);

	try {
	    spec2.toPredicate(personRootMock, criteriaQueryMock, criteriaBuilderMock);
	} catch (Exception e) {
	}
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
	when(paymentPositionRepository.findById(any(Long.class)))
	.thenReturn(Optional.empty());
	pay = paymentService.getPaymentByPaymentPositionId(1l);
	assertThat(pay).isNull();
    }

    @Test
    void getPaymentsByJobId() {
	when(paymentPositionRepository.findAllByJobId(any(Long.class))).thenReturn(null);
	List<PaymentPosition> pay = paymentService.getPaymentsByJobId(1l);
	assertThat(pay).isNull();
    }

}
