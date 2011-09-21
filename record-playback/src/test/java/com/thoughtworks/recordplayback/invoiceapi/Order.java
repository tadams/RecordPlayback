package com.thoughtworks.recordplayback.invoiceapi;

import org.apache.commons.lang.ObjectUtils;

import java.util.Date;
import java.util.List;

public class Order {

    private Integer orderId;
    private Date    orderTimeStamp;
    private Date    promisedDate;

    private List<Product> products;

    public Order(Integer orderId, Date orderPromisedDate, List<Product> products) {
        this.orderTimeStamp     = new Date();
        this.orderId            = orderId;
        this.promisedDate       = orderPromisedDate;
        this.products           = products;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Date getOrderTimeStamp() {
        return orderTimeStamp;
    }

    public Date getPromisedDate() {
        return promisedDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other instanceof Order == false) return false;

        Order order = (Order) other;

        return ObjectUtils.equals(orderId, order.orderId)                 &&
               ObjectUtils.equals(orderTimeStamp, order.orderTimeStamp)   &&
               ObjectUtils.equals(promisedDate, order.promisedDate);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCode(orderId)     +
               ObjectUtils.hashCode(orderTimeStamp) +
               ObjectUtils.hashCode(promisedDate);
    }
}
