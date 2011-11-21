package com.thoughtworks.recordplayback.testservice;

import java.util.Random;

public class SimpleApiImpl implements SimpleApi {

    Random randomGenerator = new Random();

    public String getResult(Integer integer, String string) {

        if ("Exception".equals(string)) {
            throw new RuntimeException();
        }

        return string + randomGenerator.nextInt(integer);
    }

    public String getResultWithLatency(Integer integer, String string, Integer latency) {
        try {
            Thread.sleep(latency);
        } catch (InterruptedException ignore) {
        }
        return getResult(integer, string);
    }
}
