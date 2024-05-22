package org.example;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.flywaydb.core.Flyway;
import javax.sql.DataSource;

public class DatabasePopulateService {
    // Створення екземпляру логера для зберігання логів процесу
    private static final Logger logger = Logger.getLogger(DatabasePopulateService.class.getName());

    public static void main(String[] args) {
        try {
            // Отримання джерела даних з синглтону Database
            DataSource dataSource = Database.getInstance().getDataSource();

            // Конфігурація Flyway з джерелом даних та підготовка до міграції
            Flyway flyway = Flyway.configure().dataSource(dataSource).load();

            // Запуск міграції бази даних для оновлення схеми або наповнення даними
            flyway.migrate();

            // Виведення повідомлення про успішне заповнення таблиць даними
            System.out.println("Таблиці бази даних успішно заповнено даними.");
        } catch (Exception e) {
            // Логування винятку у разі виникнення помилок під час міграції
            logger.log(Level.SEVERE, "Помилка при наповненні таблиць бази даних", e);
        }
    }
}
