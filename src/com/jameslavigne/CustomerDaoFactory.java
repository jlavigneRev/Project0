package com.jameslavigne;

/**
 * @author James Lavigne
 */
public class CustomerDaoFactory {
    private static CustomerDao dao = null;

    private CustomerDaoFactory(){}

    public CustomerDao getCustomerDao(){
        if(dao == null)
            dao = new CustomerDaoImp();
        return dao;
    }
}
