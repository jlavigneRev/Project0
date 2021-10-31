package com.jameslavigne;

/**
 * @author James Lavigne
 */
public class Employee {
    private int empId;
    private String username;
    private String password;
    private String name;

    public Employee(){}

    public Employee(int empId, String username, String password, String name) {
        this.empId = empId;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
