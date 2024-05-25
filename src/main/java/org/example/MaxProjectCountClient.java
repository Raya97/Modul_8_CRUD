package org.example;

import java.util.Date;

public class MaxProjectCountClient {
    private int projectCount; // New property to store the number of projects
    private int id; // Unique identifier
    private String name; // Client name
    private final Date birthday; // Date of birth
    private final String level; // Client level
    private final double salary; // Salary
    private final int clientId; // Unique identifier of the client
    private int workerId; // Unique identifier of the worker

    // Empty constructor for default initialization
    public MaxProjectCountClient() {
        this.id = 0;
        this.name = "";
        this.birthday = new Date();
        this.level = "";
        this.salary = 0.0;
        this.clientId = 0;
    }

    // Constructor for initializing all properties
    public MaxProjectCountClient(int id, String name, Date birthday, String level, double salary, int clientId, int workerId, int projectCount) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.level = level;
        this.salary = salary;
        this.clientId = clientId;
        this.workerId = workerId;
        this.projectCount = projectCount;
    }

    // Getters and setters for all properties

    // Getter and setter for project start date
    private Date startDate;
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    // Getter and setter for project finish date
    private Date finishDate;
    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    // Getter and setter for project ID
    private int projectId;
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    // Getter and setter for the unique object ID
    public int getId() {
        return id;
    }

    public void setId() {
        setId(0);
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and setter for client name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter for date of birth
    public Date getBirthday() {
        return birthday;
    }

    // Getter for client level
    public String getLevel() {
        return level;
    }

    // Getter for salary
    public double getSalary() {
        return salary;
    }

    // Getter for client unique identifier
    public int getClientId() {
        return clientId;
    }

    // Getter and setter for worker unique identifier
    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    // Getter and setter for the number of client projects
    public int getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(int projectCount) {
        this.projectCount = projectCount;
    }

    // Overridden toString() method to represent the object as a string
    @Override
    public String toString() {
        return "MaxProjectCountClient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", level='" + level + '\'' +
                ", salary=" + salary +
                ", clientId=" + clientId +
                ", workerId=" + workerId +
                ", projectCount=" + projectCount +
                '}';
    }
}
