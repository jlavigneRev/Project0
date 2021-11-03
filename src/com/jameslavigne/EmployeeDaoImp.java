package com.jameslavigne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoImp implements EmployeeDao {
    Connection connection;

    public EmployeeDaoImp() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public Employee getEmployeeById(int id) {
        return null;
    }

    @Override
    public boolean validEmployeeLogin(String username, String password) {
        String sql = "SELECT * FROM employee WHERE username = ? AND password = ? LIMIT 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet results = preparedStatement.executeQuery();
            if (results.next()) {
                //valid credentials
                return true;
            } else {
                //invalid credentials
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
