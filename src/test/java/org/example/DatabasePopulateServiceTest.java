package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.flywaydb.core.Flyway;
import javax.sql.DataSource;
import static org.mockito.Mockito.*;


public class DatabasePopulateServiceTest {
    // Моки для DataSource та Flyway
    @Mock
    private DataSource dataSource;
    @Mock
    private Flyway flyway;

    // Змінна для закриття ресурсів після тестування
    private AutoCloseable closeable;

    // Налаштування перед кожним тестом
    @BeforeEach
    void setUp() {
        // Ініціалізація моків з використанням Mockito
        closeable = MockitoAnnotations.openMocks(this);
        // Налаштування Flyway для повернення мокованого екземпляра при виклику configure().dataSource(dataSource).load()
        when(Flyway.configure().dataSource(dataSource).load()).thenReturn(flyway);
    }

    // Закриття ресурсів після кожного тесту
    @AfterEach
    void tearDown() throws Exception {
        closeable.close(); // Закриття ресурсів, пов'язаних з моками
    }

    // Тест для перевірки правильності поповнення бази даних
    @Test
    void testDatabasePopulation() {
        // Виклик методу, який ініціалізує базу даних
        DatabasePopulateService.main(null);

        // Перевірка, що метод migrate() був викликаний на екземплярі Flyway
        verify(flyway).migrate();
    }
}
