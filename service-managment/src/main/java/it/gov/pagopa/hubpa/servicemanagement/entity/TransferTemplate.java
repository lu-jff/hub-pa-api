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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transfer_template")
public class TransferTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "percentage", nullable = false)
    private BigDecimal percentage;

    @Column(name = "iban", nullable = false)
    private String iban;

    @ManyToOne(targetEntity = PaymentOptionTemplate.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_option_template_id")
    @JsonIgnore
    private PaymentOptionTemplate paymentOptionTemplate;

    @Column(name = "is_secondary_creditor", nullable = false)
    private Boolean isSecondaryCreditor;

}
