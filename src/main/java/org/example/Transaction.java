package org.example;

import java.time.LocalDateTime;

public class Transaction {

    private String type;
    private int quantity;
    private double price;
    private LocalDateTime date;

    public Transaction(String type, int quantity, double price, LocalDateTime date) {
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
