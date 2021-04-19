package com.intuit.v4.billingcommorderprocess.convbillhistapp.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingHistoryDocumentMetadata {
    private String accountID;
    private String billingNumber;
    private String billingDate;
    private String paymentType;
    private String paymentSubType;
    private String maskedAccountNumber;
    private double amount;
    private String currency;
}
