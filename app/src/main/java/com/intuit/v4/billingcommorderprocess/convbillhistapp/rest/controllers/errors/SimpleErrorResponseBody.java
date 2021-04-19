/**
 * Copyright 2015-2020 Intuit Inc. All rights reserved. Unauthorized reproduction
 * is a violation of applicable law. This material contains certain
 * confidential or proprietary information and trade secrets of Intuit Inc.
 */
package com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.controllers.errors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import lombok.Data;

/**
 * This is the body of the every error response (status codes 400 - 599) that is
 * sent from the REST API.
 */
@Data
public class SimpleErrorResponseBody {
    private String message;
    private String debugInfo;

    // No status or cause provided, default to 500
    public SimpleErrorResponseBody(String message, Throwable cause, boolean scrubSensitiveData) {
        this.message = message;
        this.debugInfo = (scrubSensitiveData ? null : ExceptionUtils.getStackTrace(cause));
    }
}