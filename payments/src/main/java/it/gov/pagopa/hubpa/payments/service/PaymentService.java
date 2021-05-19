
package it.gov.pagopa.hubpa.payments.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import it.gov.pagopa.hubpa.payments.entity.Debitor;
import it.gov.pagopa.hubpa.payments.entity.IncrementalIuvNumber;
import it.gov.pagopa.hubpa.payments.entity.PaymentOptions;
import it.gov.pagopa.hubpa.payments.entity.PaymentPosition;
import it.gov.pagopa.hubpa.payments.enumeration.JobStatusEnum;
import it.gov.pagopa.hubpa.payments.iuvgenerator.IuvCodeBusiness;
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

@Service
public class PaymentService {

    @Autowired
    private DebitorRepository debitorRepository;

    @Autowired
    IncrementalIuvNumberRepository incrementalIuvNumberRepository;

    @Autowired
    private PaymentPositionRepository paymentPositionRepository;

    @Value("${iuv.generate.auxDigit}")
    private Integer auxDigit;

    @Value("${iuv.generate.segregationCode}")
    private Integer segregationCode;

    public PaymentJobMinimalModel create(List<Debitor> debitors) {

	Long jobId = null;
	int nRecordFound = 0;
	int nRecordAdded = 0;
	int nRecordWarning = 0;

	for (Debitor debitor : debitors) {
	    Debitor newDebitor = debitorRepository.findByFiscalCode(debitor.getFiscalCode());
	    if (newDebitor == null) {
		newDebitor = copyDebitor(debitor);
	    }
	    int nRecordAddedPre = nRecordAdded;
	    int nRecordWarningPre = nRecordWarning;
	    try {
		for (PaymentPosition position : debitor.getPaymentPosition()) {
		    jobId = position.getJobId();
		    nRecordFound++;

		    Boolean check = checkDuplicatePayment(debitor.getFiscalCode(), position);
		    nRecordAdded++;
		    addNotificationNumber(position.getOrganizationFiscalCode(), position.getPaymentOptions());
		    position.setDebitor(null);
		    position.setJobId(jobId);
		    if (!check.booleanValue()) {
			nRecordWarning++;
			position.setInformation("POSSIBLE_DUPLICATE");
		    }

		    PaymentPosition pos = paymentPositionRepository.saveAndFlush(position);

		    newDebitor.addPaymentPosition(pos);

		}
		if (!newDebitor.getPaymentPosition().isEmpty()) {

		    debitorRepository.saveAndFlush(newDebitor);
		}
	    } catch (Exception ee) {
		ee.printStackTrace();
		nRecordAdded = nRecordAddedPre;
		nRecordWarning = nRecordWarningPre;
	    }

	}

	PaymentJobMinimalModel p = new PaymentJobMinimalModel();
	p.setJobId(jobId);
	p.setNRecordFound(nRecordFound);
	p.setNRecordAdded(nRecordAdded);
	p.setNRecordWarning(nRecordWarning);
	p.setStatus(
		nRecordFound == nRecordAdded ? JobStatusEnum.SUCCESSO.getStatus() : JobStatusEnum.PARZIALE.getStatus());
	return p;
    }

    private void addNotificationNumber(String creditorFiscalCode, List<PaymentOptions> paymentsOptions) {
	if (paymentsOptions != null) {
	    for (PaymentOptions paymentOptions : paymentsOptions) {
		paymentOptions.setNotificationCode(generateNotificationCode(creditorFiscalCode));
	    }
	}

    }

    private String generateNotificationCode(String idDominioPa) {
	Long lastNumber = 1l;
	IncrementalIuvNumber incrementalIuvNumber = incrementalIuvNumberRepository.findByIdDominioPaAndAnno(idDominioPa,
		LocalDateTime.now(ZoneId.of("Europe/Paris")).getYear());
	if (incrementalIuvNumber != null) {
	    lastNumber = (incrementalIuvNumber.getLastUsedNumber() + 1);
	    incrementalIuvNumber.setLastUsedNumber(lastNumber);

	} else {

	    incrementalIuvNumber = new IncrementalIuvNumber();
	    incrementalIuvNumber.setAnno(LocalDateTime.now(ZoneId.of("Europe/Paris")).getYear());
	    incrementalIuvNumber.setIdDominioPa(idDominioPa);
	    incrementalIuvNumber.setLastUsedNumber(lastNumber);
	}
	incrementalIuvNumberRepository.saveAndFlush(incrementalIuvNumber);

	IuvCodeGenerator iuvCodeGenerator = new IuvCodeGenerator.Builder().setAuxDigit(auxDigit)
		.setSegregationCode(segregationCode).build();

	IuvCodeBusiness.validate(iuvCodeGenerator);
	return auxDigit + IuvCodeBusiness.generateIUV(segregationCode, lastNumber + "");

    }

    private Boolean checkDuplicatePayment(String debitorFiscalCode, PaymentPosition position) {
	long count = debitorRepository
		.countByFiscalCodeAndPaymentPositionOrganizationFiscalCodeAndPaymentPositionAmount(debitorFiscalCode,
			position.getOrganizationFiscalCode(), position.getAmount());
	return count == 0;
    }

    private Debitor copyDebitor(Debitor debitor) {
	Debitor newDebitor = new Debitor();
	newDebitor.setAddress(debitor.getAddress());
	newDebitor.setArea(debitor.getArea());
	newDebitor.setCap(debitor.getCap());
	newDebitor.setCountry(debitor.getCountry());
	newDebitor.setEmail(debitor.getEmail());
	newDebitor.setFiscalCode(debitor.getFiscalCode());
	newDebitor.setId(debitor.getId());
	newDebitor.setIdTenant(debitor.getIdTenant());
	newDebitor.setName(debitor.getName());
	newDebitor.setNumber(debitor.getNumber());
	newDebitor.setPhone(debitor.getPhone());
	newDebitor.setProvince(debitor.getProvince());
	newDebitor.setSurname(debitor.getSurname());
	newDebitor.setType(debitor.getType());

	return newDebitor;
    }

    public Page<PaymentPosition> getPaymentsByFilters(String fiscalCode, FilterModel filters, Pageable pageable) {
	if(filters==null) {
	    filters=new FilterModel();
	}
	LocalDateTime dateFromTime=null;
	LocalDateTime dateToTime=null;
	LocalDate dateFrom=filters.getDateFrom();
	LocalDate dateTo=filters.getDateTo();
	if(dateFrom!=null) {
	    dateFromTime=dateFrom.atStartOfDay();
	}
	if(dateTo!=null) {
	    dateToTime=dateTo.atTime(23, 59, 59, 999999999);
	}
	Specification<PaymentPosition> spec = Specification.where(new PaymentPositionWithDateFrom(dateFromTime))
                .and(new PaymentPositionWithDateTo(dateToTime))
                .and(new PaymentPositionWithFiscalCode(fiscalCode))
                .and(new PaymentPositionWithStatus(filters.getStatus()))
                .and(new PaymentPositionWithTextSearch(filters.getTextSearch()));
	return paymentPositionRepository.findAll(spec, pageable);
    }

    public PaymentPosition getPaymentByPaymentPositionId(Long id) {
	Optional<PaymentPosition> res = paymentPositionRepository.findById(id);
	PaymentPosition paymentPos=null;
	if(res.isPresent()){
	    paymentPos=res.get();
	}
	return paymentPos;
    }
    public List<PaymentPosition> getPaymentsByJobId(Long jobId){
	return paymentPositionRepository.findAllByJobId(jobId);
    }

}
