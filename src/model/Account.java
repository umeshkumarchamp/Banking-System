package model;

import java.sql.Date;

public class Account {
    private int id; 
    private Long accountNumber; 
    private String accountType; 
    private Long balance; 
    private Date openDate; 
    private int customerId; 

    public Account() {
    }

    public Account(int id, Long accountNumber, String accountType, Long balance, Date openDate, int customerId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.openDate = openDate;
        this.customerId = customerId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Long getBalance() {
        return this.balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Date getOpenDate() {
        return this.openDate;
    }

    public void setOpenDate(Date date) {
        this.openDate = date;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", balance='" + getBalance() + "'" +
            ", openDate='" + getOpenDate() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            "}";
    }

    
}
