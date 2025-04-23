package com.springcamp.testing.springcamp_testing.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public abstract class AbstractionBaseTest {

    static final MySQLContainer MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer("mysql:8.0.27").withUsername("testuser").withPassword("testpass").withDatabaseName("ems");

        MY_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl); // Get dynamic URL
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername); // Get username
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword); // Get password
    }
}