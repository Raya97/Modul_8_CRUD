package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

public class DatabaseInitService {

    public static void main(String[] args) {
        // Create HikariCP configuration object
        HikariConfig config = new HikariConfig();

        // Set the JDBC URL for connecting to the H2 database
        config.setJdbcUrl("jdbc:h2:~/Modul6");

        // Set the username for accessing the database
        config.setUsername("Raisa");

        // Set the password for accessing the database
        config.setPassword("");

        // Create a data source using the HikariCP configuration
        try (HikariDataSource ds = new HikariDataSource(config)) {
            // Configure and initialize Flyway for managing database migrations
            Flyway flyway = Flyway.configure().dataSource(ds).load();

            // Run the database migration
            flyway.migrate();

            // Print a message indicating successful database initialization
            System.out.println("Database initialized successfully.");
        }
    }
}
