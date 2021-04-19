package com.intuit.v4.billingcommorderprocess.convbillhistapp.models;


import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "intuit-triggered-email")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoicePayload {
    @XmlElement(name = "recipients")
    private List<Recipient> recipients;

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Recipient {
        @XmlElement(name = "name-value-pairs")
        private List<Property> properties;
    }

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Property {
        private String name;
        private String value;
    }
}
