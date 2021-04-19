/**
 * Copyright 2015-2020 Intuit Inc. All rights reserved. Unauthorized reproduction
 * is a violation of applicable law. This material contains certain
 * confidential or proprietary information and trade secrets of Intuit Inc.
 */
package com.intuit.v4.billingcommorderprocess.convbillhistapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class ConvBillHistApplication {

    public static ConfigurableApplicationContext run(String[] args) {
        return SpringApplication.run(ConvBillHistApplication.class, args);
    }

    public static void main(String[] args) {
        run(args);  //NOSONAR
    }
}
