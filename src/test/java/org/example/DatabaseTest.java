package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseTest {

    @Mock
    private DataSource dataSource; // Mock object for DataSource to simulate the behavior of getting a connection

    @Mock
    private Connection connection; // Mock object for Connection to simulate database connection

    private AutoCloseable closeable; // AutoCloseable resource for managing mock objects lifecycle

    @BeforeEach
    void setUp() throws SQLException {
        // Initialize mocks before each test
        closeable = MockitoAnnotations.openMocks(this);
        // Configure the mock DataSource to return the mock Connection when getConnection() is called
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @AfterEach
    void tearDown() throws Exception {
        // Close resources related to mocks after each test
        closeable.close();
    }

    @Test
    public void testGetConnection() throws SQLException {
        // Create an instance of Database with the mock DataSource
        Database db = new Database(dataSource);
        // Call the method getConnection() which we want to test
        Connection conn = db.getConnection();

        // Verify that the result is not null
        assertNotNull(conn);
        // Verify that the getConnection() method of the DataSource was called
        verify(dataSource).getConnection();
    }
}
