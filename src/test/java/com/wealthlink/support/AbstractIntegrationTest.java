package com.wealthlink.support;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Base class for repository-layer integration tests (Day 2 deliverable per
 * the architecture doc: "Repository-layer tests per module using
 * Testcontainers + real MySQL, not H2 - need real DECIMAL precision
 * and constraint behavior").
 * <p>
 * The MySQL container is declared once here as a static field. Because
 * static fields are shared across the class hierarchy in the JVM, every
 * subclass reuses the SAME running container instead of each test class
 * starting its own - this is the standard Testcontainers "singleton
 * container" pattern and keeps the full suite fast.
 */
@Testcontainers
@SpringBootTest
public abstract class AbstractIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("wealth_link")
            .withUsername("weatlh")
            .withPassword("Weatlh@123");

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
