package com.thoughtworks.recordplayback.javadynamicproxy;

import com.thoughtworks.recordplayback.testservice.SimpleApi;
import com.thoughtworks.recordplayback.testservice.SimpleApiImpl;

import java.lang.reflect.Proxy;

public class SimpleApiFactory {

    public static SimpleApi newSimpleApi() {

        return (SimpleApi) Proxy.newProxyInstance(SimpleApi.class.getClassLoader(),
                new Class[]{SimpleApi.class},
                new MyAspect(new SimpleApiImpl()));

    }
}
