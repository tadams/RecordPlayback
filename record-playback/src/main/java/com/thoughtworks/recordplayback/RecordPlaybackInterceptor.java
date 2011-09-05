package com.thoughtworks.recordplayback;

import org.aspectj.lang.ProceedingJoinPoint;

public class RecordPlaybackInterceptor {

    private RecordPlaybackConfig recordPlaybackConfig = RecordPlaybackConfig.EMPTY_CONFIG;

    private RecordHandler   recordHandler;

    private PlaybackHandler playbackHandler;

    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {

        Object response;

        switch (recordPlaybackConfig.getRunMode()) {

            case RECORD:
                response = joinPoint.proceed();
                recordHandler.recordAPI(joinPoint.getArgs(), response);
                return response;

            case PLAYBACK:
                response = playbackHandler.getRecordedResponse(joinPoint.getArgs());
                return response;

        }

        return joinPoint.proceed();
    }

    public void setRecordPlaybackConfig(RecordPlaybackConfig recordPlaybackConfig) {
        this.recordPlaybackConfig = recordPlaybackConfig;
    }

    private void handleConfigChange(RecordPlaybackConfig newConfig) {
        if (recordPlaybackConfig.getRunMode() == RunMode.RECORD && newConfig.getRunMode() != RunMode.RECORD) {
            recordHandler.endRecord();
        }
    }

    public void setPlaybackHandler(PlaybackHandler playbackHandler) {
        this.playbackHandler = playbackHandler;
    }

    public void setRecordHandler(RecordHandler recordHandler) {
        this.recordHandler = recordHandler;
    }

}
