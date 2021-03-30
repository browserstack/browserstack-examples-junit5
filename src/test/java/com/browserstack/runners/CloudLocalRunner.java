package com.browserstack.runners;

import com.browserstack.ProfiledTest;
import com.browserstack.local.Local;
import com.browserstack.utils.JsonUtil;
import com.browserstack.utils.BrowserStackUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.browserstack.utils.Constants.Capabilities.*;

public class CloudLocalRunner extends ProfiledTestRunner {

    private static final int LOCAL_IDENTIFIER_LENGTH = 8;
    private static final String LOCAL_IDENTIFIER = RandomStringUtils.randomAlphabetic(LOCAL_IDENTIFIER_LENGTH);
    private static final Local LOCAL = new Local();
    private static final List<Thread> LOCAL_THREADS = new ArrayList<>();

    private final CloudRunner cloudRunner;

    public CloudLocalRunner(ProfiledTest test, DesiredCapabilities desiredCapabilities) {
        setLocalCapabilities(desiredCapabilities);
        cloudRunner = new CloudRunner(test,desiredCapabilities);
    }

    @Override
    public void startDriver() {
        startLocal();
        cloudRunner.startDriver();
    }

    @Override
    public void runTest() {
        cloudRunner.runTest();
    }

    @Override
    public void stopDriver() {
        cloudRunner.stopDriver();
        stopLocal();
    }

    private void setLocalCapabilities(DesiredCapabilities desiredCapabilities){
        desiredCapabilities.setCapability(CAPABILITY_BS_LOCAL,"true");
        desiredCapabilities.setCapability(CAPABILITY_BS_LOCAL_IDENTIFIER,LOCAL_IDENTIFIER);
    }

    private static synchronized void startLocal(){
        try {
            if(LOCAL_THREADS.isEmpty()){
                Map<String, String> localOptions = JsonUtil.getLocalOptions();
                localOptions.put(LOCAL_OPTION_LOCAL_IDENTIFIER, LOCAL_IDENTIFIER);
                if (!localOptions.containsKey(LOCAL_OPTION_KEY) || localOptions.get(LOCAL_OPTION_KEY) == null) {
                    localOptions.put(LOCAL_OPTION_KEY, BrowserStackUtil.getAccessKey());
                }
                System.out.println("Starting local");
                LOCAL.start(localOptions);
            }
            LOCAL_THREADS.add(Thread.currentThread());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static synchronized void stopLocal(){
        try {
            LOCAL_THREADS.remove(Thread.currentThread());
            if(LOCAL_THREADS.isEmpty()){
                System.out.println("Stopping local");
                LOCAL.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
