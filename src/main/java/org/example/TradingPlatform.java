package org.example;

import java.util.ArrayList;
import java.util.List;

public class TradingPlatform {
    private String name;
    private List<Trader> traders = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private List<Asset> assets = new ArrayList<>();


    public TradingPlatform(String name){
        this.name = name;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Trader> getTraders() {
        return traders;
    }

    public void setTraders(List<Trader> traders) {
        this.traders = traders;
    }


    public void addAsset(Asset newAsset){
        assets.add(newAsset);
    }

    public void showAssets(){
        for (Asset ast : assets){
            System.out.println("Type :" + ast.getType());
            System.out.println("Code :"+ ast.getCode() + " | Name : " + ast.getName() + " | Price : " + ast.getPrice() );
        }
    }

    public void addTrader(Trader newTrader){
        traders.add(newTrader);
    }



}
