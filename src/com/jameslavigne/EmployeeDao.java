package com.jameslavigne;

/**
 * @author James Lavigne
 * Data Access object for Employees
 */
public interface EmployeeDao {

    Employee getEmployeeById(int id);

    boolean validEmployeeLogin(String username, String password);
}
