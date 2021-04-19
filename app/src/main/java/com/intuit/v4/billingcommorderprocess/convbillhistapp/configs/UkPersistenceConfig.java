package com.intuit.v4.billingcommorderprocess.convbillhistapp.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.uk",
        entityManagerFactoryRef = "ukEntityManagerFactory"
)
public class UkPersistenceConfig extends BasePersistenceConfig {

    @Bean
    @ConfigurationProperties(prefix="spring.datasource-uk")
    public DataSource ukDataSource() {
        return dataSource();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean ukEntityManagerFactory(DataSource ukDataSource) {
        return entityManagerFactory(ukDataSource);
    }
}
