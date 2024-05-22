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
    // Поле для збереження з'єднання з базою даних
    private final Connection connection;
    // Логер для реєстрації подій та помилок
    private static final Logger logger = Logger.getLogger(DatabaseQueryService.class.getName());

    // Конструктор, який приймає з'єднання бази даних
    public DatabaseQueryService(Connection connection) {
        this.connection = connection;
    }

    // Метод для пошуку клієнтів по ідентифікатору
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

    // Метод для пошуку проектів за ID клієнта
    public List<MaxProjectCountClient> findMaxProjectsProject(int yourConditionValue) {
        List<MaxProjectCountClient> result = new ArrayList<>();

        // SQL-запит для проектів
        String sqlForProject = "SELECT ID, CLIENT_ID, START_DATE, FINISH_DATE FROM project WHERE CLIENT_ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlForProject)) {
            preparedStatement.setInt(1, yourConditionValue);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MaxProjectCountClient client = new MaxProjectCountClient();
                client.setName(resultSet.getString("name"));
                client.setProjectCount(resultSet.getInt("project_count"));
                result.add(client);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Помилка при вибірці даних з таблиці project", e);
        }

        return result;
    }

    // Метод для пошуку працівників за ID проекту
    public List<MaxProjectCountClient> findMaxProjectsProjectWorker(int yourConditionValue) {
        List<MaxProjectCountClient> result = new ArrayList<>();

        // SQL-запит для project_worker
        String sqlForProjectWorker = "SELECT PROJECT_ID, WORKER_ID FROM project_worker WHERE WORKER_ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlForProjectWorker)) {
            preparedStatement.setInt(1, yourConditionValue);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MaxProjectCountClient client = new MaxProjectCountClient();
                client.setName(resultSet.getString("name"));
                client.setProjectCount(resultSet.getInt("project_count"));
                result.add(client);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Помилка при вибірці даних з таблиці project_worker", e);
        }

        return result;
    }

    // Метод для пошуку працівників за ID працівника
    public List<MaxProjectCountClient> findMaxProjectsWorker(int yourConditionValue) {
        List<MaxProjectCountClient> result = new ArrayList<>();

        // SQL-запит для worker
        String sqlForWorker = "SELECT ID, NAME, BIRTHDAY, LEVEL, SALARY FROM worker WHERE ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlForWorker)) {
            preparedStatement.setInt(1, yourConditionValue);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MaxProjectCountClient client = new MaxProjectCountClient();
                client.setName(resultSet.getString("name"));
                client.setProjectCount(resultSet.getInt("project_count"));
                result.add(client);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Помилка при вибірці даних з таблиці worker", e);
        }

        return result;
    }


    public static void main(String[] args) {
        try {
            // Спочатку отримуємо з'єднання з базою даних
            Connection connection = Database.getInstance().getConnection();
            DatabaseQueryService queryService = new DatabaseQueryService(connection);

            // Виклик методів для пошуку клієнтів, проектів, project_worker та працівників
            List<MaxProjectCountClient> clients = queryService.findMaxProjectsClient(42);
            List<MaxProjectCountClient> projects = queryService.findMaxProjectsProject(42);
            List<MaxProjectCountClient> projectWorkers = queryService.findMaxProjectsProjectWorker(42);
            List<MaxProjectCountClient> workers = queryService.findMaxProjectsWorker(42);


            // Виведення результату
            for (MaxProjectCountClient client : clients) {
                System.out.println("Ім'я клієнта: " + client.getName());
                System.out.println("Кількість проектів: " + client.getProjectCount());
                System.out.println("-----------------------------");
            }

            for (MaxProjectCountClient project : projects) {
                System.out.println("ID проекту: " + project.getId());
                System.out.println("Клієнт ID: " + project.getClientId());
                System.out.println("Дата початку: " + project.getStartDate());
                System.out.println("Дата завершення: " + project.getFinishDate());
                System.out.println("-----------------------------");
            }

            for (MaxProjectCountClient projectWorker : projectWorkers) {
                System.out.println("ID проекту: " + projectWorker.getProjectId());
                System.out.println("ID працівника: " + projectWorker.getWorkerId());
                System.out.println("-----------------------------");
            }

            for (MaxProjectCountClient worker : workers) {
                System.out.println("ID працівника: " + worker.getId());
                System.out.println("Ім'я працівника: " + worker.getName());
                System.out.println("Дата народження: " + worker.getBirthday());
                System.out.println("Рівень: " + worker.getLevel());
                System.out.println("Зарплата: " + worker.getSalary());
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            // Обробка винятку, якщо з'єднання з базою даних не було успішним
            throw new RuntimeException("Не вдалося отримати з'єднання з базою даних", e);
        }
    }
}
