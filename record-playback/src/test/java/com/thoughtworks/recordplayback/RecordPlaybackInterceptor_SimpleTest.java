package com.thoughtworks.recordplayback;

import com.thoughtworks.recordplayback.testservice.SimpleApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/appContext.xml")
public class RecordPlaybackInterceptor_SimpleTest {

    @Autowired
    private SimpleApi simpleApi;

    @Autowired
    private RecordPlaybackInterceptor interceptor;

    private SimpleRecordPlaybackImpls simpleRecordPlaybackImpls = new SimpleRecordPlaybackImpls();

    private RecordPlaybackConfig config = new RecordPlaybackConfig();

    @Before
    public void given() {
        interceptor.setRecordPlaybackConfig(config);
        interceptor.setRecordHandler(simpleRecordPlaybackImpls);
        interceptor.setPlaybackHandler(simpleRecordPlaybackImpls);
    }

    @Test
    public void shouldRecordAndPlayback1API() {
        config.setRunMode(RunMode.RECORD);

        String[] recordResponse = invoke(3);
        assertNotSame(recordResponse[0], recordResponse[1]);
        assertNotSame(recordResponse[1], recordResponse[2]);

        config.setRunMode(RunMode.PLAYBACK);

        String[] playbackResponse = invoke(3);
        assertSame(recordResponse[0], playbackResponse[0]);
        assertSame(recordResponse[1], playbackResponse[1]);
        assertSame(recordResponse[2], playbackResponse[2]);
    }

    private String[] invoke(int timesToInvoke) {
        String[] responses = new String[timesToInvoke];
        for (int i = 0; i < timesToInvoke; i++) {
            responses[i] = simpleApi.getResult(1000, "Foo" + i);
        }
        return responses;
    }




}
