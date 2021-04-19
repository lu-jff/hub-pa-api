package it.gov.pagopa.hubpa.servicemanagement.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "denomination", nullable = false)
    private String denomination;
    @Column(name = "fiscal_code_primary_creditor", nullable = false)
    private String fiscalCodePrimaryCreditor;
    @Column(name = "fiscal_code_secondary_creditor", nullable = false)
    private String fiscalCodeSecondaryCreditor;
    @Column(name = "percentage", nullable = false)
    private BigDecimal percentage;
    @Column(name = "total_installments", nullable = false)
    private Integer totalInstallments;

    @OneToMany(targetEntity = PaymentOptionTemplate.class, fetch = FetchType.LAZY, mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentOptionTemplate> paymentOptionTemplate = new ArrayList<>();

    public void addPaymentOptionTemplate(PaymentOptionTemplate paym) {
	paymentOptionTemplate.add(paym);
	paym.setService(this);
    }

    public void removePaymentOptionTemplate(PaymentOptionTemplate paym) {
	paymentOptionTemplate.remove(paym);
	paym.setService(null);
    }

}
