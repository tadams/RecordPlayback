package com.thoughtworks.recordplayback.invoiceapi;

import com.thoughtworks.recordplayback.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/appContext.xml")
public class InvoiceServiceTest {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private RecordPlaybackInterceptor interceptor;

    private Cache cache = new Cache();

    private SimpleRecordPlaybackImpls simpleRecordPlaybackImpls = new SimpleRecordPlaybackImpls(cache);

    @Before
    public void given() {
        interceptor.setRecordHandler(simpleRecordPlaybackImpls);
        interceptor.setPlaybackHandler(simpleRecordPlaybackImpls);

        Map<String, RequestNormalizer> requestNormalizerMap = new HashMap<String, RequestNormalizer>();
        requestNormalizerMap.put("com.thoughtworks.recordplayback.invoiceapi.InvoiceService:buildInvoice",
                new InvoiceServiceRequestNormalizer());
        interceptor.setRequestNormalizer(requestNormalizerMap);

        Map<String, ResponseModifier> responseModifierMap = new HashMap<String, ResponseModifier>();
        responseModifierMap.put("com.thoughtworks.recordplayback.invoiceapi.InvoiceService:buildInvoice",
                new InvoiceServiceResponseModifier());
        interceptor.setResponseModifier(responseModifierMap);
    }

    @Test
    public void shouldRecordAndPlayback1API() {
        interceptor.setRunMode(RunMode.RECORD);

        Order order = new Order(123, new Date(), getProductList());
        Invoice invoice1 = invoiceService.buildInvoice("123", order);

        interceptor.setRunMode(RunMode.PLAYBACK);

        Invoice invoice2 = invoiceService.buildInvoice("321", order);
        // Invoice date was modified
        assertFalse(invoice1.equals(invoice2));

        assertEquals(invoice1.getInvoiceTotalDollars(), invoice2.getInvoiceTotalDollars());
        assertEquals(invoice1.getNumberOfProducts(), invoice2.getNumberOfProducts());
    }

    private List<Product> getProductList() {
        List<Product> products = new ArrayList<Product>();
        products.add(new Product("Pen", 100, new Date(), new BigDecimal("1.23")));
        products.add(new Product("Pencil", 101, new Date(), new BigDecimal("0.23")));
        return products;
    }


}
