package com.netcracker.configuration;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public DataSource getDataSource(){
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
        dataSource.setUsername("weather");
        dataSource.setPassword("1234");
        dataSource.setInitialSize(30);
        dataSource.setMaxActive(50);
        dataSource.setMaxIdle(30);
        return dataSource;
    }
}
