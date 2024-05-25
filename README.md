# Modul_8_CRUD
Java проект для управління клієнтами та проектами, який використовує H2 базу даних та фреймворк для міграцій Flyway. Проект реалізує основні CRUD (Create, Read, Update, Delete) операції для сутності Client, а також забезпечує ініціалізацію та заповнення бази даних за допомогою міграцій.

## Функціональні можливості
- **Управління клієнтами:** Виконання CRUD операцій для сутності `Client`.
- **Ініціалізація бази даних:** Ініціалізація схеми бази даних за допомогою міграцій Flyway.
- **Наповнення бази даних:** Наповнення бази даних початковими даними.
- **Інтеграція з H2:** Використання бази даних H2 для розробки та тестування.

## Використані технології
- **Java:** Основна мова програмування.
- **H2 Database:** Легка вбудована та файлова база даних.
- **Flyway:** Інструмент для міграцій бази даних.
- **JUnit 5:** Фреймворк для тестування.
- **Mockito:** Фреймворк для створення мок-об'єктів в юніт-тестах.
- **HikariCP:** Пул з'єднань JDBC.


# EN
Java project for managing clients and projects, utilizing the H2 database and the Flyway migration framework. The project implements basic CRUD (Create, Read, Update, Delete) operations for the Client entity and provides database initialization and population through migrations.

## Features
- **Client Management:** Perform CRUD operations on the Client entity.
- **Database Initialization:** Initialize the database schema using Flyway migrations.
- **Database Population:** Populate the database with initial data.
- **H2 Database Integration:** Use an in-memory or file-based H2 database for development and testing.

## Technologies Used
- **Java:** Core programming language.
- **H2 Database:** Lightweight in-memory and disk-based database.
- **Flyway:** Database migration tool.
- **JUnit 5:** Testing framework.
- **Mockito:** Mocking framework for unit tests.
- **HikariCP:** JDBC connection pool.
