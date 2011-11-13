package com.thoughtworks.recordplayback;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Cache {

    String API_CACHE_FILE_NAME = "/tmp/recordPlayback.ser";
    //TODO: Expand to contain a map of maps based on the JoinPointId
    private Map<RequestWrapper, RecordedResponse> apiCache = new HashMap<RequestWrapper, RecordedResponse>();


    public RecordedResponse get(RequestWrapper request) {

        if (apiCache.isEmpty()) {
            apiCache = initApiCache();
        }
        return apiCache.get(request);
    }

    public void save(RequestWrapper request, RecordedResponse recordedResponse) {
        apiCache.put(request, recordedResponse);
    }

    //TODO: Support named directory/file - Spring driven?
    public void saveAsFile() {
         try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(API_CACHE_FILE_NAME));
            objectOutputStream.writeObject(apiCache);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        apiCache.clear();
    }

    private Map<RequestWrapper, RecordedResponse> initApiCache() {

        try {

            File apiFile = new File(API_CACHE_FILE_NAME);
            if (apiFile.isFile() == false) {
                return new HashMap<RequestWrapper, RecordedResponse>();
            }

            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(apiFile));
            return (Map<RequestWrapper, RecordedResponse>) objectInputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
