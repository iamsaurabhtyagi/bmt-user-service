package com.bmt.user.service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class PrimaryDataSource {

    /*@Primary
    @Bean(name = "bmtProperties")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "bmtDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource dataSource(@Qualifier("bmtProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }


    @Bean(name = "dmtJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("bmtDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }*/
}
