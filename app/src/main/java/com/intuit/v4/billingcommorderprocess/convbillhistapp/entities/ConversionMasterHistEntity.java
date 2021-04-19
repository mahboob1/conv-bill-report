package com.intuit.v4.billingcommorderprocess.convbillhistapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "OBILL_CONV_MSTR_HIST", schema = "IDBS_QBDT_CNV")
@IdClass(ConversionMasterHistPK.class)
public class ConversionMasterHistEntity {

    @Id
    @Column(name = "BATCH_ID")
    private Long batchId;

    @Id
    @Column(name = "SUB_ID")
    private Long subId;

    @Column(name = "REALM_ID")
    private String realmId;
    
    @Column(name = "PTY_ID")
    private String partyId;
}
