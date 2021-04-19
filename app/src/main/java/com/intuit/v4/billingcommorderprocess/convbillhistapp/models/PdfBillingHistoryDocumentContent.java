package com.intuit.v4.billingcommorderprocess.convbillhistapp.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdfBillingHistoryDocumentContent extends AbstractBillingHistoryDocumentContent {
    private String base64Data;
}
