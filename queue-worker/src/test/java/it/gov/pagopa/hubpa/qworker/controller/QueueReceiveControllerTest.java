package it.gov.pagopa.hubpa.qworker.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import it.gov.pagopa.hubpa.commons.model.PaymentJobMinimalModel;
import it.gov.pagopa.hubpa.commons.model.UploadCsvModel;
import it.gov.pagopa.hubpa.commons.model.BooleanResponseModel;

import it.gov.pagopa.hubpa.testutils.model.mock.UploadCsvModelMock;
import it.gov.pagopa.hubpa.testutils.model.mock.PaymentJobMinimalModelMock;

@ExtendWith(MockitoExtension.class)
public class QueueReceiveControllerTest {

  @InjectMocks
  private QueueReceiveController queueReceiveController;

  @Mock
  private RestTemplate restTemplate;

  @Test
  public void receiveMessageShouldNotThrow() {

    when(restTemplate.postForObject(matches("/payments/create"), any(UploadCsvModel.class),
        eq(PaymentJobMinimalModel.class))).thenReturn(PaymentJobMinimalModelMock.getMock());

    BooleanResponseModel goodResponse = new BooleanResponseModel();
    goodResponse.setResult(true);

    when(restTemplate.postForObject(matches("/upload-payments/update/"), any(PaymentJobMinimalModel.class),
        eq(BooleanResponseModel.class))).thenReturn(goodResponse);

    UploadCsvModel myCsv = UploadCsvModelMock.getMock();

    queueReceiveController.receiveMessage(myCsv);

    verify(restTemplate, times(2)).postForObject(anyString(), any(), any());

  }

}
