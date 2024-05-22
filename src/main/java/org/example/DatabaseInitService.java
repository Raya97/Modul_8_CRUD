package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

public class DatabaseInitService {

    public static void main(String[] args) {
        // Створення об'єкта конфігурації для HikariCP
        HikariConfig config = new HikariConfig();

        // Встановлення JDBC URL для підключення до бази даних H2
        config.setJdbcUrl("jdbc:h2:~/Modul6");

        // Встановлення імені користувача для доступу до бази даних
        config.setUsername("Raisa");

        // Встановлення пароля для доступу до бази даних
        config.setPassword("");

        // Створення джерела даних з використанням конфігурації HikariCP
        try (HikariDataSource ds = new HikariDataSource(config)) {
            // Конфігурація та ініціалізація Flyway для управління міграціями бази даних
            Flyway flyway = Flyway.configure().dataSource(ds).load();

            // Запуск міграції бази даних
            flyway.migrate();

            // Виведення повідомлення про успішну ініціалізацію бази даних
            System.out.println("Базу даних ініціалізовано успішно.");
        }
    }
}

