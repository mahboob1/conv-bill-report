package com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.BillingAuditLogRepository;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.ConversionMasterHistRepository;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.OutboundTouchpointRepository;

@Component
public class BillingRepoFactory {
	
	private static final String DB_CA = "CA";
	private static final String DB_UK = "UK";
	   
    @Autowired
    @Qualifier("qcaBillingAuditLogRepository")
	private BillingAuditLogRepository caBillingAuditLogRepository;
    
    @Autowired
    @Qualifier("qukBillingAuditLogRepository")
    private BillingAuditLogRepository ukBillingAuditLogRepository;
	
	@Autowired
	@Qualifier("qcaConversionMasterHistRepository")
	private ConversionMasterHistRepository caConversionMasterHistRepository;
	
	@Autowired
	@Qualifier("qukConversionMasterHistRepository")
	ConversionMasterHistRepository ukConversionMasterHistRepository;
	
	@Autowired
	@Qualifier("qcaOutboundTouchpointRepository")
	private OutboundTouchpointRepository caOutboundTouchpointRepository;
	
	@Autowired
	@Qualifier("qukOutboundTouchpointRepository")
	private OutboundTouchpointRepository ukOutboundTouchpointRepository;
	
    public BillingAuditLogRepository getBillingAuditLogRepository(String dbName){
        if(dbName.equals(DB_CA)){
            return caBillingAuditLogRepository;
        } else {
            return ukBillingAuditLogRepository;
        }
    }
    
    public ConversionMasterHistRepository getConversionMasterHistRepository(String dbName){
        if(dbName.equals(DB_CA)){
            return caConversionMasterHistRepository;
        } else {
            return ukConversionMasterHistRepository;
        }
    }
    
    public OutboundTouchpointRepository getOutboundTouchpointRepository(String dbName){
        if(dbName.equals(DB_CA)){
            return caOutboundTouchpointRepository;
        } else {
            return ukOutboundTouchpointRepository;
        }
    }

}
