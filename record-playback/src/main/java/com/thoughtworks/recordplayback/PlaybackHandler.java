package com.thoughtworks.recordplayback;

public interface PlaybackHandler {

    public RecordedResponse getRecordedResponse(String joinPointId, Object[] arguments);

}
