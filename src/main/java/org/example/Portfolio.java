package org.example;

public class Portfolio <T> {
    private int  portID;
    private Trader trader;

    public Portfolio(int portID){
     this.portID = portID;
    }

    public void TraderPortfolio(){
        System.out.println("trader" + trader.getName() + "|" + trader.getBalance());
    }

}
