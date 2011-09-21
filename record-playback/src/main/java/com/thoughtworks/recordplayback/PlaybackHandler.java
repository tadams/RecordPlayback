package com.thoughtworks.recordplayback;

public interface PlaybackHandler {

    public Object getRecordedResponse(String joinPointId, Object[] arguments);


}
