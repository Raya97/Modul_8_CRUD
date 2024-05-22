package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class) // Використання розширення Mockito для інтеграції з JUnit 5
class ClientServiceTest {

    @Mock
    private Connection connection; // Мок об'єкта Connection для імітації з'єднання з базою даних

    @Mock
    private PreparedStatement preparedStatement; // Мок об'єкта PreparedStatement для імітації SQL-запитів

    @Mock
    private ResultSet resultSet; // Мок об'єкта ResultSet для імітації результатів SQL-запитів

    @InjectMocks
    private ClientService clientService; // Ін'єкція мока Connection у клас ClientService

    @BeforeEach
    void setUp() throws SQLException {
        // Налаштування поведінки мока Connection для повернення PreparedStatement при виклику prepareStatement
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet); // Налаштування PreparedStatement для повернення ResultSet
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet); // Налаштування для отримання згенерованих ключів
    }

    @Test
    void create() throws SQLException {
        // Налаштування ResultSet для повернення згенерованого ключа
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L); // Симуляція повернення згенерованого ключа

        String name = "New Client";
        when(preparedStatement.executeUpdate()).thenReturn(1); // Симуляція успішного виконання оновлення

        long clientId = clientService.create(name); // Виклик методу create з перевіркою його роботи

        verify(preparedStatement).setString(1, name); // Перевірка, що setString був викликаний з правильним параметром
        verify(preparedStatement).executeUpdate(); // Перевірка, що executeUpdate був викликаний
        verify(preparedStatement).getGeneratedKeys(); // Перевірка, що getGeneratedKeys був викликаний
        assertEquals(1L, clientId, "Повернений ідентифікатор клієнта має бути 1"); // Перевірка, що повернений ідентифікатор правильний
    }

    @Test
    void getById() throws SQLException {
        String expectedName = "Existing Client";
        // Налаштування ResultSet для повернення значення імені клієнта
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("name")).thenReturn(expectedName);

        String name = clientService.getById(1L); // Виклик методу getById

        verify(preparedStatement).setLong(1, 1L); // Перевірка, що setLong був викликаний з правильним параметром
        verify(preparedStatement).executeQuery(); // Перевірка, що executeQuery був викликаний
        assertEquals(expectedName, name, "Отримане ім'я має відповідати очікуваному"); // Перевірка, що отримане ім'я правильне
    }

    @Test
    void setName() throws SQLException {
        clientService.setName(1L, "Updated Client"); // Виклик методу setName

        verify(preparedStatement).setString(1, "Updated Client"); // Перевірка, що setString був викликаний з правильним параметром
        verify(preparedStatement).setLong(2, 1L); // Перевірка, що setLong був викликаний з правильним параметром
        verify(preparedStatement).executeUpdate(); // Перевірка, що executeUpdate був викликаний
    }

    @Test
    void deleteById() throws SQLException {
        clientService.deleteById(1L); // Виклик методу deleteById

        verify(preparedStatement).setLong(1, 1L); // Перевірка, що setLong був викликаний з правильним параметром
        verify(preparedStatement).executeUpdate(); // Перевірка, що executeUpdate був викликаний
    }
}
