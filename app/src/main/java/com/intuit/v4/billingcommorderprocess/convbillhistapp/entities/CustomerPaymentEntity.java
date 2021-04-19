package com.intuit.v4.billingcommorderprocess.convbillhistapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "CUSTOMER_PAYMENT", schema = "CRM_DATA")
public class CustomerPaymentEntity {

    @Id
    @Column(name = "CPA_ID")
    private Long paymentNumber;

    @Column(name = "CPA_PAYTYPE")
    private Integer paymentTypeId;

    @Column(name = "CPA_CREDIT_CARD_NUMBER")
    private String maskedCreditCardNumber;

    @Column(name = "CPA_CREDIT_CARD_TYPE")
    private String creditCardType;

    @Column(name = "CPA_FINANCIAL_INSTITUTION_ID")
    private String financialInstitutionNumber;

    @Column(name = "CPA_BANK_ACCOUNT_NUMBER")
    private String bankAccountNumber; // not sure if this is the full number. If so, follow format of *8369

    @Column(name = "CPA_CHEQUE_NUMBER")
    private String checkNumber;

    @Column(name = "CPA_PAYMENT_AMOUNT")
    private Double paymentAmount;

    @Column(name = "CPA_APPROVED")
    private String paymentStatus;

    @Column(name = "CPA_CNT_ISO_CODE")
    private String countryCode;
}
