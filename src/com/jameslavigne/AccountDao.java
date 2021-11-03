package com.jameslavigne;

import java.util.List;

/**
 * @author James Lavigne
 * Data Access Object for Accounts
 */
public interface AccountDao {
    void addAccount(Account account);

    Account getAccountById(int id);

    List<Account> getAccountsByCustId(int cust_id);

    List<Account> getPendingAccounts();

    double getBalance(int id);

    boolean deposit(int id, double amount);

    boolean withdraw(int id, double amount);

    boolean transfer(int srcId, int destId, double amount);

    void approveAccount(int id);

    void denyAccount(int id);
}
