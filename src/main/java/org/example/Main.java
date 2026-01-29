package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TradingPlatform platform = new TradingPlatform("XTrade");
        Scanner sc = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println("\n=== XTrade Simulator ===");
            System.out.println("1. Ajouter un actif");
            System.out.println("2. Afficher les actifs");
            System.out.println("3. Ajouter un trader");
            System.out.println("4. Consulter portefeuille");
            System.out.println("5. Acheter un actif");
            System.out.println("6. Vendre un actif");
            System.out.println("7. Historique des transactions");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Code de l'actif : ");
                    String code = sc.next();

                    System.out.print("Nom de l'actif : ");
                    String name = sc.next();

                    System.out.print("Prix : ");
                    double price = sc.nextDouble();

                    System.out.print("Type (1 = Stock, 2 = Crypto) : ");
                    int type = sc.nextInt();

                    try {
                        if (type == 1) {
                            platform.addAsset(new Stock(code, name, price));
                        } else if (type == 2) {
                            platform.addAsset(new CryptoCurency(code, name, price));
                        } else {
                            System.out.println("Type invalide");
                        }
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
               case 2:
                    platform.showAssets();
                    break;

                case 3:
                    System.out.print("ID Trader : ");
                    String traderId = sc.next();

                    System.out.print("Nom Trader : ");
                    String traderName = sc.next();

                    System.out.print("Solde initial : ");
                    double balance = sc.nextDouble();

                    try {
                        Trader trader = new Trader(traderId, traderName, balance);
                        platform.addTrader(trader);
                        System.out.println("Trader ajouté avec succès.");
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("ID Trader : ");
                    String idPortfolio = sc.next();
                    platform.showPortfolio(idPortfolio);
                    break;

                case 5:
                    System.out.print("ID Trader : ");
                    String buyTraderId = sc.next();

                    System.out.print("Code Actif : ");
                    String buyAssetCode = sc.next();

                    System.out.print("Quantité : ");
                    int buyQty = sc.nextInt();

                    try {
                        platform.wantsTobuy(buyTraderId, buyAssetCode, buyQty);
                        System.out.println("Achat effectué.");
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.print("ID Trader : ");
                    String sellTraderId = sc.next();

                    System.out.print("Code Actif : ");
                    String sellAssetCode = sc.next();

                    System.out.print("Quantité : ");
                    int sellQty = sc.nextInt();

                    try {
                        platform.wantsToSell(sellTraderId, sellAssetCode, sellQty);
                        System.out.println("Vente effectuée.");
                    } catch (Exception e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;

                case 7:
                    platform.showTransactions();
                    break;

                case 0:
                    running = false;
                    System.out.println("Au revoir");
                    break;
                default:
                    System.out.println("Choix invalide");
            }
        }
        sc.close();
    }
}
