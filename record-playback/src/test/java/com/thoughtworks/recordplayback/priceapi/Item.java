package com.thoughtworks.recordplayback.priceapi;


import java.io.Serializable;

public class Item implements Serializable{
    private String name;
    private double price;
    private String itemNumber;

    public Item(String name, double price, String itemNumber) {
        this.name = name;
        this.price = price;
        this.itemNumber = itemNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (Double.compare(item.price, price) != 0) return false;
        if (itemNumber != null ? !itemNumber.equals(item.itemNumber) : item.itemNumber != null) return false;
        if (name != null ? !name.equals(item.name) : item.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = price != +0.0d ? Double.doubleToLongBits(price) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (itemNumber != null ? itemNumber.hashCode() : 0);
        return result;
    }
}
