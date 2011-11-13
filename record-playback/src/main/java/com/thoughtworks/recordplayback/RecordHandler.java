package com.thoughtworks.recordplayback;

public interface RecordHandler {

    public void recordAPI(String joinPointId, RequestWrapper request, Object response, Throwable thrown);

    public void endRecord();

}
