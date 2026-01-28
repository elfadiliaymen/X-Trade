package org.example;

public class Trader extends  Person{

    private double balance;

    public Trader(String id , String name , double balance){

        super(id , name );
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


}
