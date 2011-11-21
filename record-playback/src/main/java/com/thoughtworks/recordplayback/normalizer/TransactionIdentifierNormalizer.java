package com.thoughtworks.recordplayback.normalizer;

import com.thoughtworks.recordplayback.RequestNormalizer;

public class TransactionIdentifierNormalizer implements RequestNormalizer {

    public Object[] normalize(String joinPointId, Object[] objects) {

        objects[1] = "";
        return objects;
    }

}
