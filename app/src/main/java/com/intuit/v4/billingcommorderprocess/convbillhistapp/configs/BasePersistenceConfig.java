package com.intuit.v4.billingcommorderprocess.convbillhistapp.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public abstract class BasePersistenceConfig {

    @Value("${spring.jpa.database-platform}")
    private String hibernateDialect;

    protected DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    protected LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.intuit.v4.billingcommorderprocess.convbillhistapp.entities");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", hibernateDialect);
        em.setJpaPropertyMap(properties);
        return em;
    }
}
