package it.gov.pagopa.hubpa.uploadpayments.service;

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

import it.gov.pagopa.hubpa.uploadpayments.entity.PaymentJob;
import it.gov.pagopa.hubpa.uploadpayments.mock.PaymentJobMock;
import it.gov.pagopa.hubpa.uploadpayments.repository.PaymentJobRepository;

@ExtendWith(MockitoExtension.class)
class UploadPaymentServiceTest {

    @InjectMocks
    private PaymentJobService paymentJobService;

    @Mock
    PaymentJobRepository paymentJobRepository;
    
    @Test
    void countByIdsandStatusNot() throws ServletException {
	List<Long> ids = new ArrayList<>();
	ids.add(1l);

	when(paymentJobRepository.countByJobIdInAndStatusNot(Mockito.anyList(), Mockito.anyInt())).thenReturn(2L);
	Long result = paymentJobService.countByIdsandStatusNot(ids, 1);
	assertThat(result).isGreaterThan(1);

    }
    @Test
    void isPaymentJobAvailable() throws ServletException {

	Long id = 1l;

	when(paymentJobRepository.countByCreditorIdAndStatusNot(Mockito.anyLong(), Mockito.anyInt())).thenReturn(2L);
	Long result = paymentJobService.countByCreditorIdAndStatusNot(id, 1);
	assertThat(result).isGreaterThan(1);

    }
    @Test
    void create() throws ServletException {
	PaymentJob paymentJob = PaymentJobMock.getMock();

	when(paymentJobRepository.saveAndFlush(any(PaymentJob.class))).thenReturn(paymentJob);
	Boolean result = paymentJobService.create(paymentJob);
	assertThat(result).isTrue();

    }
    
    @Test
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

	when(paymentJobRepository.findByCreditorId(any(Long.class))).thenReturn(paymentJobs);

	List<PaymentJob> result = paymentJobService.getAll(5L);
	assertThat(result.get(0).getFileName()).isEqualTo("testFileCsv20210409.csv");
    }

}
