package com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.controllers;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.intuit.platform.integration.hats.common.IAMTicket;
import com.intuit.platform.jsk.security.iam.authn.IntuitTicketAuthentication;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.TestHelpers;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryConversion;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.models.BillingHistoryRequest;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.services.BillingHistoryService;

	@SpringBootTest
	@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
	@EnableConfigurationProperties
	@ContextConfiguration(classes = BillingHistoryController.class)
	@RunWith(SpringRunner.class)
	@ActiveProfiles("test")
	public class BillingHistoryControllerTest {
	 
		@Autowired
		private BillingHistoryController billingHistoryController;

		@MockBean
		private BillingHistoryService billingHistoryService;
		
		@MockBean
		IAMTicket iamTicket;
		
		private IntuitTicketAuthentication getIntuitTicketAuthentication() {
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			IntuitTicketAuthentication principal = new IntuitTicketAuthentication(iamTicket, authorities);	
			return principal;
		}
    
		@Test
		public void getBillingHistory_Success() throws Exception {
			IntuitTicketAuthentication principal = this.getIntuitTicketAuthentication();
			principal.setAuthenticated(true);
			BillingHistoryRequest billingHistoryRequest = TestHelpers.getBillingHistoryRequest();
        	BillingHistoryConversion BillingHistoryConversion = billingHistoryController.getBillingHistory(principal, billingHistoryRequest);
        	assertNull(BillingHistoryConversion);
        	verify(billingHistoryService, times(1))
                .getBillingHistory(billingHistoryRequest);
		}

		@Test
		public void getBillingHistory_Failure() throws Exception {
			IntuitTicketAuthentication principal = this.getIntuitTicketAuthentication();
			principal.setAuthenticated(false);
			BillingHistoryRequest billingHistoryRequest = TestHelpers.getBillingHistoryRequest();
        	BillingHistoryConversion BillingHistoryConversion = billingHistoryController.getBillingHistory(principal, billingHistoryRequest);
        	assertNull(BillingHistoryConversion);
        	verify(billingHistoryService, times(1))
                .getBillingHistory(billingHistoryRequest);
		}
	}