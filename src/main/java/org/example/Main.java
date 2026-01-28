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
            System.out.println("4. Créer un portefeuille");
            System.out.println("5. Consulter portefeuille");
            System.out.println("6. Acheter un actif");
            System.out.println("7. Vendre un actif");
            System.out.println("8. Historique des transactions");
            System.out.println("0. Quitter");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    platform.addAsset(new Stock("AAPL" , "Apple" , 300 ));
                    break;
               case 2:
                    platform.showAssets();
                    break;

                case 3:
                    platform.addTrader(new Trader("trd1","Aymen" , 1000));
                    break;
                       /*
                case 4:
                    // création automatique lors de l’ajout du trader
                    System.out.println("Portefeuille créé.");
                    break;
                case 5:
                    // afficher portefeuille
                    break;
                case 6:
                    platform.buyAsset( trader, asset, qty );
                    break;
                case 7:
                    platform.sellAsset( trader, asset, qty );
                    break;
                case 8:
                    platform.showTransactions();
                    break;*/
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Choix invalide");
            }
        }
        sc.close();
    }
}
