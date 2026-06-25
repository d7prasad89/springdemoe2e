package com.springdemoe2e.config;

import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TestInfrastructureConfig {

    public TestInfrastructureConfig(DataSource dataSource) {
    }

}
