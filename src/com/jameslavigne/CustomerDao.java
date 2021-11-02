package com.jameslavigne;

/**
 * @author James Lavigne
 * Data Access Object for Customers
 */
public interface CustomerDao {
    void addCustomer(Customer customer);

    void updateCustomer(int id, Customer customer);

    void deleteCustomer(int id);

    Employee getCustomerById(int id);

    Employee getCustomerByUsername(String username);

    int login(String username);

    boolean validCustomerLogin(String username, String password);

    boolean isApporved(int id);
}
