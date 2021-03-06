package it.gov.pagopa.hubpa.payments.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment_options")
public class PaymentOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fiscal_code", nullable = false)
    private String fiscalCode;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "duo_date", nullable = false)
    private LocalDate duoDate;
    @Column(name = "retention_date", nullable = true)
    private LocalDate retentionDate;
    @Column(name = "is_conclusive", nullable = false)
    private Boolean isConclusive;
    @Column(name = "metadata", nullable = true)
    private String metadata;
    @Column(name = "payment_date", nullable = true)
    private LocalDate paymentDate;
    @Column(name = "notification_code", nullable = false)
    private String notificationCode;
    @Column(name = "status", nullable = false)
    private Integer status;

    

    @ManyToOne(targetEntity = PaymentPosition.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "payment_position_id")
    @JsonIgnore
    private PaymentPosition paymentPosition;

    @OneToMany(targetEntity = Transfers.class, fetch = FetchType.LAZY, mappedBy = "paymentOptions", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transfers> transfers = new ArrayList<>();

    public void addTransfers(Transfers trans) {
	transfers.add(trans);
	trans.setPaymentOptions(this);
    }

    public void removeTransfers(Transfers trans) {
	transfers.remove(trans);
	trans.setPaymentOptions(null);
    }
}
