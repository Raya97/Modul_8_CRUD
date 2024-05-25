package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ClientService {
    // Store the database connection in an atomic reference for thread-safe usage
    private final AtomicReference<Connection> connectionRef;

    // Constructor that accepts a connection and stores it in the atomic reference
    public ClientService(Connection connection) {
        this.connectionRef = new AtomicReference<>(connection);
    }

    // Method to create a new client in the database
    public long create(String name) throws SQLException {
        String sql = "INSERT INTO client (name) VALUES (?)";
        Connection conn = connectionRef.get(); // Get the database connection
        try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name); // Set the parameter value
            int affectedRows = statement.executeUpdate(); // Execute the query
            if (affectedRows == 0) {
                throw new SQLException("Creating client failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1); // Return the generated key
                } else {
                    throw new SQLException("Creating client failed, no ID obtained.");
                }
            }
        }
    }

    // Method to get the name of a client by ID
    public String getById(long id) throws SQLException {
        String sql = "SELECT name FROM client WHERE id = ?";
        Connection conn = connectionRef.get();  // Get the connection
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            } else {
                return null;
            }
        }
    }

    // Method to update the name of a client
    public void setName(long id, String name) throws SQLException {
        String sql = "UPDATE client SET name = ? WHERE id = ?";
        Connection conn = connectionRef.get();  // Get the connection
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setLong(2, id);
            statement.executeUpdate();
        }
    }

    // Method to delete a client by ID
    public void deleteById(long id) throws SQLException {
        String sql = "DELETE FROM client WHERE id = ?";
        Connection conn = connectionRef.get();  // Get the connection
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    // Method to get a list of all clients
    public List<Client> listAll() throws SQLException {
        String sql = "SELECT id, name FROM client";
        Connection conn = connectionRef.get();  // Get the connection
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Client> clients = new ArrayList<>();
            while (resultSet.next()) {
                Client client = new Client(resultSet.getLong("id"), resultSet.getString("name"));
                clients.add(client);
            }
            return clients;
        }
    }

    // Inner class representing a client
    public static class Client {
        private long id;
        private String name;

        public Client(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Client{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
