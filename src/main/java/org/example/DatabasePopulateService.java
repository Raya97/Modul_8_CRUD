package org.example;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.flywaydb.core.Flyway;
import javax.sql.DataSource;

public class DatabasePopulateService {
    // Create a logger instance for logging the process
    private static final Logger logger = Logger.getLogger(DatabasePopulateService.class.getName());

    public static void main(String[] args) {
        try {
            // Obtain the data source from the Database singleton
            DataSource dataSource = Database.getInstance().getDataSource();

            // Configure Flyway with the data source and prepare for migration
            Flyway flyway = Flyway.configure().dataSource(dataSource).load();

            // Run the database migration to update the schema or populate data
            flyway.migrate();

            // Print a message indicating successful data population in the tables
            System.out.println("Database tables successfully populated with data.");
        } catch (Exception e) {
            // Log the exception in case of any errors during migration
            logger.log(Level.SEVERE, "Error while populating database tables", e);
        }
    }
}
