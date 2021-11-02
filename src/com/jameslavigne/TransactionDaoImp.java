package com.jameslavigne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImp implements TransactionDao{
    Connection connection;

    public TransactionDaoImp(){
        connection = ConnectionFactory.getConnection();
    }

    @Override
    public void addTransaction(Transaction transaction) {

    }

    @Override
    public Transaction getTransactionById(int id) {
        return null;
    }

    @Override
    public List<Transaction> getPendingFromAccId(int id) {
        List<Transaction> transactions = new ArrayList<>();
        Transaction tran = new Transaction();
        String sql = "SELECT * FROM transaction WHERE dest_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet results = preparedStatement.executeQuery();
            while(results.next()){
                tran.setTranId(results.getInt(1));
                tran.setSourceId(results.getInt(2));
                tran.setDestId(results.getInt(3));
                tran.setAmount(results.getDouble(4));
                tran.setDate(results.getDate(5));
                tran.setPending(results.getBoolean(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public void updateTransactionById(int id, Transaction transaction) {

    }

    @Override
    public void deleteTransaction(int id) {

    }
}
