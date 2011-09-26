package com.thoughtworks.recordplayback;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleRecordPlaybackImpls implements RecordHandler, PlaybackHandler {

    String API_CACHE_FILE_NAME = "/tmp/recordPlayback.ser";

    //TODO: Expand to contain a map of maps based on the JoinPointId
    private Map<List, RecordedResponse>       apiCache = new HashMap<List, RecordedResponse>();

    public RecordedResponse getRecordedResponse(String joinPointId, Object[] arguments) {

        if (apiCache.isEmpty()) {
            loadApiCache();
        }

        return apiCache.get(Arrays.asList(arguments));
    }

    public void recordAPI(String joinPointId, Object[] arguments, Object response, Throwable thrown) {

        RecordedResponse recordedResponse = null;

        if (thrown == null) {
            recordedResponse = new RecordedResponse(response);
        } else {
            recordedResponse = new RecordedResponse(thrown);
        }

        apiCache.put(Arrays.asList(arguments), recordedResponse);
    }

    public void endRecord() {
        persistApiCache();
        apiCache.clear();
    }

    //TODO: Support named directory/file - Spring driven?
    private void persistApiCache() {

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(API_CACHE_FILE_NAME));
            objectOutputStream.writeObject(apiCache);
            objectOutputStream.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadApiCache() {

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(API_CACHE_FILE_NAME));
            apiCache = (Map<List, RecordedResponse>)objectInputStream.readObject();
        } catch(IOException e) {
            throw new RuntimeException(e);
        } catch(ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
