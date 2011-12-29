package com.thoughtworks.recordplayback.jmx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/appContext.xml")
public class JmxTest {

    private final static List<String> RUN_MODE_TOGGLE_METHODS = new ArrayList<String>();

    static {
        RUN_MODE_TOGGLE_METHODS.add("setRunModeDebug");
        RUN_MODE_TOGGLE_METHODS.add("setRunModeRecord");
        RUN_MODE_TOGGLE_METHODS.add("setRunModePlayback");
        RUN_MODE_TOGGLE_METHODS.add("setRunModeOff");
        RUN_MODE_TOGGLE_METHODS.add("getRunMode");
    }

    // Run Command
    // mvn exec:java -Dexec.mainClass="com.thoughtworks.recordplayback.jmx.JmxTest" -Dexec.classpathScope=test
    public static void main(String[] args) {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ApplicationContext context = new ClassPathXmlApplicationContext("appContext.xml");


        try {
            ObjectName objectName = new ObjectName("com.thoughtworks.recordplayback:name=RecordPlayBackRunMode");

            MBeanInfo runModeInfo = mbs.getMBeanInfo(objectName);

            for (MBeanOperationInfo operationInfo : runModeInfo.getOperations()) {
                System.out.println(operationInfo.getName());
            }

            Thread.sleep(60000 * 3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldRunJmxServerWithRunModeToggle() throws Exception {
        ObjectName objectName = new ObjectName("com.thoughtworks.recordplayback:name=RunModeToggle");

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        MBeanInfo runModeInfo = mbs.getMBeanInfo(objectName);

        for (MBeanOperationInfo operationInfo : runModeInfo.getOperations()) {
            assertTrue(RUN_MODE_TOGGLE_METHODS.contains(operationInfo.getName()));
        }

    }

}
