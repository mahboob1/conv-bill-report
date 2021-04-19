package com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories;

import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.ConversionMasterHistEntity;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.entities.ConversionMasterHistPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversionMasterHistRepository extends JpaRepository<ConversionMasterHistEntity, ConversionMasterHistPK> {
    List<ConversionMasterHistEntity> findByRealmId(String realmId);
}
