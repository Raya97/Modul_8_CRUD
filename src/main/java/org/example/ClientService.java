package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicReference;

public class ClientService {
    // Зберігання з'єднання з базою даних в атомічній змінній для безпечного використання у багатопоточному середовищі
    private final AtomicReference<Connection> connectionRef;

    // Конструктор, який приймає з'єднання і зберігає його в атомічній змінній
    public ClientService(Connection connection) {
        this.connectionRef = new AtomicReference<>(connection);
    }

    // Метод для створення нового клієнта в базі даних
    public long create(String name) throws SQLException {
        String sql = "INSERT INTO client (name) VALUES (?)";
        Connection conn = connectionRef.get(); // Отримання з'єднання з базою
        try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name); // Встановлення значення параметра
            int affectedRows = statement.executeUpdate(); // Виконання запиту
            if (affectedRows == 0) {
                throw new SQLException("Створення клієнта не вдалося, жодного рядка не змінено.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1); // Повернення згенерованого ключа
                } else {
                    throw new SQLException("Створення клієнта не вдалося, ID не отримано.");
                }
            }
        }
    }


    // Метод для отримання імені клієнта за ID
    public String getById(long id) throws SQLException {
        String sql = "SELECT name FROM client WHERE id = ?";
        Connection conn = connectionRef.get();  // Отримуємо з'єднання
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

    // Метод для оновлення імені клієнта
    public void setName(long id, String name) throws SQLException {
        String sql = "UPDATE client SET name = ? WHERE id = ?";
        Connection conn = connectionRef.get();  // Отримуємо з'єднання
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setLong(2, id);
            statement.executeUpdate();
        }
    }

    // Метод для видалення клієнта за ID
    public void deleteById(long id) throws SQLException {
        String sql = "DELETE FROM client WHERE id = ?";
        Connection conn = connectionRef.get();  // Отримуємо з'єднання
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}






