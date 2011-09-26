package com.thoughtworks.recordplayback.invoiceapi;

import com.thoughtworks.recordplayback.RecordedResponse;
import com.thoughtworks.recordplayback.ResponseModifier;

import java.util.Date;

public class InvoiceServiceResponseModifier implements ResponseModifier {

    public Object modify(String joinPointId, RecordedResponse recordedResponse, Object[] arguments) {
        Invoice invoice = (Invoice)recordedResponse.getResponse();
        Date newInvoiceDate = applyDateAdjustment(recordedResponse.getTimeStamp(), invoice.getInvoiceDate(), new Date());
        invoice.setInvoiceDate(newInvoiceDate);

        return invoice;
    }

    private Date applyDateAdjustment(Date origDate1, Date origDate2, Date newDate1) {
        Long adjustment = origDate2.getTime() - origDate1.getTime();
        return new Date(newDate1.getTime() + adjustment);
    }

}
