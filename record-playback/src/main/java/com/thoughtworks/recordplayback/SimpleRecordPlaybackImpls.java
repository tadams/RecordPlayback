package com.thoughtworks.recordplayback;

public class SimpleRecordPlaybackImpls implements RecordHandler, PlaybackHandler {

    private Cache cache;


    public SimpleRecordPlaybackImpls(Cache cache) {
        this.cache = cache;
    }

    public RecordedResponse getRecordedResponse(String joinPointId, Object[] arguments) {
        return cache.get(arguments);
    }

    public void recordAPI(String joinPointId, Object[] arguments, Object response, Throwable thrown) {

        RecordedResponse recordedResponse = null;

        if (thrown == null) {
            recordedResponse = new RecordedResponse(response);
        } else {
            recordedResponse = new RecordedResponse(thrown);
        }

        cache.save(arguments, recordedResponse);
    }

    public void endRecord() {
        cache.saveAsFile();
        cache.clear();
    }

}
