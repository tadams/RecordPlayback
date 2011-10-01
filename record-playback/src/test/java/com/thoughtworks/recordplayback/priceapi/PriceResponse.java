package com.thoughtworks.recordplayback.priceapi;


import java.io.Serializable;
import java.util.List;

public class PriceResponse implements Serializable{

    private String transactionId;
    private List<Item> items;


    public List<Item> getItems() {
        return items;
    }

    public PriceResponse(String transactionId, List<Item> items) {
        this.transactionId = transactionId;
        this.items = items;
    }
}
