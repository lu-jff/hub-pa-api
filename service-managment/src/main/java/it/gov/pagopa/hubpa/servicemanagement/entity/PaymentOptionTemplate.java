package it.gov.pagopa.hubpa.servicemanagement.entity;

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
@Table(name = "payment_option_template")
public class PaymentOptionTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "installment_number", nullable = false)
    private Integer installmentNumber;
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @ManyToOne(targetEntity = Service.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    @JsonIgnore
    private Service service;

    @Column(name = "is_final", nullable = false)
    private Boolean isFinal;

    @OneToMany(targetEntity = TransferTemplate.class, fetch = FetchType.LAZY, mappedBy = "paymentOptionTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransferTemplate> transferTemplate = new ArrayList<>();

    public void addTransferTemplate(TransferTemplate trans) {
	transferTemplate.add(trans);
	trans.setPaymentOptionTemplate(this);
    }

    public void removeTransferTemplate(TransferTemplate trans) {
	transferTemplate.remove(trans);
	trans.setPaymentOptionTemplate(null);
    }

}
