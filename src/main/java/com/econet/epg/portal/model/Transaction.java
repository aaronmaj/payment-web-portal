package com.econet.epg.portal.model;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private String transactionCode;
    private Date createdDate;
    private String transactionType;
    private String author;
    private String accountNumber;
    private Double balanceBefore;
    private Double balanceAfter;
    private Double increaseAmount;
    private Double reduceAmount;
    private Double balance;

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(Double balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public Double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(Double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public Double getIncreaseAmount() {
        return increaseAmount;
    }

    public void setIncreaseAmount(Double increaseAmount) {
        this.increaseAmount = increaseAmount;
    }

    public Double getReduceAmount() {
        return reduceAmount;
    }

    public void setReduceAmount(Double reduceAmount) {
        this.reduceAmount = reduceAmount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
