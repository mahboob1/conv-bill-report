package com.intuit.v4.billingcommorderprocess.convbillhistapp.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "BILLING_AUDIT_LOG", schema = "CRM_DATA")
public class BillingAuditLogEntity {

    @Id
    @Column(name = "BAL_BILL_ID")
    private Long billId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "BAL_CSP_ID")
    private Long subId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BAL_BILL_FOR_DATE")
    private Date billingDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BAL_BILL_FROM_DATE")
    private Date billPeriodStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BAL_BILL_TO_DATE")
    private Date billPeriodEnd;

    @Column(name = "BAL_BILL_TOT_CHARGE_AMT")
    private Double negativeChargeAmount;

    @Column(name = "BAL_SCI_CHARGE_YEAR_PERIOD")
    private Boolean isYearlyBill;

    @Column(name = "BAL_SCI_CHARGE_MONTH_PERIOD")
    private Boolean isMonthlyBill;

    @Column(name = "BAL_SCI_CHARGE_DAY_PERIOD")
    private Boolean isDailyBill;

    @OneToOne
    @JoinColumn(name = "BAL_BILL_PAYMENT_CPA_ID", referencedColumnName = "CPA_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    private CustomerPaymentEntity customerPayment;

    @Column(name = "BAL_SCI_METHOD")
    private String paymentMethod; // CC or EFT -- case insensitive

    @Column(name = "BAL_SEND_STATUS")
    private String invoiceSendStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BAL_SEND_STATUS_DATE")
    private Date invoiceSendStatusDate;
}
