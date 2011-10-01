package com.thoughtworks.recordplayback.priceapi;

public interface PricingService {

    public PriceResponse searchForItems(PriceRequest request);
}
