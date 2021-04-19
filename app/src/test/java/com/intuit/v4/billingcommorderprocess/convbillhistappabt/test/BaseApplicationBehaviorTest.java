package com.intuit.v4.billingcommorderprocess.convbillhistappabt.test;

import com.intuit.v4.billingcommorderprocess.convbillhistapp.TestHelpers;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.BillingAuditLogEntity;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.ConversionMasterHistEntity;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryConversion;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryDocumentType;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryRequest;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.ConversionMasterHistRepository;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.controllers.BillingHistoryController;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.services.BillingHistoryService;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.services.BillingRepoFactory;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.services.DocumentService;
import com.intuit.v4.billingcommorderprocess.convbillhistappabt.TestApp;

import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.intuit.platform.integration.hats.common.IAMTicket;
import com.intuit.platform.jsk.security.iam.authn.IntuitTicketAuthentication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(properties = {"cntrlsvc.dbName=UK"})
public class BaseApplicationBehaviorTest {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
    @Autowired
	private BillingHistoryController billingHistoryController;

    @Autowired
	private BillingHistoryService billingHistoryService;
    
    @Autowired
	DocumentService documentService;
	
	@MockBean
	private IAMTicket iamTicket;
	
	@Autowired
	private BillingRepoFactory billingRepoFactory;
	   
	@Autowired
	ConversionMasterHistRepository conversionMasterHistRepository;
    
	List<ConversionMasterHistEntity> conversionMasterHistEntityList;
	List<BillingAuditLogEntity> billingAuditLogEntityList;
	BillingHistoryRequest billingHistoryRequest;
	
	private void trustSelfSignedSSL() {
	    try {
	        SSLContext ctx = SSLContext.getInstance("TLS");
	        X509TrustManager tm = new X509TrustManager() {

	            public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
	            }

	            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
	            }

	            public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	        };
	        ctx.init(null, new TrustManager[]{tm}, null);
	        SSLContext.setDefault(ctx);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}

    @Before
	public void init() {
    	this.trustSelfSignedSSL();
		conversionMasterHistEntityList = TestHelpers.getConversionMasterHistEntityList();
		billingAuditLogEntityList = TestHelpers.getBillingAuditLogEntityList();
		billingHistoryRequest = TestHelpers.getBillingHistoryRequest();
	}

    @After
    public void tearDown() throws Exception {
         
    }
    
    private IntuitTicketAuthentication getIntuitTicketAuthentication() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		IntuitTicketAuthentication principal = new IntuitTicketAuthentication(iamTicket, authorities);	
		return principal;
	}
    
    @Test
    public void getBillingHistoryServiceTest() {   	
    	BillingHistoryConversion billingHistoryConversion = billingHistoryService.getBillingHistory(billingHistoryRequest);
    	Assert.assertNotNull(billingHistoryConversion);
        
    }
    
    @Ignore
    @Test
	public void getBillingHistory_Success() throws Exception {
		IntuitTicketAuthentication principal = this.getIntuitTicketAuthentication();
		principal.setAuthenticated(true);
		BillingHistoryRequest billingHistoryRequest = TestHelpers.getBillingHistoryRequest();
    	BillingHistoryConversion billingHistoryConversion = billingHistoryController.getBillingHistory(principal, billingHistoryRequest);
    	Assert.assertEquals(BillingHistoryDocumentType.BASE_64.name(), billingHistoryConversion.getBillingHistoryDocumentType());
    	Assert.assertEquals(1, billingHistoryConversion.getBillingHistoryDocuments().size());
    	Assert.assertTrue(billingHistoryConversion.getBillingHistoryDocuments().get(0).getMetadata().getAmount()==75.0);
    	Assert.assertNotNull(billingHistoryConversion);
	}
    
    @Ignore
    @Test
	public void getBillingHistory_Failure() throws Exception {
		IntuitTicketAuthentication principal = this.getIntuitTicketAuthentication();
		principal.setAuthenticated(false);
		BillingHistoryRequest billingHistoryRequest = TestHelpers.getBillingHistoryRequest();
    	BillingHistoryConversion billingHistoryConversion = billingHistoryController.getBillingHistory(principal, billingHistoryRequest);
    	Assert.assertNotNull(billingHistoryConversion);
	}
    
    @Ignore
    @Test
	public void getBillingHistory_rest() throws Exception {
    	final String baseUrl = "https://localhost:"+port+"/v1/billing/billing_history/";
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");      
 
        HttpEntity<BillingHistoryRequest> request = new HttpEntity<>(billingHistoryRequest, headers);
         
        ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
        Assert.assertNotNull(result);
	}
}
