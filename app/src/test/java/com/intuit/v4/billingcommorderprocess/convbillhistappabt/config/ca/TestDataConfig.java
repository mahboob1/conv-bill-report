package com.intuit.v4.billingcommorderprocess.convbillhistappabt.config.ca;

import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.BillingAuditLogRepository;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.ConversionMasterHistRepository;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.OutboundTouchpointRepository;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.ca.CaBillingAuditLogRepository;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.ca.CaConversionMasterHistRepository;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.ca.CaOutboundTouchpointRepository;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.uk.UkBillingAuditLogRepository;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.uk.UkConversionMasterHistRepository;
import com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.uk.UkOutboundTouchpointRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = {
                "com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories"
        },
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                		BillingAuditLogRepository.class,
                		ConversionMasterHistRepository.class,
                		OutboundTouchpointRepository.class,
                		CaBillingAuditLogRepository.class,
                		CaConversionMasterHistRepository.class,
                		CaOutboundTouchpointRepository.class,
                		UkBillingAuditLogRepository.class,
                		UkConversionMasterHistRepository.class,
                		UkOutboundTouchpointRepository.class
                })
        }
)
public class TestDataConfig {

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                       JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setPackagesToScan(
                "com.intuit.v4.billingcommorderprocess.convbillhistapp.entities"
        );
        return entityManagerFactory;
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:jdbc/schemas.sql")
                .addScript("classpath:jdbc/data.sql")
                .build();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.H2);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.Oracle10gDialect");
        return jpaVendorAdapter;
    }
}

