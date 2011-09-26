package com.thoughtworks.recordplayback;

import java.io.Serializable;
import java.util.Date;

public class RecordedResponse implements Serializable {

    private Date        timeStamp;
    private Object      response;
    private Throwable   thrown;

    public RecordedResponse(Throwable thown) {
        this.thrown     = thown;
        this.timeStamp  = new Date();
    }

    public RecordedResponse(Object response) {
        this.response   = response;
        this.timeStamp  = new Date();
    }

    public boolean isException() {
        return thrown != null;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public Object getResponse() {
        return response;
    }

    public Throwable getException() {
        return thrown;
    }
}
