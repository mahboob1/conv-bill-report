package com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories;

import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.OutboundTouchpointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface OutboundTouchpointRepository extends JpaRepository<OutboundTouchpointEntity, Long> {
    List<OutboundTouchpointEntity> findByCreateDateAfterAndCreateDateBeforeAndTouchpointPartyIdInAndDescriptionLikeIgnoreCaseOrderByCreateDateAsc(Date startDate, Date endDate, Collection<Long> partyIds, String pattern);
}
