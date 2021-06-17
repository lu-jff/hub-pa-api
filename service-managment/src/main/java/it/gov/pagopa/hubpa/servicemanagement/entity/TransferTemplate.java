package it.gov.pagopa.hubpa.servicemanagement.entity;

import java.math.BigDecimal;

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

import org.hibernate.annotations.Check;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transfer_template")
@Check(constraints = "postal_iban IS NOT NULL AND postal_iban_holder IS NOT NULL AND postal_auth_code IS NOT NULL OR iban IS NOT NULL AND postal_iban IS NULL AND postal_iban_holder IS NULL AND postal_auth_code IS NULL ")

public class TransferTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "percentage", nullable = false)
    private BigDecimal percentage;

    @Column(name = "iban", nullable = true)
    private String iban;

    @Column(name = "postal_iban", nullable = true)
    private String postalIban;
    @Column(name = "postal_iban_holder", nullable = true)
    private String postalIbanHolder;
    @Column(name = "postal_auth_code", nullable = true)
    private String postalAuthCode;

    @ManyToOne(targetEntity = PaymentOptionTemplate.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_option_template_id")
    @JsonIgnore
    private PaymentOptionTemplate paymentOptionTemplate;

    @Column(name = "is_secondary_creditor", nullable = false)
    private Boolean isSecondaryCreditor;

}
