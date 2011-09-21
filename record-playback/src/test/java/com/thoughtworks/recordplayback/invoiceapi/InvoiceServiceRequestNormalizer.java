package com.thoughtworks.recordplayback.invoiceapi;

import com.thoughtworks.recordplayback.RequestNormalizer;

public class InvoiceServiceRequestNormalizer implements RequestNormalizer {

    public Object[] normalize(String joinPointId, Object[] arguments) {

        String transactionId    = normalizeTransactionId(arguments);
        Order order             = (Order)arguments[1];

        return new Object[] { transactionId, order };
    }

    private String normalizeTransactionId(Object[] arguments) {
        if (arguments.length < 1 || arguments[0] == null) {
            return null;
        }
        return "";
    }
}
