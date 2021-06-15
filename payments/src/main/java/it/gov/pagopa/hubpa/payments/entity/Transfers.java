package it.gov.pagopa.hubpa.payments.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transfers")
public class Transfers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "partial_amount", nullable = false)
    private BigDecimal partialAmount;
    @Column(name = "iban", nullable = false)
    private String iban;
    @Column(name = "organization_fiscal_code", nullable = false)
    private String organizationFiscalCode;
    @Column(name = "reason", nullable = false)
    private String reason;
    @Column(name = "taxonomy", nullable = false)
    private String taxonomy;
    @Column(name = "cc_postal", nullable = true)
    private String ccPostal;

    @ManyToOne(targetEntity = PaymentOptions.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "payment_option_id")
    @JsonIgnore
    private PaymentOptions paymentOptions;

}
