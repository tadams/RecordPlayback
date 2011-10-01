package com.thoughtworks.recordplayback.priceapi;


public class Item {
    private String name;
    private double price;
    private String itemNumber;

    public Item(String name, double price, String itemNumber) {
        this.name = name;
        this.price = price;
        this.itemNumber = itemNumber;
    }
}
