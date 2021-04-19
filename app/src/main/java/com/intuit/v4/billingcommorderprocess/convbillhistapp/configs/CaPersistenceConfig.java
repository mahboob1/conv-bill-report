package com.intuit.v4.billingcommorderprocess.convbillhistapp.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.intuit.v4.billingcommorderprocess.convbillhistapp.repositories.ca",
        entityManagerFactoryRef = "caEntityManagerFactory"
)
public class CaPersistenceConfig extends BasePersistenceConfig {

    @Bean
    @ConfigurationProperties(prefix="spring.datasource-ca")
    public DataSource caDataSource() {
        return dataSource();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean caEntityManagerFactory(DataSource caDataSource) {
        return entityManagerFactory(caDataSource);
    }
}
