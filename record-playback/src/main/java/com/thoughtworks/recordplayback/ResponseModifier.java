package com.thoughtworks.recordplayback;

public interface ResponseModifier {

    public Object modify(String joinPointId,
                         RecordedResponse recordedResponse,
                         Object[] arguments);

}
