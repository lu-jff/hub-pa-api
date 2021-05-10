package it.gov.pagopa.hubpa.support.entity;

import java.time.LocalDate;

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
@Table(name = "service")
public class Support {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fiscal_code_rp", nullable = false)
    private String fiscalCodeRp;
    @Column(name = "municipality", nullable = true)
    private String municipality;
    @Column(name = "region", nullable = true)
    private String region;
    @Column(name = "province", nullable = true)
    private String province;
    @Column(name = "certified_mail", nullable = true)
    private String certifiedmail;
    @Column(name = "telephone_municipality", nullable = true)
    private String telephoneMunicipality;
    @Column(name = "full_name", nullable = true)
    private String fullName;
    @Column(name = "email", nullable = true)
    private String email;
    @Column(name = "telephone", nullable = true)
    private String telephone;
    @Column(name = "type_contact", nullable = false)
    private String typeContact;
    @Column(name = "platform_call", nullable = true)
    private String platformCall;
    @Column(name = "mail_sent_date", nullable = false)
    private LocalDate dateRequest;
    @Column(name = "mail_sent_date", nullable = false)
    private String timeRequest;

    @Column(name = "mail_sent_date", nullable = false)
    private Integer mailSentDate;

}
