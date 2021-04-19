/**
 * Copyright 2015-2020 Intuit Inc. All rights reserved. Unauthorized reproduction
 * is a violation of applicable law. This material contains certain
 * confidential or proprietary information and trade secrets of Intuit Inc.
 */
package com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.services;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.intuit.v4.billingcommorderprocess.convbillhistapp.TestHelpers;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.BillingAuditLogEntity;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.ConversionMasterHistEntity;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.OutboundTouchpointEntity;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryRequest;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.ca.CaBillingAuditLogRepository;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.ca.CaConversionMasterHistRepository;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.ca.CaOutboundTouchpointRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"cntrlsvc.dbName=UK"})
public class BillingHistoryServiceImplTest {

    @Autowired
    private BillingHistoryServiceImpl billingHistoryService;
    
    @MockBean
    private BillingRepoFactory billingRepoFactory;
    
    @MockBean
    CaBillingAuditLogRepository billingAuditLogRepository;
	
    @MockBean
	CaConversionMasterHistRepository conversionMasterHistRepository;
	
    @MockBean
	CaOutboundTouchpointRepository outboundTouchpointRepository;
	
    @MockBean
	DocumentService documentService;
	
	List<ConversionMasterHistEntity> conversionMasterHistEntityList;
	List<BillingAuditLogEntity> billingAuditLogEntityList;
	List<OutboundTouchpointEntity> outboundTouchpointEntityList;
	BillingHistoryRequest billingHistoryRequest;
	String base64Data;
	 
	
	@Before
	public void init() {
		conversionMasterHistEntityList = TestHelpers.getConversionMasterHistEntityList();
		billingAuditLogEntityList = TestHelpers.getBillingAuditLogEntityList();
		outboundTouchpointEntityList = TestHelpers.getOutboundTouchpointEntityList();
		billingHistoryRequest = TestHelpers.getBillingHistoryRequest();
		base64Data = TestHelpers.getBase64Data();
	}

    @Test
    public void getBillingHistoryTest() {
    	when(billingRepoFactory.getConversionMasterHistRepository(anyString())).thenReturn(conversionMasterHistRepository);
    	when(conversionMasterHistRepository.findByRealmId(anyString())).thenReturn(conversionMasterHistEntityList);
    	
    	when(billingRepoFactory.getBillingAuditLogRepository(anyString())).thenReturn(billingAuditLogRepository);
    	when(billingAuditLogRepository.findBySubIdInAndCreateDateAfterAndCreateDateBefore(anyCollection(),
                any(Date.class), any(Date.class))).thenReturn(billingAuditLogEntityList);
    	
    	when(billingRepoFactory.getOutboundTouchpointRepository(anyString())).thenReturn(outboundTouchpointRepository);
    	when(outboundTouchpointRepository.findByCreateDateAfterAndCreateDateBeforeAndTouchpointPartyIdInAndDescriptionLikeIgnoreCaseOrderByCreateDateAsc(
    			any(Date.class), any(Date.class), anyCollection(), anyString())).thenReturn(outboundTouchpointEntityList);
    	
    	when(documentService.caXmlToBase64Pdf(anyString(), anyLong(), anyString())).thenReturn(base64Data);
    	
    	billingHistoryService.getBillingHistory(billingHistoryRequest);
        
    }

}
