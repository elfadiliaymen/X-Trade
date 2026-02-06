package org.example;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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

    public void basicData() {


        Asset aapl = new Stock("AAPL", "Apple", 150);
        Asset btc  = new CryptoCurency("BTC", "Bitcoin", 30000);
        Asset eth  = new CryptoCurency("ETH", "Ethereum", 2000);

        assets.addAll(List.of(aapl, btc, eth));

        Trader t1 = new Trader("T1", "Alice", 100000);
        Trader t2 = new Trader("T2", "Bob", 80000);
        Trader t3 = new Trader("T3", "Charlie", 50000);

        traders.addAll(List.of(t1, t2, t3));


        transactions.add(new Transaction("T1", "BUY", "AAPL", 50, 150, LocalDateTime.now().minusDays(3)));
        transactions.add(new Transaction("T1", "BUY", "BTC", 1, 30000, LocalDateTime.now().minusDays(2)));
        transactions.add(new Transaction("T2", "BUY", "AAPL", 30, 150, LocalDateTime.now().minusDays(2)));
        transactions.add(new Transaction("T2", "BUY", "ETH", 10, 2000, LocalDateTime.now().minusDays(1)));
        transactions.add(new Transaction("T3", "BUY", "ETH", 5, 2000, LocalDateTime.now().minusHours(10)));

        transactions.add(new Transaction("T1", "SELL", "AAPL", 10, 155, LocalDateTime.now().minusHours(5)));
        transactions.add(new Transaction("T2", "SELL", "ETH", 3, 2100, LocalDateTime.now().minusHours(2)));

        t1.buyAsset(aapl, 40);
        t1.buyAsset(btc, 1);

        t2.buyAsset(aapl, 30);
        t2.buyAsset(eth, 7);

        t3.buyAsset(eth, 5);
    }


    public void addAsset(Asset newAsset){
        assets.add(newAsset);
    }

    public void showAssets(){

        if (assets.isEmpty()) {
            System.out.println("Aucun actif");
            return;
        }
        System.out.println("============< Actifs Disponibles >=========");


        for (Asset ast : assets){
            System.out.println("Code :"+ ast.getCode() + " | Name : " + ast.getName() + " | Price : " + ast.getPrice() + "  |  Type :" + ast.getType());
            System.out.println("-----------------------------------------------------------------");
        }
    }

    public void addTrader(Trader newTrader){
        traders.add(newTrader);
    }

    public Trader findTrader(String traderid){
        Trader trader = null;
        for(Trader t : traders){
            if(t.getId().equals(traderid)){
                trader = t;
            }
        }
        return trader;
    }

    public Asset findAsset(String assetcode){
        Asset asset = null;
        for (Asset a : assets){
            if(a.getCode().equals(assetcode)){
                asset = a ;
            }
        }
        return asset;
    }

