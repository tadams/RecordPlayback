package com.thoughtworks.recordplayback;


import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {

    String API_CACHE_FILE_NAME = "/tmp/recordPlayback.ser";
    //TODO: Expand to contain a map of maps based on the JoinPointId
    private Map<List, RecordedResponse> apiCache = new HashMap<List, RecordedResponse>();


    public RecordedResponse get(Object[] arguments) {

        if (apiCache.isEmpty()) {
            apiCache = initApiCache();
        }
        return apiCache.get(Arrays.asList(arguments));
    }

    public void save(Object[] arguments, RecordedResponse recordedResponse) {
        apiCache.put(Arrays.asList(arguments), recordedResponse);
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

    private Map<List, RecordedResponse> initApiCache() {

        try {

            File apiFile = new File(API_CACHE_FILE_NAME);
            if (apiFile.isFile() == false) {
                return new HashMap<List, RecordedResponse>();
            }

            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(apiFile));
            return (Map<List, RecordedResponse>) objectInputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
