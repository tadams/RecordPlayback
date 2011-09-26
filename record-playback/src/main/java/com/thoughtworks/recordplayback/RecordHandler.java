package com.thoughtworks.recordplayback;

public interface RecordHandler {

    public void recordAPI(String joinPointId, Object[] arguments, Object response, Throwable thrown);

    public void endRecord();

}
