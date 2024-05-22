package org.example;

import static org.junit.jupiter.api.Assertions.*; // Імпорт всіх статичних методів з Assertions для використання в тестах
import org.junit.jupiter.api.Test; // Імпорт анотації Test для позначення тестових методів
import java.util.Date; // Імпорт класу Date для роботи з датами

class MaxProjectCountClientTest {

    @Test // Позначення методу як тестового
    void testClientProperties() {
        // Ініціалізація тестових даних
        int id = 1;
        String name = "John Doe";
        Date birthday = new Date();
        String level = "Senior";
        double salary = 5000.0;
        int clientId = 10;
        int workerId = 20;
        int projectCount = 3;
        Date startDate = new Date();
        Date finishDate = new Date();
        int projectId = 100;

        // Створення об'єкта MaxProjectCountClient з тестовими даними
        MaxProjectCountClient client = new MaxProjectCountClient(id, name, birthday, level, salary, clientId, workerId, projectCount);
        client.setStartDate(startDate);
        client.setFinishDate(finishDate);
        client.setProjectId(projectId);

        // Перевірка, що всі геттери повертають правильні значення
        assertEquals(id, client.getId()); // Перевірка ID
        assertEquals(name, client.getName()); // Перевірка імені
        assertEquals(birthday, client.getBirthday()); // Перевірка дати народження
        assertEquals(level, client.getLevel()); // Перевірка рівня
        assertEquals(salary, client.getSalary()); // Перевірка зарплати
        assertEquals(clientId, client.getClientId()); // Перевірка ID клієнта
        assertEquals(workerId, client.getWorkerId()); // Перевірка ID працівника
        assertEquals(projectCount, client.getProjectCount()); // Перевірка кількості проектів
        assertEquals(startDate, client.getStartDate()); // Перевірка дати початку проекту
        assertEquals(finishDate, client.getFinishDate()); // Перевірка дати завершення проекту
        assertEquals(projectId, client.getProjectId()); // Перевірка ID проекту

        // Тестування методу toString
        String expectedString = "MaxProjectCountClient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", level='" + level + '\'' +
                ", salary=" + salary +
                ", clientId=" + clientId +
                ", workerId=" + workerId +
                ", projectCount=" + projectCount +
                '}';
        assertEquals(expectedString, client.toString()); // Перевірка, що метод toString повертає очікуваний рядок
    }
}

