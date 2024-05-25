package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Date;

class MaxProjectCountClientTest {

    @Test // Marking the method as a test method
    void testClientProperties() {
        // Initializing test data
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

        // Creating an instance of MaxProjectCountClient with test data
        MaxProjectCountClient client = new MaxProjectCountClient(id, name, birthday, level, salary, clientId, workerId, projectCount);
        client.setStartDate(startDate);
        client.setFinishDate(finishDate);
        client.setProjectId(projectId);

        // Verifying that all getters return the correct values
        assertEquals(id, client.getId()); // Verifying ID
        assertEquals(name, client.getName()); // Verifying name
        assertEquals(birthday, client.getBirthday()); // Verifying birthday
        assertEquals(level, client.getLevel()); // Verifying level
        assertEquals(salary, client.getSalary()); // Verifying salary
        assertEquals(clientId, client.getClientId()); // Verifying client ID
        assertEquals(workerId, client.getWorkerId()); // Verifying worker ID
        assertEquals(projectCount, client.getProjectCount()); // Verifying project count
        assertEquals(startDate, client.getStartDate()); // Verifying start date of the project
        assertEquals(finishDate, client.getFinishDate()); // Verifying finish date of the project
        assertEquals(projectId, client.getProjectId()); // Verifying project ID

        // Testing the toString method
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
        assertEquals(expectedString, client.toString()); // Verifying that the toString method returns the expected string
    }
}
