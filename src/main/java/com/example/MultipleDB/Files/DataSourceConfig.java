package com.example.MultipleDB.Files;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource db1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource db2DataSource() {
        return DataSourceBuilder.create().build();
    }

 

    @Bean
    public DataSource dynamicDataSource(
            @Qualifier("db1DataSource") DataSource db1DataSource,
            @Qualifier("db2DataSource") DataSource db2DataSource
          ) {

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DbType.DB1, db1DataSource);
        targetDataSources.put(DbType.DB2, db2DataSource);

        MultiRoutingDataSource routingDataSource = new MultiRoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(db1DataSource);
        routingDataSource.setDefaultTargetDataSource(db2DataSource);

        routingDataSource.setTargetDataSources(targetDataSources);

        return routingDataSource;
    }





    
}
