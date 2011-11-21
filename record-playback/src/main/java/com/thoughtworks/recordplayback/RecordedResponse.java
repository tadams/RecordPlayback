package com.thoughtworks.recordplayback;

import java.io.Serializable;
import java.util.Date;

public class RecordedResponse implements Serializable {

    private Date        timeStamp;
    private Object      response;
    private Throwable   thrown;
    private Long        responseLatencyMilliseconds;

    public RecordedResponse(Throwable thrown, Long responseLatencyMilliseconds) {
        this.thrown     = thrown;
        this.responseLatencyMilliseconds = responseLatencyMilliseconds;
        this.timeStamp  = new Date();
    }

    public RecordedResponse(Object response, Long responseLatencyMilliseconds) {
        this.response   = response;
        this.responseLatencyMilliseconds = responseLatencyMilliseconds;
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

    public boolean hasLatency() {
        return responseLatencyMilliseconds > 0;
    }

    public Long getLatencyMilliseconds() {
        return responseLatencyMilliseconds;
    }
}
