package com.thoughtworks.recordplayback;

import com.thoughtworks.recordplayback.config.RunModeToggle;
import com.thoughtworks.recordplayback.testservice.SimpleApi;
import org.apache.commons.lang.time.StopWatch;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/appContext.xml")
public class RecordPlaybackInterceptor_LatencyTest {

    @Autowired
    private SimpleApi simpleApi;

    @Autowired
    private RecordPlaybackInterceptor interceptor;

    @Autowired
    private RunModeToggle toggle;

    private Cache cache = new Cache();

    private SimpleRecordPlaybackImpls simpleRecordPlaybackImpls = new SimpleRecordPlaybackImpls(cache);

    @Before
    public void given() {
        interceptor.setRecordHandler(simpleRecordPlaybackImpls);
        interceptor.setPlaybackHandler(simpleRecordPlaybackImpls);
    }

    @Test
    public void shouldRecordAndPlayback1API() {
        toggle.setRunModeRecord();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String response1 = simpleApi.getResultWithLatency(100, "Foo", 5);
        stopWatch.stop();
        assertTrue(stopWatch.getTime() > 4);

        toggle.setRunModePlayback();
        stopWatch.reset();
        stopWatch.start();
        String response2 = simpleApi.getResultWithLatency(100, "Foo", 5);
        stopWatch.stop();
        assertTrue(stopWatch.getTime() > 4);
        assertEquals(response1, response2);
    }
}
