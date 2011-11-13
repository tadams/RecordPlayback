package com.thoughtworks.recordplayback.Normalizer;

import com.thoughtworks.recordplayback.RequestNormalizer;

public class TransactionIdentifierNormalizer implements RequestNormalizer {

    public Object[] normalize(String joinPointId, Object[] objects) {

        objects[1] = "";
        return objects;
    }

}
