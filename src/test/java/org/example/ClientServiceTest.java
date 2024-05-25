package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // Use the Mockito extension to integrate with JUnit 5
class ClientServiceTest {

    @Mock
    private Connection connection; // Mock Connection object to simulate database connection

    @Mock
    private PreparedStatement preparedStatement; // Mock PreparedStatement object to simulate SQL queries

    @Mock
    private ResultSet resultSet; // Mock ResultSet object to simulate SQL query results

    @InjectMocks
    private ClientService clientService; // Inject the mocked Connection into the ClientService class

    @BeforeEach
    void setUp() throws SQLException {
        // Configure the behavior of the mocked Connection to return a PreparedStatement when prepareStatement is called
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        // Configure the mocked PreparedStatement to return a ResultSet when executeQuery is called
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        // Configure the mocked PreparedStatement to return a ResultSet for generated keys
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
    }

    @Test
    void create() throws SQLException {
        // Configure the ResultSet to return a generated key
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L); // Simulate returning a generated key

        String name = "New Client";
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate successful update execution

        long clientId = clientService.create(name); // Call the create method and check its behavior

        verify(preparedStatement).setString(1, name); // Verify that setString was called with the correct parameter
        verify(preparedStatement).executeUpdate(); // Verify that executeUpdate was called
        verify(preparedStatement).getGeneratedKeys(); // Verify that getGeneratedKeys was called
        assertEquals(1L, clientId, "The returned client ID should be 1"); // Verify that the returned client ID is correct
    }

    @Test
    void getById() throws SQLException {
        String expectedName = "Existing Client";
        // Configure the ResultSet to return a client name
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("name")).thenReturn(expectedName);

        String name = clientService.getById(1L); // Call the getById method

        verify(preparedStatement).setLong(1, 1L); // Verify that setLong was called with the correct parameter
        verify(preparedStatement).executeQuery(); // Verify that executeQuery was called
        assertEquals(expectedName, name, "The retrieved name should match the expected name"); // Verify that the retrieved name is correct
    }

    @Test
    void setName() throws SQLException {
        clientService.setName(1L, "Updated Client"); // Call the setName method

        verify(preparedStatement).setString(1, "Updated Client"); // Verify that setString was called with the correct parameter
        verify(preparedStatement).setLong(2, 1L); // Verify that setLong was called with the correct parameter
        verify(preparedStatement).executeUpdate(); // Verify that executeUpdate was called
    }

    @Test
    void deleteById() throws SQLException {
        clientService.deleteById(1L); // Call the deleteById method

        verify(preparedStatement).setLong(1, 1L); // Verify that setLong was called with the correct parameter
        verify(preparedStatement).executeUpdate(); // Verify that executeUpdate was called
    }

    @Test
    void listAll() throws SQLException {
        // Configure the ResultSet to simulate two records
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getLong("id")).thenReturn(1L, 2L);
        when(resultSet.getString("name")).thenReturn("Client 1", "Client 2");

        List<ClientService.Client> clients = clientService.listAll(); // Call the listAll method

        assertEquals(2, clients.size(), "The list should contain 2 clients"); // Verify the size of the list
        assertEquals(1L, clients.get(0).getId(), "The ID of the first client should be 1"); // Verify the ID of the first client
        assertEquals("Client 1", clients.get(0).getName(), "The name of the first client should be 'Client 1'"); // Verify the name of the first client
        assertEquals(2L, clients.get(1).getId(), "The ID of the second client should be 2"); // Verify the ID of the second client
        assertEquals("Client 2", clients.get(1).getName(), "The name of the second client should be 'Client 2'"); // Verify the name of the second client

        verify(preparedStatement).executeQuery(); // Verify that executeQuery was called
    }
}
