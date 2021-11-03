package com.jameslavigne;

import com.mysql.cj.protocol.x.XProtocolError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImp implements TransactionDao {
    Connection connection;

    public TransactionDaoImp() {
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transaction (source_id, dest_id, amount, pending) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, transaction.getSourceId());
            preparedStatement.setInt(2, transaction.getDestId());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setBoolean(4, transaction.isPending());
            int count = preparedStatement.executeUpdate();
            if (count > 0)
                return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Could not record transaction.");
    }

    @Override
    public Transaction getTransactionById(int id) {
        return null;
    }

    @Override
    public List<Transaction> getPendingFromCustId(int id) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE dest_id = ? AND pending = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setBoolean(2, true);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                Transaction tran = new Transaction();
                tran.setTranId(results.getInt(1));
                tran.setSourceId(results.getInt(2));
                tran.setDestId(results.getInt(3));
                tran.setAmount(results.getDouble(4));
                tran.setPending(results.getBoolean(5));
                tran.setDate(results.getDate(6));
                transactions.add(tran);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public List<Transaction> getAllTransaction() {
        String sql = "call getAllTransaction()";
        List<Transaction> transactionLog = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                Transaction tran = new Transaction();
                tran.setTranId(results.getInt(1));
                tran.setSourceId(results.getInt(2));
                tran.setDestId(results.getInt(3));
                tran.setAmount(results.getDouble(4));
                tran.setPending(results.getBoolean(5));
                tran.setDate(results.getDate(6));
                transactionLog.add(tran);
            }
            return transactionLog;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Could Not Retrieve Transaction Log");
        return transactionLog;
    }

    @Override
    public boolean approveTransaction(int id) {
        String sql = "UPDATE transaction SET pending = ? WHERE tran_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, id);
            int count = preparedStatement.executeUpdate();
            if (count > 0) {
                return true;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteTransaction(int id) {
        String sql = "DELETE FROM transaction WHERE tran_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int count = preparedStatement.executeUpdate();
            if (count > 0)
                System.out.println("Transfer Request Denied.");
            return;
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        System.out.println("Could Not Deny Transfer Request");
    }
}
