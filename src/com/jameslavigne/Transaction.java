package com.jameslavigne;

import java.util.Date;

/**
 * @author James Lavigne
 */
public class Transaction {
    private int tranId;
    private int sourceId;
    private int destId;
    private double amount;
    private Date date;
    private boolean pending;

    public Transaction() {
    }

    public Transaction(int tranId, int sourceId, int destId, double amount, Date date, boolean pending) {
        this.tranId = tranId;
        this.sourceId = sourceId;
        this.destId = destId;
        this.amount = amount;
        this.date = date;
        this.pending = pending;
    }

    public int getTranId() {
        return tranId;
    }

    public void setTranId(int tranId) {
        this.tranId = tranId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getDestId() {
        return destId;
    }

    public void setDestId(int destId) {
        this.destId = destId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "tranId=" + tranId +
                ", sourceId=" + sourceId +
                ", destId=" + destId +
                ", amount=" + amount +
                ", date=" + date +
                ", pending=" + pending +
                '}';
    }
}
