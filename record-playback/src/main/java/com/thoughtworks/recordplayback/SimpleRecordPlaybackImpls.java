package com.thoughtworks.recordplayback;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleRecordPlaybackImpls implements RecordHandler, PlaybackHandler {

    private Map<List, Object>       apiCache = new HashMap<List, Object>();
    private Map<String, RequestNormalizer> normalizerMap = new HashMap<String, RequestNormalizer>();


    public Object getRecordedResponse(String joinPointId, Object[] arguments) {
        arguments = normalizeRequest(joinPointId, arguments);
        return apiCache.get(Arrays.asList(arguments));
    }

    public void recordAPI(String joinPointId, Object[] arguments, Object response) {
        arguments = normalizeRequest(joinPointId, arguments);
        apiCache.put(Arrays.asList(arguments), response);
    }

    private Object[] normalizeRequest(String joinPointId, Object[] arguments) {
        RequestNormalizer requestNormalizer = normalizerMap.get(joinPointId);
        if (requestNormalizer != null) {
            return requestNormalizer.normalize(joinPointId, arguments);
        }
        return arguments;
    }

    public void endRecord() {
        // better impl might persist the apiCache
    }

    public void setRequestNormalizer(Map<String, RequestNormalizer> normalizerMap) {
        this.normalizerMap = normalizerMap;
    }
}
