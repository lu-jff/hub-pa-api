package it.gov.pagopa.hubpa.uploadpayments.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "paymentjob")
public class PaymentJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "job_id", nullable = false)
    private Long jobId;
    @Column(name = "creditor_id", nullable = false)
    private Long creditorId;
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "insert_date", nullable = false)
    private LocalDateTime insertDate;
    @Column(name = "elaboration_date", nullable = true)
    private LocalDateTime elaborationDate;
    @Column(name = "n_record_found", nullable = true)
    private Integer nRecordFound;
    @Column(name = "n_record_added", nullable = true)
    private Integer nRecordAdded;
    @Column(name = "status", nullable = true)
    private Integer status;
}
