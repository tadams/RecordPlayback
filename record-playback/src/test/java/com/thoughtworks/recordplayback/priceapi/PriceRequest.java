package com.thoughtworks.recordplayback.priceapi;


import java.io.Serializable;

public class PriceRequest implements Serializable{

    private String transactionId;
    private String itemType;
    private double minimum;


    public PriceRequest(String transactionId, String itemType, double minimum, double maximum) {
        this.transactionId = transactionId;
        this.itemType = itemType;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public String getTransactionId(){
        return transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceRequest that = (PriceRequest) o;

        if (Double.compare(that.maximum, maximum) != 0) return false;
        if (Double.compare(that.minimum, minimum) != 0) return false;
        if (itemType != null ? !itemType.equals(that.itemType) : that.itemType != null) return false;
        if (transactionId != null ? !transactionId.equals(that.transactionId) : that.transactionId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = transactionId != null ? transactionId.hashCode() : 0;
        result = 31 * result + (itemType != null ? itemType.hashCode() : 0);
        temp = minimum != +0.0d ? Double.doubleToLongBits(minimum) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = maximum != +0.0d ? Double.doubleToLongBits(maximum) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    private double maximum;

}
