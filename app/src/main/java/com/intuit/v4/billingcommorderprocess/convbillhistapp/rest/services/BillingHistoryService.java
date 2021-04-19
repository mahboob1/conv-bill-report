package com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.services;

import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryConversion;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryRequest;

public interface BillingHistoryService {
    BillingHistoryConversion getBillingHistory(BillingHistoryRequest billingHistoryRequest);
}
