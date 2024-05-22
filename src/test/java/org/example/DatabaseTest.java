package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class DatabaseTest {
    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() throws SQLException { // Додавання throws SQLException тут
        closeable = MockitoAnnotations.openMocks(this);
        when(dataSource.getConnection()).thenReturn(connection);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close(); // Це забезпечить закриття ресурсів, пов'язаних з моками
    }

    @Test
    public void testGetConnection() throws SQLException { // Додавання throws SQLException тут
        Database db = new Database(dataSource); // Створення екземпляру класу Database з мокованим DataSource
        Connection conn = db.getConnection(); // Виклик методу, який ми хочемо протестувати

        assertNotNull(conn); // Перевірка, що результат не є null
        verify(dataSource).getConnection(); // Перевірка, що метод getConnection() був викликаний
    }
}
