package org.example;

import java.time.LocalDateTime;
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

public void wantsTobuy( String traderid , String assetcode , int quantity ){
        Trader trader = null;
        for(Trader t : traders){
            if(t.getId().equals(traderid)){
                trader = t;
            }
        }

        Asset asset = null;
        for (Asset a : assets){
            if(a.getCode().equals(assetcode)){
              asset = a ;
            }
        }
    if(trader.getBalance() > asset.getPrice() * quantity ){
        trader.buyAsset(asset , quantity);
        trader.setBalance(trader.getBalance() - asset.getPrice() * quantity);
        transactions.add(new Transaction("BUY" , assetcode, quantity , asset.getPrice() , LocalDateTime.now()));
    }else System.out.println("balance not allowed");
};

    public void wantsToSell( String traderid , String assetcode , int quantity ){
        Trader trader = null;
        for(Trader t : traders){
            if(t.getId().equals(traderid)){
                trader = t;
            }
        }

        Asset asset = null;
        for (Asset a : assets){
            if(a.getCode().equals(assetcode)){
                asset = a ;
            }
        }

        if (trader == null || asset == null) {
            System.out.println("Trader or asset not found");
            return;
        }
            transactions.add(new Transaction("SELL"  ,assetcode, quantity , asset.getPrice() , LocalDateTime.now()));
            trader.sellAsset(asset , quantity);
            trader.setBalance(trader.getBalance() + asset.getPrice() * quantity);


    };

    public void showPortfolio(String traderID){
        Trader traderFound = null;
        for(Trader t : traders){
            if(t.getId().equals(traderID)){
                traderFound = t;
            }
        }

        if(traderFound != null){
            System.out.println("name : " + traderFound.getId() + "| balance :" +
                    traderFound.getBalance() + "| portfolio : " + traderFound.getPortfolio().getOutcome());
        }else {
            System.out.println("trader not found");
        }

    }

    public void showTransactions(){

        for(Transaction tran : transactions){
            System.out.println(" type : "+tran.getType() + "| asset Code : " + tran.getAssetCode() + " | asset Price : " + tran.getPrice() + " | Quantity : " + tran.getQuantity() + "| Date :" + tran.getDate() );
        }

    }

}
