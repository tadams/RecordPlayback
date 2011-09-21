package com.thoughtworks.recordplayback.invoiceapi;

public interface InvoiceService {

    public Invoice buildInvoice(String transactionId, Order order);

}
