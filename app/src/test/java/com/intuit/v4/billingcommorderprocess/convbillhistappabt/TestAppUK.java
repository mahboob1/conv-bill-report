package com.intuit.v4.billingcommorderprocess.convbillhistappabt;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.intuit.v4.billingcommorderprocess.convbillhistappabt.config.uk",
         
        "com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.services",
        "com.intuit.v4.billingcommorderprocess.convbillhistapp.rest.controllers"
}
)
public class TestAppUK {

}
