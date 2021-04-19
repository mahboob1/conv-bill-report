package com.intuit.v4.billingcommorderprocess.convbillhistapp.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ConversionMasterHistPK implements Serializable {
    private long batchId;
    private long subId;
}
