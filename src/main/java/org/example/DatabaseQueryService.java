package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseQueryService {
    // Field to store the database connection
    private final Connection connection;
    // Logger for logging events and errors
    private static final Logger logger = Logger.getLogger(DatabaseQueryService.class.getName());

    // Constructor that accepts a database connection
    public DatabaseQueryService(Connection connection) {
        this.connection = connection;
    }

    // Method to find clients by their ID
    public List<MaxProjectCountClient> findMaxProjectsClient(int clientId) throws SQLException {
        List<MaxProjectCountClient> result = new ArrayList<>();
        String sql = "SELECT NAME, ID FROM client WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MaxProjectCountClient client = new MaxProjectCountClient();
                client.setName(resultSet.getString("name"));
                client.setId(resultSet.getInt("id"));
                result.add(client);
            }
        }
        return result;
    }

    // Method to find projects by client ID
    public List<MaxProjectCountClient> findMaxProjectsProject(int clientId) {
        List<MaxProjectCountClient> result = new ArrayList<>();

        // SQL query for projects
        String sqlForProject = "SELECT ID, CLIENT_ID, START_DATE, FINISH_DATE FROM project WHERE CLIENT_ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlForProject)) {
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MaxProjectCountClient client = new MaxProjectCountClient();
                client.setName(resultSet.getString("name"));
                client.setProjectCount(resultSet.getInt("project_count"));
                result.add(client);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error selecting data from project table", e);
        }

        return result;
    }

    // Method to find workers by project ID
    public List<MaxProjectCountClient> findMaxProjectsProjectWorker(int workerId) {
        List<MaxProjectCountClient> result = new ArrayList<>();

        // SQL query for project_worker
        String sqlForProjectWorker = "SELECT PROJECT_ID, WORKER_ID FROM project_worker WHERE WORKER_ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlForProjectWorker)) {
            preparedStatement.setInt(1, workerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MaxProjectCountClient client = new MaxProjectCountClient();
                client.setName(resultSet.getString("name"));
                client.setProjectCount(resultSet.getInt("project_count"));
                result.add(client);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error selecting data from project_worker table", e);
        }

        return result;
    }

    // Method to find workers by their ID
    public List<MaxProjectCountClient> findMaxProjectsWorker(int workerId) {
        List<MaxProjectCountClient> result = new ArrayList<>();

        // SQL query for worker
        String sqlForWorker = "SELECT ID, NAME, BIRTHDAY, LEVEL, SALARY FROM worker WHERE ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlForWorker)) {
            preparedStatement.setInt(1, workerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MaxProjectCountClient client = new MaxProjectCountClient();
                client.setName(resultSet.getString("name"));
                client.setProjectCount(resultSet.getInt("project_count"));
                result.add(client);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error selecting data from worker table", e);
        }

        return result;
    }

    public static void main(String[] args) {
        try {
            // First, get a connection to the database
            Connection connection = Database.getInstance().getConnection();
            DatabaseQueryService queryService = new DatabaseQueryService(connection);

            // Call methods to find clients, projects, project_worker, and workers
            List<MaxProjectCountClient> clients = queryService.findMaxProjectsClient(42);
            List<MaxProjectCountClient> projects = queryService.findMaxProjectsProject(42);
            List<MaxProjectCountClient> projectWorkers = queryService.findMaxProjectsProjectWorker(42);
            List<MaxProjectCountClient> workers = queryService.findMaxProjectsWorker(42);

            // Print the results
            for (MaxProjectCountClient client : clients) {
                System.out.println("Client name: " + client.getName());
                System.out.println("Project count: " + client.getProjectCount());
                System.out.println("-----------------------------");
            }

            for (MaxProjectCountClient project : projects) {
                System.out.println("Project ID: " + project.getId());
                System.out.println("Client ID: " + project.getClientId());
                System.out.println("Start date: " + project.getStartDate());
                System.out.println("End date: " + project.getFinishDate());
                System.out.println("-----------------------------");
            }

            for (MaxProjectCountClient projectWorker : projectWorkers) {
                System.out.println("Project ID: " + projectWorker.getProjectId());
                System.out.println("Worker ID: " + projectWorker.getWorkerId());
                System.out.println("-----------------------------");
            }

            for (MaxProjectCountClient worker : workers) {
                System.out.println("Worker ID: " + worker.getId());
                System.out.println("Worker name: " + worker.getName());
                System.out.println("Birthday: " + worker.getBirthday());
                System.out.println("Level: " + worker.getLevel());
                System.out.println("Salary: " + worker.getSalary());
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            // Handle exception if connection to the database was not successful
            throw new RuntimeException("Failed to get a connection to the database", e);
        }
    }
}
