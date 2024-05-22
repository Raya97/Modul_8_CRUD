package org.example;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static Database instance; // Статична змінна для зберігання єдиного екземпляру класу (singleton pattern)
    private DataSource dataSource;  // Зберігання джерела даних, що дозволяє гнучкість у використанні різних джерел

    // Приватний конструктор для запобігання створенню об'єктів класу ззовні
    private Database() {
        HikariConfig config = new HikariConfig(); // Створення конфігурації для пулу з'єднань HikariCP
        config.setJdbcUrl("jdbc:h2:~/testdb"); // Встановлення URL бази даних
        config.setUsername("sa"); // Встановлення імені користувача бази даних
        config.setPassword(""); // Встановлення пароля користувача бази даних

        dataSource = new HikariDataSource(config); // Створення джерела даних з конфігурацією
    }

    // Тестовий конструктор, що приймає DataSource. Це забезпечує можливість вставки мок-об'єктів у тестах.
    public Database(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Синхронізований метод для отримання єдиного екземпляру класу, що забезпечує потокобезпечність
    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database(); // Якщо екземпляр не існує, створюється новий
        }
        return instance;
    }

    // Метод для отримання з'єднання з базою даних
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection(); // Витяг з'єднання з пулу
    }

    // Геттер для отримання джерела даних
    public DataSource getDataSource() {
        return dataSource;
    }
}
