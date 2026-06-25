package com.springdemoe2e.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class H2DatabaseIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void testH2DatabaseConnection() throws Exception {
        assertNotNull(dataSource, "DataSource should be injected");
        
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection, "Connection should be established");
            
            DatabaseMetaData metaData = connection.getMetaData();
            String databaseProductName = metaData.getDatabaseProductName();
            String databaseVersion = metaData.getDatabaseMajorVersion() + "." + metaData.getDatabaseMinorVersion();
            
            assertEquals("H2", databaseProductName, "Should be using H2 database");
            System.out.println("✓ Database: " + databaseProductName);
            System.out.println("✓ Version: " + databaseVersion);
        }
    }

    @Test
    void testH2InMemoryDatabase() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            String url = connection.getMetaData().getURL();
            assertTrue(url.contains("mem:testdb"), "Should be using in-memory database");
            System.out.println("✓ Database URL: " + url);
        }
    }

    @Test
    void testH2SchemaCreation() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            
            // Get all tables in the database
            var resultSet = metaData.getTables(null, "PUBLIC", null, new String[]{"TABLE"});
            int tableCount = 0;
            
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                if (!tableName.startsWith("INFORMATION_SCHEMA")) {
                    tableCount++;
                    System.out.println("✓ Found table: " + tableName);
                }
            }
            
            assertTrue(tableCount > 0, "Should have created at least one table");
        }
    }
}

