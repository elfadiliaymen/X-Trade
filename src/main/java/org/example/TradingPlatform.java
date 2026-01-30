package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        if (assets.isEmpty()) {
            System.out.println("Aucun actif");
            return;
        }
        System.out.println("============Actifs Disponibles=========");


        for (Asset ast : assets){
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Code :"+ ast.getCode() + " | Name : " + ast.getName() + " | Price : " + ast.getPrice() + "  |  Type :" + ast.getType());
            System.out.println("-----------------------------------------------------------------");
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
    if (trader == null){
        throw new IllegalArgumentException("Trader non trouvé");
    }

        Asset asset = null;
        for (Asset a : assets){
            if(a.getCode().equals(assetcode)){
              asset = a ;
            }
        }

    if (asset == null) throw new IllegalArgumentException("Actif non trouvé");
    if (quantity <= 0) throw new IllegalArgumentException("Quantité invalide");

    if (trader.getBalance() <  asset.getPrice() * quantity) {
        throw new IllegalArgumentException("Solde insuffisant ");
    }

        trader.buyAsset(asset , quantity);
        trader.setBalance(trader.getBalance() - asset.getPrice() * quantity);
        transactions.add(new Transaction("BUY" , assetcode, quantity , asset.getPrice() , LocalDateTime.now()));

};

    public void wantsToSell( String traderid , String assetcode , int quantity ){
        Trader trader = null;
        for(Trader t : traders){
            if(t.getId().equals(traderid)){
                trader = t;
            }
        }

        if (trader == null){
            throw new IllegalArgumentException("Trader non trouvé");
        }

        Asset asset = null;
        for (Asset a : assets){
            if(a.getCode().equals(assetcode)){
                asset = a ;
            }
        }

        if (asset == null){
            throw new IllegalArgumentException("Actif non trouvé");
        }

        if (quantity <= 0){
            throw new IllegalArgumentException("Quantité invalide");
        }
        if (trader.getPortfolio().getQuantity(assetcode) < quantity) {
            throw new IllegalArgumentException("Quantité insuffisante dans portefeuille");
        }


            trader.sellAsset(asset , quantity);
            trader.setBalance(trader.getBalance() + asset.getPrice() * quantity);
        transactions.add(new Transaction("SELL"  ,assetcode, quantity , asset.getPrice() , LocalDateTime.now()));


    };

    public void showPortfolio(String traderID){
        Trader traderFound = null;
        for(Trader t : traders){
            if(t.getId().equals(traderID)){
                traderFound = t;
            }
        }

        if(traderFound != null){
            System.out.println("-----------------------------------------------------------------");
            System.out.printf("-----------Portefeuille de" + traderFound.getName() + "-----------");
            System.out.println("-----------------------------------------------------------------");
            double totalValue = 0;

            for (var entry : traderFound.getPortfolio().getOutcome().entrySet()) {
                String code = entry.getKey();
                int qty = entry.getValue();
                Asset asseF = null;
                for (Asset ast : assets) {
                    if(ast.getCode().equals(code)){
                        asseF = ast ;
                }
                if (asseF != null) {
                    double value = qty * asseF.getPrice();
                    totalValue += value;
                    System.out.printf("Code : " + code + "  | Asset : " + asseF.getName()+ "  | Quantity  : " + qty + "  |  Price  : " +  asseF.getPrice() +  " |  total price : " + value);
                }
            }
                System.out.println("\n-----------------------------------------------------------------");
            System.out.printf("Valeur totale portefeuille : " +  totalValue);
        }
        }else {
            System.out.println("Trader non trouvé");
        }

    }

    public void showTransactions(){
        if (transactions.isEmpty()) {
            System.out.println("Aucune transaction");
            return;
        }

        System.out.println("-----------------------------------------------------------------");
        System.out.println("---------------| Historique des Transactions |-------------------");
        System.out.println("-----------------------------------------------------------------");
        for(Transaction tran : transactions){
            System.out.println(" type : "+tran.getType() + "| asset Code : " + tran.getAssetCode() + " | asset Price : " + tran.getPrice() + " | Quantity : " + tran.getQuantity() + "| Date :" + tran.getDate() );
        }
        System.out.println("-----------------------------------------------------------------");

    }



    public void updateAssetPricesRandomly() {

        if (assets.isEmpty()) {
            System.out.println("Aucun actif à mettre à jour");
            return;
        }

        Random random = new Random();

        System.out.println(" Mise à jour des prix...");

        for (Asset asset : assets) {
            double oldPrice = asset.getPrice();
            boolean UpOrDown = random.nextBoolean();
            double variation;
            if (UpOrDown) {
                variation = 0.05;
            } else {
                variation = -0.05;
            }

            double newPrice = oldPrice + (oldPrice * variation);

            if (newPrice < 1) {
                newPrice = 1;
            }
            asset.setPrice(newPrice);
            System.out.println(
                    asset.getName() + " (" + asset.getCode() + ") : "
                            + oldPrice + " $ → " + newPrice + " $"
            );
        }

        System.out.println("Prix mis à jour\n");
    }
}
