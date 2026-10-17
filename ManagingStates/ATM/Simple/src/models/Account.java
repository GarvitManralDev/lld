package models;

import exceptions.ATMException;

public class Account {
    private final String accountNumber;
    private double balance;

    public Account(String accountNumber, double initialBalance){
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public String getAccountNumber(){
        return this.accountNumber;
    }

    public synchronized double getBalance(){
        return this.balance;
    }

    public synchronized void debit(double amount){
        if(amount > balance){
            throw new ATMException("Itna paisa nhi hai account mei");
        }

        this.balance = balance - amount;
    }

    public synchronized void credit(double amount){
        this.balance += amount;
    }
}
