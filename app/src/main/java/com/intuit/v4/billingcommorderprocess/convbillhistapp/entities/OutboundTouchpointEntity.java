package com.intuit.v4.billingcommorderprocess.convbillhistapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "OUTBOUND_TOUCHPOINTS", schema = "CRM_DATA")
public class OutboundTouchpointEntity {

    @Id
    @Column(name = "OT_ID")
    private Long id;

    @Column(name = "OT_DESCRIPTION")
    private String description;

    @Column(name = "OT_LANGUAGE")
    private String language;

    @Column(name = "OT_MESSAGE_BODY")
    @Lob
    private String messageBody;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "OT_TCH_ID", referencedColumnName = "TCH_ID")
    private TouchpointEntity touchpoint;
}
