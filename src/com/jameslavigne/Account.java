package com.jameslavigne;

/**
 * @author James Lavigne
 */
public class Account {
    private int accId;
    private int custId;
    private double balance;
    private boolean approved;

    public Account() {
    }

    public Account(int accId, int custId, double balance, boolean approved) {
        this.accId = accId;
        this.custId = custId;
        this.balance = balance;
        this.approved = approved;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
