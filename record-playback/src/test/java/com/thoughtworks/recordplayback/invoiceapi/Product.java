package com.thoughtworks.recordplayback.invoiceapi;

import org.apache.commons.lang.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;

public class Product {

    private String      productName;
    private Integer     inventoryCount;
    private Date        inventoryTimeStamp;
    private BigDecimal  priceDollars;

    public Product(String productName, Integer inventoryCount, Date inventoryTimeStamp, BigDecimal priceDollars) {
        this.productName = productName;
        this.inventoryCount = inventoryCount;
        this.inventoryTimeStamp = inventoryTimeStamp;
        this.priceDollars = priceDollars;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(Integer inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    public Date getInventoryTimeStamp() {
        return inventoryTimeStamp;
    }

    public void setInventoryTimeStamp(Date inventoryTimeStamp) {
        this.inventoryTimeStamp = inventoryTimeStamp;
    }

    public BigDecimal getPriceDollars() {
        return priceDollars;
    }

    public void setPriceDollars(BigDecimal priceDollars) {
        this.priceDollars = priceDollars;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other instanceof Product == false) return false;

        Product product = (Product) other;

        return ObjectUtils.equals(inventoryCount, product.inventoryCount)           &&
               ObjectUtils.equals(inventoryTimeStamp, product.inventoryTimeStamp)   &&
               ObjectUtils.equals(priceDollars, product.priceDollars)               &&
               ObjectUtils.equals(productName, product.productName);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(inventoryCount)     +
               ObjectUtils.hashCode(inventoryTimeStamp) +
               ObjectUtils.hashCode(priceDollars)       +
               ObjectUtils.hashCode(productName);
    }
}
