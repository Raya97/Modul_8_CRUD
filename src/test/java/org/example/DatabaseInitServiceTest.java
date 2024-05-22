package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class DatabaseInitServiceTest {

    @Test
    public void testDatabaseInitialization() {
        HikariConfig config = new HikariConfig();
        // Використання тестової H2 бази даних
        config.setJdbcUrl("jdbc:h2:~/Modul6"); // Ваш JDBC URL
        config.setUsername("Raisa"); // Ім'я користувача
        config.setPassword(""); // Пароль

        try (HikariDataSource ds = new HikariDataSource(config)) {
            Flyway flyway = Flyway.configure().dataSource(ds).load();
            // Запуск міграції
            flyway.migrate();

            // Перевірка, що міграція пройшла успішно
            Assertions.assertTrue(flyway.info().current().getVersion().toString().startsWith("1"), "Міграція не пройшла успішно");
            System.out.println("Базу даних ініціалізовано успішно.");
        } catch (Exception e) {
            Assertions.fail("Не вдалося ініціалізувати базу даних: " + e.getMessage());
        }
    }
}
