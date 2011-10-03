package com.thoughtworks.recordplayback;

import com.thoughtworks.recordplayback.testservice.SimpleApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/appContext.xml")
public class RecordPlaybackInterceptor_ExceptionTest {

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
    public void shouldRecordAndPlaybackRecordedException() {
        interceptor.setRunMode(RunMode.RECORD);

        RuntimeException rte1 = null;
        RuntimeException rte2 = null;

        try {
            simpleApi.getResult(0, "Exception");
            fail();
        } catch(RuntimeException rte) {
            rte1 = rte;
        }

        interceptor.setRunMode(RunMode.PLAYBACK);

        try {
            simpleApi.getResult(0, "Exception");
            fail();
        } catch(RuntimeException rte) {
            rte2 = rte;
        }

        assertEquals(rte1.getClass().getName() + rte1.getMessage(),
                     rte2.getClass().getName() + rte2.getMessage());

    }

}
