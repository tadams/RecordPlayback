package com.thoughtworks.recordplayback;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleRecordPlaybackImpls implements RecordHandler, PlaybackHandler {

    private Map<List, Object>       apiCache = new HashMap<List, Object>();


    public Object getRecordedResponse(Object[] arguments) {
        return apiCache.get(Arrays.asList(arguments));
    }

    public void recordAPI(Object[] arguments, Object response) {
        apiCache.put(Arrays.asList(arguments), response);
    }

    public void endRecord() {
        // better impl might persist the apiCache
    }
}
