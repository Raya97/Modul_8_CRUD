package org.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DatabaseQueryServiceTest {
    // Mocks for Connection, PreparedStatement, and ResultSet
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    // Variable to close resources after testing
    private AutoCloseable closeable;
    // Instance of the class being tested
    private DatabaseQueryService queryService;

    // Setup method to run before each test
    @BeforeEach
    void setUp() throws SQLException {
        // Initialize mocks using Mockito
        closeable = MockitoAnnotations.openMocks(this);
        // Configure the mock PreparedStatement to return the mock ResultSet when executeQuery is called
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        // Initialize the instance of the class being tested with the mock connection
        queryService = new DatabaseQueryService(connection);
    }

    // Tear down method to run after each test
    @AfterEach
    void tearDown() throws Exception {
        closeable.close(); // Close resources related to mocks
    }

    // Test method for findMaxProjectsClient
    @Test
    void testFindMaxProjectsClient() throws SQLException {
        // Configure the mock ResultSet to return specified values
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("name")).thenReturn("Test Client");
        when(resultSet.getInt("id")).thenReturn(10);

        // Call the method being tested
        var results = queryService.findMaxProjectsClient(1);

        // Verify that the result is not null
        assertNotNull(results);
        // Verify that the methods for preparing and executing the query were called
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();
        // Verify that the methods for retrieving values from the ResultSet were called the specified number of times
        verify(resultSet, times(1)).getString("name");
        verify(resultSet, times(1)).getInt("id");
    }
}
