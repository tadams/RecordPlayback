package com.thoughtworks.recordplayback.javadynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MyAspect implements InvocationHandler {

    private Object advisedInstance;
    private Map<String, Integer> methodMap = new HashMap<String, Integer>();

    public MyAspect(Object advisedInstance) {
        this.advisedInstance = advisedInstance;
    }

    public Object invoke(Object advise, Method method, Object[] arguments) throws Throwable {

        countMethodCall(method.getName());

        return method.invoke(advisedInstance, arguments);
    }

    private void countMethodCall(String methodName) {
        Integer methodCount = methodMap.get(methodName);
        methodCount = methodCount == null ? 1 : methodCount + 1;
        methodMap.put(methodName, methodCount);
    }

    public void printMethodCount() {
        for (Map.Entry<String, Integer> mapEntry : methodMap.entrySet()) {
            System.out.println(mapEntry.getKey() + " count: " + mapEntry.getValue());
        }
    }

    public Integer getCount(String methodName) {
        return methodMap.get(methodName);
    }
}
