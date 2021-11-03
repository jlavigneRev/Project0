package com.jameslavigne;

import java.util.List;

/**
 * @author James Lavigne
 * Data Access Object for Transactions
 */
public interface TransactionDao {
    void addTransaction(Transaction transaction);

    Transaction getTransactionById(int id);

    List<Transaction> getPendingFromCustId(int id);

    List<Transaction> getAllTransaction();

    boolean approveTransaction(int id);

    void deleteTransaction(int id);
}
