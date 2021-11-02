package com.jameslavigne;

/**
 * @author James Lavigne
 * Data Access object for Employees
 */
public interface EmployeeDao {
    void addEmployee(Employee employee);

    Employee getEmployeeByUsername(String username);

    Employee getEmployeeById(int id);

    boolean validEmployeeLogin(String username, String password);
}
