package com.intuit.v4.billingcommorderprocess.convbillhistapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "TOUCHPOINTS", schema = "CRM_DATA")
public class TouchpointEntity {

    @Id
    @Column(name = "TCH_ID")
    private Long id;

    @Column(name = "TCH_PTY_ID")
    private Long partyId;
}
