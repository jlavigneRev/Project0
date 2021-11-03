package com.jameslavigne;

/**
 * @author James Lavigne
 * Data Access Object for Customers
 */
public interface CustomerDao {
    void addCustomer(Customer customer);

    int login(String username);

    boolean validCustomerLogin(String username, String password);

    boolean usernameTaken(String username);
}
