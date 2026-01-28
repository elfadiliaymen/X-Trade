package org.example;

public class Stock extends Asset{

    public Stock(String code, String name, double price) {
        super(code, name, price , "STOCK");
    }
}
