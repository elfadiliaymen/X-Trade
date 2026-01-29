package org.example;

public class Trader extends  Person{

    private double balance;
    private Portfolio<String> portfolio;

    public Trader(String id , String name , double balance){

        super(id , name );
        this.balance = balance;
        this.portfolio = new Portfolio<>();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Portfolio<String> getPortfolio(){
        return portfolio;
    }


    public void buyAsset( Asset asset , int  quantity){
        portfolio.addAsset(asset.getCode(),  quantity);
    }

    public void sellAsset( Asset asset , int quantity){
      portfolio.deleteAsset(asset.getCode() , quantity);
    }

}
