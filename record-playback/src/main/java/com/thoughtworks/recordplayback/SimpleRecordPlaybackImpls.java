package com.thoughtworks.recordplayback;

public class SimpleRecordPlaybackImpls implements RecordHandler, PlaybackHandler {

    private Cache cache;


    public SimpleRecordPlaybackImpls(Cache cache) {
        this.cache = cache;
    }

    public RecordedResponse getRecordedResponse(String joinPointId, RequestWrapper request) {
        return cache.get(request);
    }

    public void recordAPI(String joinPointId, RequestWrapper request, Object response, Throwable thrown) {

        RecordedResponse recordedResponse = null;

        if (thrown == null) {
            recordedResponse = new RecordedResponse(response);
        } else {
            recordedResponse = new RecordedResponse(thrown);
        }

        cache.save(request, recordedResponse);
    }

    public void endRecord() {
        cache.saveAsFile();
        cache.clear();
    }

}
