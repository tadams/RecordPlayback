package com.thoughtworks.recordplayback;

import com.thoughtworks.recordplayback.testservice.SimpleApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/appContext.xml")
public class RecordPlaybackInterceptor_DemoModeTest {

    @Autowired
    private SimpleApi simpleApi;

    @Autowired
    private RecordPlaybackInterceptor interceptor;

    private Cache cache = new Cache();

    private SimpleRecordPlaybackImpls simpleRecordPlaybackImpls = new SimpleRecordPlaybackImpls(cache);

    @Before
    public void given() {
        interceptor.setRecordHandler(simpleRecordPlaybackImpls);
        interceptor.setPlaybackHandler(simpleRecordPlaybackImpls);
    }

    @Test
    public void shouldRecordAndPlayback1API() {
        interceptor.setRunMode(RunMode.DEBUG);

        String[] recordResponse = invoke(3);
        assertFalse(recordResponse[0].equals(recordResponse[1]));
        assertFalse(recordResponse[1].equals(recordResponse[2]));

        String[] playbackResponse = invoke(3);
        assertEquals(recordResponse[0], playbackResponse[0]);
        assertEquals(recordResponse[1], playbackResponse[1]);
        assertEquals(recordResponse[2], playbackResponse[2]);
    }

    private String[] invoke(int timesToInvoke) {
        String[] responses = new String[timesToInvoke];
        for (int i = 0; i < timesToInvoke; i++) {
            responses[i] = simpleApi.getResult(1000, "Foo" + i);
        }
        return responses;
    }




}
