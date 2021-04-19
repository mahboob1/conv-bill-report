//package com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories;
//
//import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.OutboundTouchpointEntity;
//import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.OutboundTouchpointRepository;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.math.BigInteger;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({ "classpath:spring/appContextTest.xml" })
//public class OutboundTouchpointRepositoryTest {
//
//    @Autowired
//    private OutboundTouchpointRepository outboundTouchpointRepository;
//
//    @Test
//    public void test_findByEntitledProductFeatureEntitledProductFeatureIdAndProcessingStateIn() {
//        BigInteger entitledProductFeatureId = BigInteger.valueOf(2345);
//        String processingStatus = "COMPLETE";
//        String datePattern = "yyyy-MM-dd";
//        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
//        //SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
//        Date startDate = null;;
//        Date endDate = null;
//        try {
//        String dateInString = "2015-05-04";
////        Date startDate = new Date("2015-05-04");
////        Date endDate = new Date("2020-05-04"); 
//        startDate = sdf.parse(dateInString);
//        dateInString = "2020-05-04";
//        endDate = sdf.parse(dateInString);
//        } catch (Exception e) {
//        	
//        }
//        Collection<Long> partyIds = new ArrayList<>();
//        partyIds.add(001l);
//        partyIds.add(002l);
//        partyIds.add(003l);
//        String pattern = "%BILL%";
//
//        List<OutboundTouchpointEntity> entities = outboundTouchpointRepository
//                .findByCreateDateAfterAndCreateDateBeforeAndTouchpointPartyIdInAndDescriptionLikeIgnoreCaseOrderByCreateDateAsc(startDate,
//                		endDate, partyIds, pattern);
//
//        //Assert.assertEquals(1, entities.size());
////        Assert.assertEquals(entitledProductFeatureId, entities.get(0).getEntitledProductFeature().getEntitledProductFeatureId());
////        Assert.assertEquals(processingStatus, entities.get(0).getProcessingState());
//    }
//
//}
