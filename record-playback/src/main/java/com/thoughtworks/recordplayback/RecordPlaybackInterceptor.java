package com.thoughtworks.recordplayback;

import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.HashMap;
import java.util.Map;

public class RecordPlaybackInterceptor {

    private RunMode mode = RunMode.OFF;

    private Cache   cache = new Cache();

    private Map<String, RequestNormalizer> normalizerMap = new HashMap<String, RequestNormalizer>();
    private Map<String, ResponseModifier>  modifierMap   = new HashMap<String, ResponseModifier>();

    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {

        Object response = null;

        switch (mode) {

            case DEBUG:
                response = doDebug(joinPoint);
                return response;

            case RECORD:
                response = doRecord(joinPoint);
                return response;

            case PLAYBACK:
                response = doPlayback(joinPoint);
                if (response != null) {
                    return response;
                }
        }

        return joinPoint.proceed();
    }

    private Object doRecord(ProceedingJoinPoint joinPoint) throws Throwable {


        StopWatch stopWatch = new StopWatch();

        try {
            stopWatch.start();
            Object response = joinPoint.proceed();
            stopWatch.stop();

            recordResponse(stopWatch, joinPoint, response);
            return response;

        } catch (Throwable thrown) {
            stopWatch.stop();

            recordException(stopWatch, joinPoint, thrown);
            throw thrown;
        }
    }

    private void recordException(StopWatch          stopWatch,
                                 ProceedingJoinPoint joinPoint,
                                 Throwable           thrown) {

        String joinPointId = createMethodId(joinPoint);
        RequestWrapper request = normalizeRequest(joinPointId, joinPoint.getArgs());
        RecordedResponse recordedResponse = new RecordedResponse(thrown, stopWatch.getTime());

        cache.save(request, recordedResponse);
    }

    private void recordResponse(StopWatch           stopWatch,
                                ProceedingJoinPoint joinPoint,
                                Object              response) {

        String joinPointId = createMethodId(joinPoint);
        RequestWrapper request = normalizeRequest(joinPointId, joinPoint.getArgs());
        RecordedResponse recordedResponse = new RecordedResponse(response, stopWatch.getTime());

        cache.save(request, recordedResponse);
    }

    //TODO: Handle condition when there is no RecordedResponse for Request
    private Object doPlayback(ProceedingJoinPoint joinPoint) throws Throwable {
        String joinPointId = createMethodId(joinPoint);
        RequestWrapper request = normalizeRequest(joinPointId, joinPoint.getArgs());

        RecordedResponse recordedResponse = cache.get(request);

        if (recordedResponse == null && mode.isDebug()) {
            return null;
        }

        if (recordedResponse.isException()) {
            throw recordedResponse.getException();
        }

        //TODO: add toggle for simulate latency option
        simulateLatency(recordedResponse);

        return modifyResponse(joinPointId, recordedResponse, joinPoint.getArgs());
    }

    private Object doDebug(ProceedingJoinPoint joinPoint) throws Throwable {
        Object response = doPlayback(joinPoint);
        if (response != null) {
            return response;
        }

        return doRecord(joinPoint);
    }

    public void setRunMode(RunMode newMode) {
        handleConfigChange(newMode);
        this.mode = newMode;
    }

    public RunMode getRunMode() {
        return mode;
    }

    private void handleConfigChange(RunMode newMode) {
        if (mode == RunMode.RECORD && newMode != RunMode.RECORD) {
            cache.saveAsFile();
            cache.clear();
        }
    }

    private String createMethodId(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName() + ":" +
               joinPoint.getSignature().getName();
    }

    private RequestWrapper normalizeRequest(String joinPointId, Object[] arguments) {
        RequestNormalizer requestNormalizer = normalizerMap.get(joinPointId);
        if (requestNormalizer != null) {
            arguments = requestNormalizer.normalize(joinPointId, arguments);
        }
        return new RequestWrapper(arguments);
    }

    private Object modifyResponse(String joinPointId, RecordedResponse recordedResponse, Object[] originalArguments) {

        if (recordedResponse == null) {
            return null;
        }

        ResponseModifier responseModifier = modifierMap.get(joinPointId);
        if (responseModifier != null) {
            return responseModifier.modify(joinPointId, recordedResponse, originalArguments);
        }
        return recordedResponse.getResponse();
    }


    private void simulateLatency(RecordedResponse recordedResponse) {

        if (recordedResponse.hasLatency()) {
            try {
                Thread.sleep(recordedResponse.getLatencyMilliseconds());
            } catch (InterruptedException ignoreAndResumeExecution) {
            }
        }
    }

    public void setRequestNormalizer(Map<String, RequestNormalizer> normalizerMap) {
        this.normalizerMap = normalizerMap;
    }

    public void setResponseModifier(Map<String, ResponseModifier> modifierMap) {
        this.modifierMap = modifierMap;
    }

}
