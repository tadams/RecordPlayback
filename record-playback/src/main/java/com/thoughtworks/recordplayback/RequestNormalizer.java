package com.thoughtworks.recordplayback;

public interface RequestNormalizer {

    public Object[] normalize(String joinPointId, Object[] arguments);

}
