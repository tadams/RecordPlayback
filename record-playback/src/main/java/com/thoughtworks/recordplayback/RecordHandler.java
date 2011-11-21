package com.thoughtworks.recordplayback;

import org.apache.commons.lang.time.StopWatch;

public interface RecordHandler {

    //TODO: create a single request object to feed into recordAPI with isExeceptionThrown method
    public void recordAPI(StopWatch stopWatch, String joinPointId, RequestWrapper request, Object response, Throwable thrown);

    public void endRecord();

}
