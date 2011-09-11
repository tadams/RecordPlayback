package com.thoughtworks.recordplayback.javadynamicproxy;

import com.thoughtworks.recordplayback.testservice.SimpleApi;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class MyAspectTest {

    SimpleApi simpleApi;

    @Before
    public void given() {
        simpleApi = SimpleApiFactory.newSimpleApi();
    }

    @Test
    public void shouldCount3Invocations() {

        assertNotNull(simpleApi.getResult(100, "Foo1"));
        assertNotNull(simpleApi.getResult(200, "Foo2"));
        assertNotNull(simpleApi.getResult(300, "Foo3"));

        MyAspect myAspect = getAspect();
        assertSame(3, myAspect.getCount("getResult"));
        myAspect.printMethodCount();
    }

    private MyAspect getAspect() {
        return (MyAspect) Proxy.getInvocationHandler(simpleApi);
    }

}
