package com.thoughtworks.recordplayback.priceapi;


import java.util.Arrays;

public class PricingServiceImpl implements PricingService{

    public PriceResponse searchForItems(PriceRequest request) {

       Item item1 = new Item("Awesome Snowboard", 300.00, "34567");
       Item item2 = new Item("More Awesome Snowboard", 500.00, "34563");
       Item item3 = new Item("Black/Purple Snowboard", 400.00, "23453");
       return new PriceResponse(request.getTransactionId() + "1", Arrays.asList(new Item[]{item1, item2, item3}));
    }
}
