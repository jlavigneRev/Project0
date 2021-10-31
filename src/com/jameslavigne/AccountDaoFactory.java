package com.jameslavigne;

/**
 * @author James Lavigne
 */
public class AccountDaoFactory {
    private static AccountDao dao = null;

    private AccountDaoFactory(){}

    public static AccountDao getAccountDao(){
        if(dao == null)
            dao = new AccountDaoImp();
        return dao;
    }
}
