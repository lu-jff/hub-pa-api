package it.gov.pagopa.hubpa.uploadpayments.mapping;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import it.gov.pagopa.hubpa.uploadpayments.entity.PaymentJob;
import it.gov.pagopa.hubpa.uploadpayments.enumeration.JobStatusEnum;
import it.gov.pagopa.hubpa.uploadpayments.model.UploadCsvModel;

public class ConvertUploadCsvModelToPaymentJob implements Converter<UploadCsvModel, PaymentJob> {
    @Override
    public PaymentJob convert(MappingContext<UploadCsvModel, PaymentJob> context) {
	UploadCsvModel source = context.getSource();
	PaymentJob destination = new PaymentJob();
	destination.setFiscalCode(source.getFiscalCodeCreditor());
	destination.setFileName(source.getCsv().getFileName());
	destination.setInsertDate(LocalDateTime.now(ZoneId.of("Europe/Paris")));
	destination.setStatus(JobStatusEnum.IN_ATTESA.getStatus());

	return destination;
    }

}
