package com.jameslavigne;

import java.util.List;

/**
 * @author James Lavigne
 * Data Access Object for Accounts
 */
public interface AccountDao {
    void addAccount(Account account);

    Account getAccountById(int id);

    List<Account> getUserAccounts(String username);

    void updateAccount(int id, Account account);
}
