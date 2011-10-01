package com.thoughtworks.recordplayback.priceapi;

import com.thoughtworks.recordplayback.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/appContext.xml")
public class PriceServiceTest {

    @Autowired
    private PricingService priceService;

    @Autowired
    private RecordPlaybackInterceptor interceptor;

    private SimpleRecordPlaybackImpls simpleRecordPlaybackImpls = new SimpleRecordPlaybackImpls();

    private RecordPlaybackConfig config = new RecordPlaybackConfig();

    @Before
    public void given() {
        interceptor.setRecordPlaybackConfig(config);
        interceptor.setRecordHandler(simpleRecordPlaybackImpls);
        interceptor.setPlaybackHandler(simpleRecordPlaybackImpls);

        Map<String, RequestNormalizer> requestNormalizerMap = new HashMap<String, RequestNormalizer>();
        requestNormalizerMap.put("com.thoughtworks.recordplayback.priceapi.PricingService:searchForItems",
                                 new PriceServiceRequestNormalizer());
        simpleRecordPlaybackImpls.setRequestNormalizer(requestNormalizerMap);
    }

    @Test
    public void shouldRecordAndPlayback1API() {

        config.setRunMode(RunMode.RECORD);
        PriceRequest priceRequest1 = new PriceRequest(generateTransactionId(),"Snowboard",100.00, 500.00);
        priceService.searchForItems(priceRequest1);

        config.setRunMode(RunMode.PLAYBACK);
        PriceRequest priceRequest2 = new PriceRequest(generateTransactionId(),"Snowboard",100.00, 500.00);
        priceService.searchForItems(priceRequest2);
    }



    public String generateTransactionId() {
        Random randomGenerator = new Random();
        return  "1234" + randomGenerator.nextInt(1);
    }



}
