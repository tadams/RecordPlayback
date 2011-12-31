package com.thoughtworks.recordplayback;

import java.io.Serializable;

public class RequestWrapper implements Serializable {

    private Object[] requestArguments;

    public RequestWrapper(Object... requestArguments) {
        this.requestArguments = requestArguments;
    }

    @Override
    public boolean equals(Object other) {

        if (other == null || other instanceof RequestWrapper == false) {
            return false;
        }
        RequestWrapper otherRequestWrapper = (RequestWrapper)other;

        return DeepEquals.deepEquals(requestArguments,
                                     otherRequestWrapper.requestArguments);
    }

    @Override
    public int hashCode() {
        return DeepEquals.deepHashCode(requestArguments);
    }

}
