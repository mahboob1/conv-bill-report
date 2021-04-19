package com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.services;

import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.BillingAuditLogEntity;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.ConversionMasterHistEntity;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.CustomerPaymentEntity;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.OutboundTouchpointEntity;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.AbstractBillingHistoryDocumentContent;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryConversion;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryDocument;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryDocumentMetadata;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryDocumentType;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryRequest;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.PdfBillingHistoryDocumentContent;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@ConfigurationProperties(prefix = "cntrlsvc")
public class BillingHistoryServiceImpl implements BillingHistoryService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BillingHistoryServiceImpl.class);
	private static final String pattern = "%BILL%";
	private static final String TEMPLATE_EN = "invoice-en_CA.html";
	private static final String DB_CA = "CA";
	private static final String DB_UK = "UK";
	
	private String dbName;
	
	@Autowired
	private BillingRepoFactory billingRepoFactory;
	
	@Autowired
	DocumentService documentService;
	
    @Override
    public BillingHistoryConversion getBillingHistory(BillingHistoryRequest billingHistoryRequest) {
    	BillingHistoryConversion billingHistoryConversion = new BillingHistoryConversion();
    	List<ConversionMasterHistEntity> conversionMasterHistEntityList = billingRepoFactory.getConversionMasterHistRepository(DB_UK).findByRealmId(billingHistoryRequest.getAccountID());
    	Collection<Long> subIds = new ArrayList<>();
    	Collection<Long> partyIds = new ArrayList<>();
    	for(ConversionMasterHistEntity conversionMasterHistEntity: conversionMasterHistEntityList) {
    		subIds.add(conversionMasterHistEntity.getSubId());
    		partyIds.add(Long.valueOf(conversionMasterHistEntity.getPartyId()));
    	}
    	
    	List<BillingAuditLogEntity> billingAuditLogEntityList = billingRepoFactory.getBillingAuditLogRepository(DB_UK).findBySubIdInAndCreateDateAfterAndCreateDateBefore(subIds,
                billingHistoryRequest.getStartDate(), billingHistoryRequest.getEndDate());
    	
    	billingHistoryConversion.setBillingHistoryDocumentType(BillingHistoryDocumentType.BASE_64.name());
    	List<BillingHistoryDocument> billingHistoryDocuments = new ArrayList<>();
    	billingHistoryConversion.setBillingHistoryDocuments(billingHistoryDocuments);
    	 
    	for(BillingAuditLogEntity billingAuditLogEntity: billingAuditLogEntityList) {
    		BillingHistoryDocumentMetadata metadata = transform(billingAuditLogEntity.getCustomerPayment());
    		BillingHistoryDocument billingHistoryDocument = new BillingHistoryDocument();
    		billingHistoryDocuments.add(billingHistoryDocument);
    		billingHistoryDocument.setMetadata(metadata);
    		List<AbstractBillingHistoryDocumentContent> billingData = new ArrayList<>();
        	billingHistoryDocument.setBillingData(billingData);
    		List<OutboundTouchpointEntity> outboundTouchpointEntityList = billingRepoFactory.getOutboundTouchpointRepository(DB_UK).
    				findByCreateDateAfterAndCreateDateBeforeAndTouchpointPartyIdInAndDescriptionLikeIgnoreCaseOrderByCreateDateAsc(
    				billingAuditLogEntity.getBillPeriodStart(), billingAuditLogEntity.getBillPeriodEnd(), partyIds, pattern);
    		for(OutboundTouchpointEntity outboundTouchpointEntity: outboundTouchpointEntityList) {
    			PdfBillingHistoryDocumentContent billingHistoryDocumentContent = new PdfBillingHistoryDocumentContent();
    			if(dbName.equals(DB_CA)) {
    				billingHistoryDocumentContent.setBase64Data(documentService.caXmlToBase64Pdf(outboundTouchpointEntity.getMessageBody(), outboundTouchpointEntity.getId(), TEMPLATE_EN));
    			} else if(dbName.equals(DB_UK)) {
    				billingHistoryDocumentContent.setBase64Data(documentService.ukHtmlToBase64Pdf(outboundTouchpointEntity.getMessageBody()));
    			}
    			billingData.add(billingHistoryDocumentContent);
    		}
    	}
        return billingHistoryConversion; 
    }
    
    private BillingHistoryDocumentMetadata transform(CustomerPaymentEntity customerPayment) {
    	BillingHistoryDocumentMetadata billingHistoryDocumentMetadata = new BillingHistoryDocumentMetadata();
    	billingHistoryDocumentMetadata.setAccountID(customerPayment.getBankAccountNumber());
    	billingHistoryDocumentMetadata.setAmount(customerPayment.getPaymentAmount());
    	billingHistoryDocumentMetadata.setBillingNumber(String.valueOf(customerPayment.getPaymentNumber()));
    	billingHistoryDocumentMetadata.setPaymentType(String.valueOf(customerPayment.getPaymentTypeId()));
    	return billingHistoryDocumentMetadata;
    }
}
