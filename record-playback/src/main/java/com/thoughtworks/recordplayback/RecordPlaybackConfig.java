package com.thoughtworks.recordplayback;

public class RecordPlaybackConfig {

    public static RecordPlaybackConfig EMPTY_CONFIG = new RecordPlaybackConfig();

    private RunMode runMode     = RunMode.OFF;

    public RunMode getRunMode() {
        return runMode;
    }

    public void setRunMode(RunMode runMode) {
        this.runMode = runMode;
    }

}
