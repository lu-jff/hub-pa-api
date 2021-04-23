package it.gov.pagopa.hubpa.payments.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import it.gov.pagopa.hubpa.payments.entity.Debitor;
import it.gov.pagopa.hubpa.payments.entity.IncrementalIuvNumber;
import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;
import it.gov.pagopa.hubpa.payments.mock.DebitorMock;
import it.gov.pagopa.hubpa.payments.mock.IncrementalIuvNumberMock;
import it.gov.pagopa.hubpa.payments.model.PaymentJobMinimalModel;
import it.gov.pagopa.hubpa.payments.repository.DebitorRepository;
import it.gov.pagopa.hubpa.payments.repository.IncrementalIuvNumberRepository;
import it.gov.pagopa.hubpa.payments.repository.PaymentPositionRepository;

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

   /* @Test
    void countByIdsandStatusNot() throws ServletException {
	List<Long> ids = new ArrayList<>();
	ids.add(1l);

	when(paymentJobRepository.countByJobIdInAndStatusNot(Mockito.anyList(), Mockito.anyInt())).thenReturn(2L);
	Long result = paymentJobService.countByIdsAndStatusNot(ids, 1);
	assertThat(result).isGreaterThan(1);

    }

    @Test
    void isPaymentJobAvailable() throws ServletException {

	String id = "12345678901";

	when(paymentJobRepository.countByFiscalCodeAndStatusNot(Mockito.anyString(), Mockito.anyInt())).thenReturn(2L);
	Long result = paymentJobService.countByFiscalCodeAndStatusNot(id, 1);
	assertThat(result).isGreaterThan(1);

    }*/

    @Test
    void create() throws ServletException {
	ReflectionTestUtils.setField(paymentService, "auxDigit", 3);
	ReflectionTestUtils.setField(paymentService, "segregationCode", 82);
	Debitor debitor=DebitorMock.getMock();
	Debitor debitor2=DebitorMock.getMock();
	Debitor debitor3=DebitorMock.getMock();
	IncrementalIuvNumber incrementalIuvNumberMock = IncrementalIuvNumberMock.getMock();
	List<Debitor> debitorList = new ArrayList<>();
	debitorList.add(debitor);
	debitorList.add(debitor2);
	PaymentPosition paymentPositionMock=DebitorMock.createPaymentPositionMock();
	paymentPositionMock.setId(1l);

	when(debitorRepository.findByFiscalCode(any(String.class))).thenReturn(debitor3);
	when(incrementalIuvNumberRepository.findByIdDominioPaAndAnno(Mockito.anyString(), Mockito.anyInt())).thenReturn(incrementalIuvNumberMock);
	when(debitorRepository.saveAndFlush(any(Debitor.class))).thenReturn(debitor);
	when(debitorRepository.countByFiscalCodeAndPaymentPositionOrganizationFiscalCodeAndPaymentPositionAmount(Mockito.anyString(),Mockito.anyString(),Mockito.any())).thenReturn(0l);
	
	when(paymentPositionRepository.saveAndFlush(any(PaymentPosition.class))).thenReturn(paymentPositionMock);
	PaymentJobMinimalModel result = paymentService.create(debitorList);
	assertThat(result.getNRecordFound()).isEqualTo(2);
	
	when(incrementalIuvNumberRepository.findByIdDominioPaAndAnno(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
	when(debitorRepository.countByFiscalCodeAndPaymentPositionOrganizationFiscalCodeAndPaymentPositionAmount(Mockito.anyString(),Mockito.anyString(),Mockito.any())).thenReturn(1l);
	when(debitorRepository.findByFiscalCode(any(String.class))).thenReturn(null);
	result = paymentService.create(debitorList);
	assertThat(result.getNRecordFound()).isEqualTo(2);
	
	

    }

   /* @Test
    void savePaymentJob() throws ServletException {
	PaymentJob paymentJob = PaymentJobMock.getMock();

	when(paymentJobRepository.saveAndFlush(any(PaymentJob.class))).thenReturn(paymentJob);
	Long result = paymentJobService.savePaymentJob(paymentJob);
	assertThat(result).isEqualTo(1l);

    }

    @Test
    void getAll() throws ServletException {
	List<PaymentJob> paymentJobs = new ArrayList<>();
	PaymentJob serviceMock = PaymentJobMock.getMock();
	paymentJobs.add(serviceMock);
	paymentJobs.add(serviceMock);

	when(paymentJobRepository.findByFiscalCode(any(String.class))).thenReturn(paymentJobs);

	List<PaymentJob> result = paymentJobService.getAll("12345678901");
	assertThat(result.get(0).getFileName()).isEqualTo("testFileCsv20210409.csv");
    }*/

}
