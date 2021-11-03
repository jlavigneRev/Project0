package com.jameslavigne.test;

import com.jameslavigne.CustomerDao;
import com.jameslavigne.CustomerDaoImp;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerDaoImpTest {
    @Test
    public void validCutstomerLoginTest(){
        CustomerDao dao = new CustomerDaoImp();
        //correct combination
        assertEquals(true, dao.validCustomerLogin("testCust", "testCust"));
        //wrong password
        assertEquals(false, dao.validCustomerLogin("testCust", "test"));
        //employee account
        assertEquals(false, dao.validCustomerLogin("test", "test"));
    }
}
