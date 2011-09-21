package com.thoughtworks.recordplayback.invoiceapi;

import org.apache.commons.lang.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Invoice {

    private Date invoiceDate;
    private Integer numberOfProducts;
    private BigDecimal invoiceTotalDollars = BigDecimal.ZERO;

    public Invoice(Order orderToInvoice) {
        invoiceDate = new Date();
        List<Product> products = orderToInvoice.getProducts();
        numberOfProducts = products.size();
        for (Product product : products) {
            invoiceTotalDollars = invoiceTotalDollars.add(product.getPriceDollars());
        }
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public Integer getNumberOfProducts() {
        return numberOfProducts;
    }

    public BigDecimal getInvoiceTotalDollars() {
        return invoiceTotalDollars;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other instanceof Invoice == false) return false;

        Invoice invoice = (Invoice) other;

        return ObjectUtils.equals(invoiceDate, invoice.invoiceDate)                 &&
               ObjectUtils.equals(numberOfProducts, invoice.numberOfProducts)       &&
               ObjectUtils.equals(invoiceTotalDollars, invoice.invoiceTotalDollars);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(invoiceDate)     +
               ObjectUtils.hashCode(numberOfProducts) +
               ObjectUtils.hashCode(invoiceTotalDollars);
    }
}
