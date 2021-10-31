package com.jameslavigne;

/**
 * @author James Lavigne
 */
public class EmployeeDaoFactory {
    private static EmployeeDao dao = null;

    private EmployeeDaoFactory(){}

    public static EmployeeDao getEmployeeDao(){
        if(dao == null)
            dao = new EmployeeDaoImp();
        return dao;
    }
}
