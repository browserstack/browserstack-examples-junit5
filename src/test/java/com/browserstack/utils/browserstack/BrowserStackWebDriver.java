package com.browserstack.utils.browserstack;


import com.browserstack.local.Local;
import com.browserstack.utils.JsonUtil;
import com.browserstack.utils.ManagedWebDriver;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Semaphore;

import static com.browserstack.utils.Constants.Capabilities.*;
import static com.browserstack.utils.Constants.Limits.BROWSERSTACK_PARALLEL_LIMIT;
import static com.browserstack.utils.Constants.Profiles.PROFILE_LOCAL;
import static com.browserstack.utils.Constants.Profiles.PROFILE_LOCAL_PARALLEL;

public class BrowserStackWebDriver extends ManagedWebDriver {

    private static final int LOCAL_IDENTIFIER_LENGTH = 8;

    private WebDriver bsWebDriver;
    private Local bsLocal;
    private static final Semaphore REMAINING_PARALLEL_COUNTER = new Semaphore(BROWSERSTACK_PARALLEL_LIMIT);
    private static final String SCRIPT_FORMAT = "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"%s\", \"reason\": \"%s\"}}";

    public static BrowserStackWebDriver getDriver(String profile, DesiredCapabilities desiredCapabilities) {
        return new BrowserStackWebDriver(profile,desiredCapabilities);
    }

    private BrowserStackWebDriver(String profile, DesiredCapabilities desiredCapabilities) {
        BrowserStackUtil browserStackUtil = new BrowserStackUtil();
        if (Arrays.asList(PROFILE_LOCAL,PROFILE_LOCAL_PARALLEL).contains(profile)) {
            desiredCapabilities.setCapability(CAPABILITY_BS_LOCAL,"true");
            String localIdentifier = (String) desiredCapabilities.getCapability(CAPABILITY_BS_LOCAL_IDENTIFIER);
            if (localIdentifier==null){
                localIdentifier = RandomStringUtils.randomAlphabetic(LOCAL_IDENTIFIER_LENGTH);
                desiredCapabilities.setCapability(CAPABILITY_BS_LOCAL_IDENTIFIER, localIdentifier);
            }
            Map<String, String> localOptions = JsonUtil.getLocalOptions();
            localOptions.put(LOCAL_OPTION_LOCAL_IDENTIFIER, localIdentifier);
            if (!localOptions.containsKey(LOCAL_OPTION_KEY)||localOptions.get(LOCAL_OPTION_KEY)==null){
                localOptions.put(LOCAL_OPTION_KEY, browserStackUtil.getBrowserstackAccessKey());
            }
            bsLocal = new Local();
            try {
                bsLocal.start(localOptions);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            REMAINING_PARALLEL_COUNTER.acquire();
            bsWebDriver = new RemoteWebDriver(browserStackUtil.getHubURL(), desiredCapabilities);
            setWebDriver(bsWebDriver);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        quit();
    }

    @Override
    public void quit() {
        try {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) bsWebDriver;
            javascriptExecutor.executeScript(String.format(SCRIPT_FORMAT,getStatus(),getReason()));
            if (bsLocal!=null){
                bsLocal.stop();
            }
        }
        catch (Exception e) {
                e.printStackTrace();
            }
        finally {
            bsWebDriver.quit();
            REMAINING_PARALLEL_COUNTER.release();
        }
    }

}
