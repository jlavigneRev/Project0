package com.jameslavigne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImp implements CustomerDao {
    Connection connection;

    public CustomerDaoImp() {
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (username, password, name) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getUsername());
            preparedStatement.setString(2, customer.getPassword());
            preparedStatement.setString(3, customer.getName());
            int count = preparedStatement.executeUpdate();
            if (count > 0)
                System.out.println("New user created, please login to access your account");
            else
                System.out.println("New user couldn't be created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves id from passed credentials
     *
     * @param username Username of user
     * @return id of customer matching credentials, if not found then -1
     */
    @Override
    public int login(String username) {
        String sql = "SELECT cust_id FROM customer WHERE username = ? LIMIT 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet results = preparedStatement.executeQuery();
            if (results.next()) {
                //valid credentials
                System.out.println("Logging in as Customer...");
                return results.getInt(1);
            } else {
                //invalid credentials
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean validCustomerLogin(String username, String password) {
        String sql = "SELECT * FROM customer WHERE username = ? AND password = ? LIMIT 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet results = preparedStatement.executeQuery();
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
