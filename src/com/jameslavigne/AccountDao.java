package com.jameslavigne;

import java.util.List;

/**
 * @author James Lavigne
 * Data Access Object for Accounts
 */
public interface AccountDao {
    void addAccount(Account account);

    double getBalance(int id);

    Account getAccountById(int id);

    List<Account> getAccountsByCustId(int cust_id);

    List<Account> getUserAccounts(String username);

    List<Account> getPendingAccounts();

    void updateAccount(int id, Account account);

    boolean deposit(int id, Double amount);

    boolean withdraw(int id, Double amount);

    void approveAccount(int id);

    void denyAccount(int id);
}
