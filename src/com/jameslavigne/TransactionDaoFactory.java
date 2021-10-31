package com.jameslavigne;

/**
 * @author James Lavigne
 */
public class TransactionDaoFactory {
    private static TransactionDao dao = null;

    private TransactionDaoFactory(){}

    public static TransactionDao getTransactionDao(){
        if(dao == null)
            dao = new TransactionDaoImp();
        return dao;
    }
}
