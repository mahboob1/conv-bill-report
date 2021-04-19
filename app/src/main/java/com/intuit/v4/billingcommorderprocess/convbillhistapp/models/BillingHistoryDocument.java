package com.intuit.v4.billingcommorderprocess.convbillhistapp.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BillingHistoryDocument {
    private BillingHistoryDocumentMetadata metadata;
    private List<AbstractBillingHistoryDocumentContent> billingData;
}
