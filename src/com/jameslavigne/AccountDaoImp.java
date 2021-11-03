package com.jameslavigne;

import com.mysql.cj.protocol.x.XProtocolError;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImp implements AccountDao {
    Connection connection;

    public AccountDaoImp() {
        this.connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addAccount(Account account) {
        String sql = "INSERT INTO account (cust_id, balance) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account.getCustId());
            preparedStatement.setDouble(2, account.getBalance());
            int count = preparedStatement.executeUpdate();
            if (count > 0)
                System.out.println("New bank account created");
            else
                System.out.println("New bank account couldn't be created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account getAccountById(int id) {
        Account account = new Account();
        String sql = "SELECT * FROM account WHERE acc_id = ? LIMIT 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                account.setAccId(result.getInt(1));
                account.setCustId(result.getInt(2));
                account.setBalance(result.getDouble(3));
                account.setApproved(result.getBoolean(4));
                return account;
            }
        } catch (SQLException e) {
            // e.printStackTrace();
        }
        System.out.println("Could Not Find an Account with that ID");
        return account;
    }

    @Override
    public List<Account> getAccountsByCustId(int id) {
        String sql = "SELECT * FROM account WHERE cust_id = ?";
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                Account account = new Account();
                account.setAccId(results.getInt(1));
                account.setCustId(results.getInt(2));
                account.setBalance(results.getDouble(3));
                account.setApproved(results.getBoolean(4));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> getPendingAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account WHERE approved = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, false);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                Account acc = new Account();
                acc.setAccId(results.getInt(1));
                acc.setCustId(results.getInt(2));
                acc.setBalance(results.getDouble(3));
                acc.setApproved(results.getBoolean(4));
                accounts.add(acc);
            }
            return accounts;
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deposit(int id, double amount) {
        String sql = "UPDATE account SET balance=balance + ? WHERE acc_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, id);
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println("Deposited $" + Menu.df.format(amount) + " into Account ID #" + id);
                return true;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        System.out.println("Could not deposit");
        return false;
    }

    @Override
    public boolean withdraw(int id, double amount) {
        String sql = "UPDATE account SET balance=balance - ? WHERE acc_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, id);
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println("Withdrew $" + Menu.df.format(amount) + " into Account ID #" + id);
                return true;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        System.out.println("Could not withdraw");
        return false;
    }

    @Override
    public boolean transfer(int srcId, int destId, double amount) {
        String withdrawSql = "UPDATE account SET balance = balance - ? WHERE acc_id = ?";
        String depositSql = "UPDATE account SET balance = balance + ? WHERE acc_id = ?";

        try {
            connection.setAutoCommit(false);
            //withdraw from src account
            PreparedStatement preparedStatement = connection.prepareStatement(withdrawSql);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, srcId);
            preparedStatement.executeUpdate();
            //deposit to dest account
            PreparedStatement preparedStatement1 = connection.prepareStatement(depositSql);
            preparedStatement1.setDouble(1, amount);
            preparedStatement1.setInt(2, destId);
            preparedStatement1.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return false;
    }

    @Override
    public void approveAccount(int id) {
        String sql = "UPDATE account SET approved = ? WHERE acc_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, id);
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println("Account #" + id + " was approved");
            } else {
                System.out.println("Account #" + id + " could not be approved");
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void denyAccount(int id) {
        String sql = "UPDATE account SET approved = ? WHERE acc_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, id);
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                System.out.println("Account #" + id + " was denied");
                return;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        System.out.println("Account #" + id + " could not be denied");
    }
}
