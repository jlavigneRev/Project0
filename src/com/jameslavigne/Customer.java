package com.jameslavigne;

/**
 * @author James Lavigne
 */
public class Customer {
    private int custId;
    private String username;
    private String password;
    private boolean approved;
    private String name;

    public Customer(){}

    public Customer(int custId, String username, String password, boolean approved, String name) {
        this.custId = custId;
        this.username = username;
        this.password = password;
        this.approved = approved;
        this.name = name;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
