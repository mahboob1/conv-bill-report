package com.intuit.v4.billingcommorderprocess.convbillhistapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.BillingAuditLogEntity;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.ConversionMasterHistEntity;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.CustomerPaymentEntity;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.OutboundTouchpointEntity;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.TouchpointEntity;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryRequest;

public class TestHelpers {
	public static List<BillingAuditLogEntity> getBillingAuditLogEntityList() {
		Date createDate = null;
		Date billingDate = null;
		Date billPeriodStart = null;
		Date billPeriodEnd = null;
		Date invoiceSendStatusDate = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			createDate = sdf.parse("2020-01-01");
			billingDate = sdf.parse("2020-10-01");
			billPeriodStart = sdf.parse("2020-09-01");
			billPeriodEnd = sdf.parse("2020-09-30");
			invoiceSendStatusDate = sdf.parse("2020-10-01");
		
		} catch (Exception e) {
			
		}
		List<BillingAuditLogEntity> billingAuditLogEntityList = new ArrayList<>();
		BillingAuditLogEntity billingAuditLogEntity = new BillingAuditLogEntity();
		billingAuditLogEntity.setBillId(1L);
		billingAuditLogEntity.setCreateDate(createDate);
		billingAuditLogEntity.setBillingDate(billingDate);
		billingAuditLogEntity.setBillPeriodStart(billPeriodStart);
		billingAuditLogEntity.setBillPeriodEnd(billPeriodEnd);
		billingAuditLogEntity.setInvoiceSendStatusDate(invoiceSendStatusDate);
		billingAuditLogEntity.setIsDailyBill(true);
		billingAuditLogEntity.setIsMonthlyBill(false);
		billingAuditLogEntity.setIsYearlyBill(false);
		billingAuditLogEntity.setNegativeChargeAmount(2.0);
		billingAuditLogEntity.setPaymentMethod("CC");
		billingAuditLogEntity.setSubId(1L);
		billingAuditLogEntity.setInvoiceSendStatus("InvoiceSendStatus01");
		CustomerPaymentEntity customerPayment = new CustomerPaymentEntity();
		customerPayment.setBankAccountNumber("11111111111");
		customerPayment.setCheckNumber("1111");
		customerPayment.setCountryCode("USA");
		customerPayment.setCreditCardType("TYPE001");
		customerPayment.setFinancialInstitutionNumber("FIN001");
		customerPayment.setPaymentAmount(100.00);
		customerPayment.setPaymentNumber(1L);
		customerPayment.setPaymentStatus("STATUS01");
		customerPayment.setPaymentTypeId(1);
		billingAuditLogEntity.setCustomerPayment(customerPayment);
		billingAuditLogEntityList.add(billingAuditLogEntity);
		//
		billingAuditLogEntity = new BillingAuditLogEntity();
		billingAuditLogEntity.setBillId(2L);
		billingAuditLogEntity.setCreateDate(createDate);
		billingAuditLogEntity.setBillingDate(billingDate);
		billingAuditLogEntity.setBillPeriodStart(billPeriodStart);
		billingAuditLogEntity.setBillPeriodEnd(billPeriodEnd);
		billingAuditLogEntity.setInvoiceSendStatusDate(invoiceSendStatusDate);
		billingAuditLogEntity.setIsDailyBill(false);
		billingAuditLogEntity.setIsMonthlyBill(true);
		billingAuditLogEntity.setIsYearlyBill(false);
		billingAuditLogEntity.setNegativeChargeAmount(3.0);
		billingAuditLogEntity.setPaymentMethod("EFT");
		billingAuditLogEntity.setSubId(2L);
		billingAuditLogEntity.setInvoiceSendStatus("InvoiceSendStatus02");
		customerPayment = new CustomerPaymentEntity();
		customerPayment.setBankAccountNumber("11111111112");
		customerPayment.setCheckNumber("1112");
		customerPayment.setCountryCode("CAN");
		customerPayment.setCreditCardType("TYPE002");
		customerPayment.setFinancialInstitutionNumber("FIN002");
		customerPayment.setPaymentAmount(200.00);
		customerPayment.setPaymentNumber(2L);
		customerPayment.setPaymentStatus("STATUS02");
		customerPayment.setPaymentTypeId(2);
		billingAuditLogEntity.setCustomerPayment(customerPayment);
		billingAuditLogEntityList.add(billingAuditLogEntity);
		return billingAuditLogEntityList;
	}
	
	public static List<ConversionMasterHistEntity> getConversionMasterHistEntityList() {
		return new ArrayList<ConversionMasterHistEntity>() {{
			add( new ConversionMasterHistEntity() {{
				setBatchId(1L);
				setSubId(1L);
				setRealmId("Realm01");
				setPartyId("101");
			}});
			add( new ConversionMasterHistEntity() {{
				setBatchId(2L);
				setSubId(2L);
				setRealmId("Realm02");
				setPartyId("102");
			}});
		}};
	}
	
	public static BillingHistoryRequest getBillingHistoryRequest() {
		BillingHistoryRequest billingHistoryRequest = new BillingHistoryRequest();
		billingHistoryRequest.setAccountID("Realm01");
		billingHistoryRequest.setLegacyAccountID("Realm01");
		Date billPeriodStart = null;
		Date billPeriodEnd = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			 
			billPeriodStart = sdf.parse("2020-09-01");
			billPeriodEnd = sdf.parse("2020-09-30");
		
		} catch (Exception e) {
			
		}
		billingHistoryRequest.setStartDate(billPeriodStart);
		billingHistoryRequest.setEndDate(billPeriodEnd);
		return billingHistoryRequest; 
	}
	
	public static List<OutboundTouchpointEntity> getOutboundTouchpointEntityList() {
		Date createDate = null;
		 
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			createDate = sdf.parse("2020-01-01");
		} catch (Exception e) {
			
		}
		List<OutboundTouchpointEntity> outboundTouchpointEntityList = new ArrayList<>();
		OutboundTouchpointEntity outboundTouchpointEntity = new OutboundTouchpointEntity();
		outboundTouchpointEntity.setCreateDate(createDate);
		outboundTouchpointEntity.setDescription("Bill Description 01");
		outboundTouchpointEntity.setId(1L);
		outboundTouchpointEntity.setLanguage("EN");
		outboundTouchpointEntity.setMessageBody("PDF Documnet message body 001");
		TouchpointEntity touchpointEntity = new TouchpointEntity();
		touchpointEntity.setId(1L);
		touchpointEntity.setPartyId(1L);
		outboundTouchpointEntity.setTouchpoint(touchpointEntity);
		outboundTouchpointEntityList.add(outboundTouchpointEntity);
		//
		outboundTouchpointEntity = new OutboundTouchpointEntity();
		outboundTouchpointEntity.setCreateDate(createDate);
		outboundTouchpointEntity.setDescription("Bill Description 02");
		outboundTouchpointEntity.setId(2L);
		outboundTouchpointEntity.setLanguage("EN");
		outboundTouchpointEntity.setMessageBody("PDF Documnet message body 002");
		touchpointEntity = new TouchpointEntity();
		touchpointEntity.setId(2L);
		touchpointEntity.setPartyId(2L);
		outboundTouchpointEntity.setTouchpoint(touchpointEntity);
		outboundTouchpointEntityList.add(outboundTouchpointEntity);
		return outboundTouchpointEntityList;
	}
	
	public static String getBase64Data() {
		return "base64Data-PDF001"; 
	}
}
