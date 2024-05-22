package org.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class DatabaseQueryServiceTest {
    // Моки для об'єктів Connection, PreparedStatement та ResultSet
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    // Змінна для закриття ресурсів після тестування
    private AutoCloseable closeable;
    // Об'єкт класу, який ми тестуємо
    private DatabaseQueryService queryService;

    // Налаштування перед кожним тестом
    @BeforeEach
    void setUp() throws SQLException {
        // Ініціалізація моків з використанням Mockito
        closeable = MockitoAnnotations.openMocks(this);
        // Налаштування мокованого PreparedStatement на повернення мокованого ResultSet при виклику executeQuery
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        // Ініціалізація об'єкта класу, який ми тестуємо, з мокованим з'єднанням
        queryService = new DatabaseQueryService(connection);
    }

    // Закриття ресурсів після кожного тесту
    @AfterEach
    void tearDown() throws Exception {
        closeable.close(); // Закриття ресурсів, пов'язаних з моками
    }

    // Тест для методу findMaxProjectsClient
    @Test
    void testFindMaxProjectsClient() throws SQLException {
        // Налаштування мокованого ResultSet на повернення заданих значень
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("name")).thenReturn("Test Client");
        when(resultSet.getInt("id")).thenReturn(10);

        // Виклик методу, який ми тестуємо
        var results = queryService.findMaxProjectsClient(1);

        // Перевірка, що результат не є null
        assertNotNull(results);
        // Перевірка, що методи підготовки та виконання запиту були викликані
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();
        // Перевірка, що методи отримання значень з ResultSet були викликані задану кількість разів
        verify(resultSet, times(1)).getString("name");
        verify(resultSet, times(1)).getInt("id");
    }
}
