package com.thoughtworks.recordplayback.config;

import com.thoughtworks.recordplayback.RecordPlaybackInterceptor;
import com.thoughtworks.recordplayback.RunMode;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "com.thoughtworks.recordplayback:name=RecordPlayBackRunMode")
public class RunModeToggle {

    private RecordPlaybackInterceptor recordPlaybackInterceptor;

    public RunModeToggle(RecordPlaybackInterceptor recordPlaybackInterceptor) {
        this.recordPlaybackInterceptor = recordPlaybackInterceptor;
    }

    @ManagedOperation
    public void setRunModeDebug() {
        recordPlaybackInterceptor.setRunMode(RunMode.DEBUG);
    }

    @ManagedAttribute
    public String getRunMode() {
        return recordPlaybackInterceptor.getRunMode().name();
    }

    @ManagedOperation
    public void setRunModeRecord() {
        recordPlaybackInterceptor.setRunMode(RunMode.RECORD);
    }

    @ManagedOperation
    public void setRunModePlayback() {
        recordPlaybackInterceptor.setRunMode(RunMode.PLAYBACK);
    }

    @ManagedOperation
    public void setRunModeOff() {
        recordPlaybackInterceptor.setRunMode(RunMode.OFF);
    }
}
