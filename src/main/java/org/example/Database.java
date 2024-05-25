package org.example;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static Database instance; // Static variable to store the singleton instance of the class
    private DataSource dataSource;  // Variable to store the data source, allowing flexibility in using different sources

    // Private constructor to prevent instantiation from outside the class
    private Database() {
        HikariConfig config = new HikariConfig(); // Creating configuration for HikariCP connection pool
        config.setJdbcUrl("jdbc:h2:~/testdb"); // Setting the database URL
        config.setUsername("sa"); // Setting the database username
        config.setPassword(""); // Setting the database password

        dataSource = new HikariDataSource(config); // Creating the data source with the configuration
    }

    // Test constructor that accepts a DataSource. This allows for injection of mock objects in tests.
    public Database(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Synchronized method to get the singleton instance of the class, ensuring thread safety
    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database(); // If the instance does not exist, create a new one
        }
        return instance;
    }

    // Method to get a connection from the database
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection(); // Retrieve a connection from the pool
    }

    // Getter to obtain the data source
    public DataSource getDataSource() {
        return dataSource;
    }
}