public void wantsTobuy( String traderid , String assetcode , int quantity ){
        Trader trader = findTrader(traderid);

    if (trader == null){
        throw new IllegalArgumentException("Trader non trouvé");
    }

        Asset asset = findAsset(assetcode);

    if (asset == null) throw new IllegalArgumentException("Actif non trouvé");
    if (quantity <= 0) throw new IllegalArgumentException("Quantité invalide");

    if (trader.getBalance() <  asset.getPrice() * quantity) {
        throw new IllegalArgumentException("Solde insuffisant ");
    }

        trader.buyAsset(asset , quantity);
        trader.setBalance(trader.getBalance() - asset.getPrice() * quantity);
        transactions.add(new Transaction(traderid , "BUY" , assetcode, quantity , asset.getPrice() , LocalDateTime.now()));

};

    public void wantsToSell( String traderid , String assetcode , int quantity ){
        Trader trader = findTrader(traderid);

        if (trader == null){
            throw new IllegalArgumentException("Trader non trouvé");
        }

        Asset asset = findAsset(assetcode);

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
        transactions.add(new Transaction(traderid , "SELL"  ,assetcode, quantity , asset.getPrice() , LocalDateTime.now()));


    };

    public void showPortfolio(String traderID){
        Trader traderFound = findTrader(traderID);

        if (traderFound == null) {
            System.out.println("Trader non trouvé");
            return;
        }

        if(traderFound != null){
            System.out.printf("-----< Portefeuille de : " + traderFound.getName() + " >----- \n");

            if (traderFound.getPortfolio().getOutcome().isEmpty()) {
                System.out.println("Portefeuille vide.");
                System.out.println("Valeur totale portefeuille : 0.0 $");
                System.out.println("-----------------------------------------------------------------");
                return;
            }


            double totalValue = 0;

            for (var entry : traderFound.getPortfolio().getOutcome().entrySet()) {
                String code = entry.getKey();
                int qty = entry.getValue();

                Asset asset = findAsset(code);
                if (asset != null) {
                    double value = qty * asset.getPrice();
                    totalValue += value;

                    System.out.println(
                            "Code : " + code +
                                    " | Actif : " + asset.getName() +
                                    " | Quantité : " + qty +
                                    " | Prix : " + asset.getPrice() + " $" +
                                    " | Valeur : " + value + " $"
                    );
                }
            }

            System.out.println("-----------------------------------------------------------------");
            System.out.println("Valeur totale portefeuille : " + totalValue + " $");
            System.out.println("-----------------------------------------------------------------");

    }
    }

    public void showTransactions(){
        if (transactions.isEmpty()) {
            System.out.println("Aucune transaction");
            return;
        }

        System.out.println("-----------------------------------------------------------------");
        System.out.println("---------------| Historique des Transactions |-------------------");
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


    public void calculateGainsAndLosses(String traderId){
        Trader trader = findTrader(traderId);
        if (trader == null) {
            System.out.println("Trader non trouvé");
            return;
        }

        double totalInvested = 0;
        double currentValue = 0;

        for(Transaction t : transactions){
         if(t.getType().equals("BUY")){
             totalInvested += t.getPrice() * t.getQuantity();
         }
        }

        for(var entry : trader.getPortfolio().getOutcome().entrySet()){
             String assetCode = entry.getKey();
             int quantity = entry.getValue();

             Asset asset = findAsset(assetCode);

             if(asset != null){
                currentValue += asset.getPrice() * quantity;
             }
        }

        double gainOrLoss = currentValue - totalInvested;

        System.out.println("---- Gains et Pertes ----");
        System.out.println("Montant investi : " + totalInvested + "$");
        System.out.println("Valeur actuelle du portefeuille : " + currentValue + "$");
        System.out.println("Gain / Perte : " + gainOrLoss + "$");
    }

//< --------------- partie 2 : --------------------->

    public void getTransactionOfTrader(String traderID){
        Trader traderTofind = findTrader(traderID);
        if (traderTofind == null) {
            System.out.println("Trader non trouvé");
            return;
        }

      List<Transaction> traderTransactions  = transactions.stream()
              .filter(t -> t.getTraderId().equals(traderID))
              .toList();

        if (traderTransactions.isEmpty()) {
            System.out.println("Aucune transaction trouvée");
            return;
        }
        System.out.println("---- Transactions du trader " + traderTofind.getName() + " ----");
        traderTransactions.forEach(System.out::println);

    }



    public void filterTransactions(
            String traderId,
            String type,
            String assetCode,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {

        List<Transaction> result = transactions.stream()
                .filter(t ->  t.getTraderId().equals(traderId))
                .filter(t ->  t.getType().equalsIgnoreCase(type))
                .filter(t ->  t.getAssetCode().equalsIgnoreCase(assetCode))
                .filter(t -> !t.getDate().isBefore(startDate))
                .filter(t -> !t.getDate().isAfter(endDate))
                .toList();

        if (result.isEmpty()) {
            System.out.println("Aucune transaction trouvée avec ces critères");
            return;
        }

        System.out.println("---- Transactions filtrées ----");
        result.forEach(System.out::println);
    }

    public void sortBydate(){
        List<Transaction> sortedtransactionList = transactions.stream()
                .sorted(Comparator.comparing(Transaction::getDate))
                .toList();

        if ( sortedtransactionList.isEmpty()){
            System.out.println("Aucune transaction à trier");
            return;
        }

        System.out.println("---- Transactions triées par date ----");
        sortedtransactionList.forEach(System.out::println);
    }

    public void sortByMontant(){
        List<Transaction> sortedList = transactions.stream()
                .sorted(Comparator.comparing(t -> t.getPrice() * t.getQuantity())).toList();

        if( sortedList.isEmpty()){
            System.out.println("Aucune transaction à trier");
            return;
        }
        System.out.println("---- Transactions triées par Montant ----");
        sortedList.forEach(System.out::println);
    }

public void getTotalVolumeTraded(){
      Map< String , Integer> volume = transactions.stream()
              .collect(groupingBy(Transaction::getAssetCode , summingInt(Transaction::getQuantity)));

      volume.forEach((asset , qty) ->
              System.out.println("Asset: " + asset + " | Volume total: " + qty));


}

public void getTotalAmountTradedByBuy(){
        Map <String , Double > totalBuyAmount = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("BUY"))
                .collect(
                        groupingBy(
                                t -> t.getAssetCode(), summingDouble(t -> t.getPrice() * t.getQuantity())
                        )
                );
    totalBuyAmount.forEach((asset, amount) ->
            System.out.println("Asset: " + asset + " | Montant total des achats: " + amount)
    );
}


    public void getTotalAmountTradedBySell(){
        Map<String , Double> totalSellAmount = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("SELL"))
                .collect(
                        groupingBy(
                                t-> t.getAssetCode() , summingDouble(t -> t.getPrice() * t.getQuantity())
                        )
                );

        totalSellAmount.forEach((asset, amount) ->
                System.out.println("Asset: " + asset + " | Montant total des ventes: " + amount)
        );
    }


    public void getTotalVolumeByTrader(){
        Map<String , Integer> totalVolumeByTrader = transactions.stream()
                .collect(
                        groupingBy(Transaction::getTraderId , summingInt(Transaction::getQuantity))
                );
        totalVolumeByTrader.forEach((trader, volume) ->
                System.out.println("Trader ID: " + trader + " | Volume total échangé: " + volume)
        );
    }

    public void getTotalOrders() {
        long totalOrders = transactions.stream().count();
        System.out.println("Nombre total d’ordres passés : " + totalOrders);
    }

    public void getTopTradersByVolume(int topN){
        Map<String , Integer> volumeByTrader = transactions.stream()
                .collect(
                        groupingBy(Transaction::getTraderId ,
                                summingInt(Transaction::getQuantity)

                        ));

        List< Map.Entry<String , Integer>> topTraders = volumeByTrader.entrySet().stream()
                .sorted((e1 , e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(topN)
                .toList();

        System.out.println("----- Top " + topN + " Traders par volume -----");
        topTraders.forEach(e ->
                System.out.println("Trader ID: " + e.getKey() + " | Volume total: " + e.getValue())
        );
    }



    public void getTotalVolumeByInstrument(){
        Map<String , Integer> volumeByInstrument = transactions.stream()
                .collect(
                        groupingBy(
                                t -> t.getAssetCode() ,
                                summingInt( t -> t.getQuantity())
                        )
                );

        System.out.println("---- Volume total échangé par instrument ----");
        volumeByInstrument.forEach((assetCode , volume) ->
                        System.out.println("Instrument: " + assetCode  + " | Volume total: " + volume)
                );
    }

    public void getMostTradedInstrument(){
        Map<String , Integer>  volumeByInstrument = transactions.stream()
                .collect(
                        groupingBy(
                                t -> t.getAssetCode() ,
                                summingInt(t -> t.getQuantity())
                        )
                );
        volumeByInstrument.entrySet().stream()
                .sorted((e1 , e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(1)
                .forEach(e ->
                                System.out.println(
                                        "Instrument le plus échangé : "
                                                + e.getKey()
                                                + " | Volume total : "
                                                + e.getValue()
                                ));
    }



    public void getTotalBuyAndSellAmounts(){
        double totalBuy = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("BUY"))
                .mapToDouble(t -> t.getPrice() * t.getQuantity())
                .sum();

        double totalSell = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("SELL"))
                .mapToDouble(t -> t.getPrice() * t.getQuantity())
                .sum();

        System.out.println("Montant total des BUY  : " + totalBuy);
        System.out.println("Montant total des SELL : " + totalSell);

    }
}
