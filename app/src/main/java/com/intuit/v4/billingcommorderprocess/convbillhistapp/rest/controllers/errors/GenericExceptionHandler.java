/**
 * Copyright 2015-2020 Intuit Inc. All rights reserved. Unauthorized reproduction
 * is a violation of applicable law. This material contains certain
 * confidential or proprietary information and trade secrets of Intuit Inc.
 */
package com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.controllers.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@ControllerAdvice
public class GenericExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericExceptionHandler.class);

    @Value("${server.include-debug-info}")
    private boolean includeDebugInfo;

    @ExceptionHandler
    public SimpleErrorResponse handleDocumentException(SecurityException e) {
        LOGGER.error("Security exception occurred", e);
        return genericErrorResponse(HttpStatus.FORBIDDEN, "Access forbidden", e);
    }

    @ExceptionHandler
    public SimpleErrorResponse handleBadRequestBodyException(HttpMessageNotReadableException e) {
        return invalidRequest(e);
    }

    @ExceptionHandler
    public SimpleErrorResponse handleBadRequestValidationException(MethodArgumentNotValidException e) {
        return invalidRequest(e);
    }

    @ExceptionHandler
    public SimpleErrorResponse handleBadRequestMediaException(HttpMediaTypeNotSupportedException e) {
        return invalidRequest(e);
    }

    @ExceptionHandler
    public SimpleErrorResponse handleOtherErrors(Throwable t) {
        LOGGER.error("handling an unexpected exception", t);
        return genericErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred processing this request", t);
    }

    private SimpleErrorResponse invalidRequest(Throwable e) {
        LOGGER.error("Invalid request exception", e);
        return genericErrorResponse(HttpStatus.BAD_REQUEST, "Bad request", e);
    }

    private SimpleErrorResponse genericErrorResponse(HttpStatus status, String message, Throwable e) {
        return new SimpleErrorResponse(status, message, e, includeDebugInfo);
    }
}
