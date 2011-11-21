package com.thoughtworks.recordplayback.testservice;

public interface SimpleApi {
    public String getResult(Integer integer, String string);

    public String getResultWithLatency(Integer integer, String string, Integer latency);
}
