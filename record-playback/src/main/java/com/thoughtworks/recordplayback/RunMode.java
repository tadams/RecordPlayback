package com.thoughtworks.recordplayback;

public enum RunMode {
    OFF, RECORD, PLAYBACK, DEBUG;

    public boolean isDebug() {
        return this == DEBUG;
    }

}
