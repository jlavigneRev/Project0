package com.jameslavigne;

/**
 * @author James Lavigne
 * Data Access Object for Customers
 */
public interface CustomerDao {
    void addCustomer(Employee employee);

    void updateCustomer(int id, Employee employee);

    void deleteCustomer(int id);

    Employee getEmployeeById(int id);

    Employee getEmployeeByUsername(String username);

    boolean validEmployeeLogin(Employee employee);
}
