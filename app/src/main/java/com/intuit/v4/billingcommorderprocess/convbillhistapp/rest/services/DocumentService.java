package com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.services;

public interface DocumentService {
    String ukHtmlToBase64Pdf(String html);
    String caXmlToBase64Pdf(String xml, Long paymentNumber, String templateName);
}
