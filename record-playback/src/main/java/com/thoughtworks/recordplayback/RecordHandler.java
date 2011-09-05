package com.thoughtworks.recordplayback;

public interface RecordHandler {

    public void recordAPI(Object[] arguments, Object response);

    public void endRecord();

}
