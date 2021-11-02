package com.jameslavigne;

import java.util.List;

/**
 * @author James Lavigne
 * Data Access Object for Transactions
 */
public interface TransactionDao {
    void addTransaction(Transaction transaction);

    Transaction getTransactionById(int id);

    List<Transaction> getPendingFromAccId(int id);

    void updateTransactionById(int id, Transaction transaction);

    void deleteTransaction(int id);
}
