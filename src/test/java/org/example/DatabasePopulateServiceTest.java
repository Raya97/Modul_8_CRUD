package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.flywaydb.core.Flyway;
import javax.sql.DataSource;
import static org.mockito.Mockito.*;

public class DatabasePopulateServiceTest {
    // Mocks for DataSource and Flyway
    @Mock
    private DataSource dataSource;
    @Mock
    private Flyway flyway;

    // Variable to close resources after testing
    private AutoCloseable closeable;

    // Setup method to run before each test
    @BeforeEach
    void setUp() {
        // Initialize mocks using Mockito
        closeable = MockitoAnnotations.openMocks(this);
        // Configure Flyway to return a mocked instance when configure().dataSource(dataSource).load() is called
        when(Flyway.configure().dataSource(dataSource).load()).thenReturn(flyway);
    }

    // Tear down method to run after each test
    @AfterEach
    void tearDown() throws Exception {
        closeable.close(); // Close resources related to mocks
    }

    // Test to verify correct database population
    @Test
    void testDatabasePopulation() {
        // Call the method that initializes the database
        DatabasePopulateService.main(null);

        // Verify that the migrate() method was called on the Flyway instance
        verify(flyway).migrate();
    }
}
