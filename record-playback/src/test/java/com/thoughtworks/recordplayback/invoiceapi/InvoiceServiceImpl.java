package com.thoughtworks.recordplayback.invoiceapi;

public class InvoiceServiceImpl implements InvoiceService {

    public Invoice buildInvoice(String transactionId, Order order) {
        return new Invoice(order);
    }
}
