package com.thoughtworks.recordplayback.priceapi;


import java.util.List;

public class PriceResponse {

    private String transactionId;
    List<Item> items;


    public PriceResponse(String transactionId, List<Item> items) {
        this.transactionId = transactionId;
        this.items = items;
    }
}
