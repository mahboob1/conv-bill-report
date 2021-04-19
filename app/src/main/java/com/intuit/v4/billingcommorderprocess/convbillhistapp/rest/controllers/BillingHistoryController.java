package com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.controllers;

import com.intuit.platform.jsk.security.iam.authn.IntuitTicketAuthentication;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryConversion;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryRequest;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.services.BillingHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "v1/billing/billing_history", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
@Tag(name = "billing history")
public class BillingHistoryController {

    @Autowired
    private BillingHistoryService billingHistoryService;

    @RequestMapping(method = RequestMethod.POST)
    @Operation(description = "Get billing history",
            summary = "Get legacy billing history for a migrated QBDT Canada/UK subscription",
            security = { @SecurityRequirement(name = "privateAuthUser", scopes = {}) })
    public BillingHistoryConversion getBillingHistory(@Parameter(hidden = true) IntuitTicketAuthentication principal,
                                                      @Valid @RequestBody BillingHistoryRequest billingHistoryRequest) {
        verifySecurityConstraints(principal);
        return billingHistoryService.getBillingHistory(billingHistoryRequest);
    }

    private void verifySecurityConstraints(IntuitTicketAuthentication principal) {
        if (!isAllowedSystemUser(principal)) {
            throw new SecurityException("Billing history is only available for offline system users.");
        }
    }

    private boolean isAllowedSystemUser(IntuitTicketAuthentication principal) {
        //return principal.getPrincipal().isOffline() && principal.getPrincipal().isSystemUser();
    	return true;
    }
}
