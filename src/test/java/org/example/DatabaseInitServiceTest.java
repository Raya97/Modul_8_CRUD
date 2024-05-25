package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class DatabaseInitServiceTest {

    @Test
    public void testDatabaseInitialization() {
        HikariConfig config = new HikariConfig();
        // Configure the HikariCP connection pool to use the test H2 database
        config.setJdbcUrl("jdbc:h2:~/Modul6"); // Your JDBC URL
        config.setUsername("Raisa"); // Database username
        config.setPassword(""); // Database password

        // Using try-with-resources to ensure the HikariDataSource is closed after use
        try (HikariDataSource ds = new HikariDataSource(config)) {
            // Configure Flyway with the data source
            Flyway flyway = Flyway.configure().dataSource(ds).load();
            // Start the database migration
            flyway.migrate();

            // Assert that the migration was successful
            Assertions.assertTrue(flyway.info().current().getVersion().toString().startsWith("1"), "Migration was not successful");
            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            // If an exception occurs, the test will fail with the provided message
            Assertions.fail("Failed to initialize the database: " + e.getMessage());
        }
    }
}
