package com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories;

import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.BillingAuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface BillingAuditLogRepository extends JpaRepository<BillingAuditLogEntity, Long> {
    List<BillingAuditLogEntity> findBySubIdInAndCreateDateAfterAndCreateDateBefore(Collection<Long> subIds,
                                                                                   Date startDate, Date endDate);
}
