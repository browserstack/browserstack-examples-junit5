package com.browserstack.utils.browserstack;


import com.browserstack.local.Local;
import com.browserstack.utils.ManagedWebDriver;
import com.browserstack.utils.browserstack.BrowserStackUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class DropSafeBrowserStackWebDriver extends ManagedWebDriver {

    private static final String BROWSERSTACK_LOCAL_CAPABILITY = "browserstack.local";
    private static final int LOCAL_IDENTIFIER_LENGTH = 8;
    private static final String LOCAL_OPTIONS = "local_options";
    private static final String LOCAL_SPLITTER = ",";
    private static final char LOCAL_SEPARATOR = ':';
    private static final Semaphore REMAINING_PARALLEL_COUNTER =new Semaphore(new BrowserStackUtil().getSessionsMaxAllowed());

    private WebDriver bsWebDriver;
    private BrowserStackUtil browserStackUtil;
    private Local bsLocal;

    public DropSafeBrowserStackWebDriver(DesiredCapabilities desiredCapabilities) {
        browserStackUtil = new BrowserStackUtil();
        if (desiredCapabilities.getCapability(BROWSERSTACK_LOCAL_CAPABILITY) != null && desiredCapabilities.getCapability(BROWSERSTACK_LOCAL_CAPABILITY).equals("true")) {
            String localIdentifier = (String) desiredCapabilities.getCapability("browserstack.localIdentifier");
            localIdentifier = localIdentifier == null ? RandomStringUtils.randomAlphabetic(LOCAL_IDENTIFIER_LENGTH) : localIdentifier;
            desiredCapabilities.setCapability("browserstack.localIdentifier", localIdentifier);
            String localOptionsCapability = (String) desiredCapabilities.getCapability(LOCAL_OPTIONS);
            String[] localOptionPairs = localOptionsCapability.split(LOCAL_SPLITTER);
            Map<String, String> localOptions = new HashMap<>();

            for (String localOptionPair : localOptionPairs) {
                int separator = localOptionPair.indexOf(LOCAL_SEPARATOR);
                localOptions
                        .put(localOptionPair.substring(0, separator - 1)
                                , localOptionPair.substring(separator + 1, localOptionPair.length() - 1));
            }
            localOptions.put("key", browserStackUtil.getBrowserstackAccessKey());
            localOptions.put("localIdentifier", localIdentifier);
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
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) bsWebDriver;
        javascriptExecutor.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"" + getStatus() + "\", \"reason\": \"" + getReason() + "\"}}");
        bsWebDriver.quit();
        REMAINING_PARALLEL_COUNTER.release();
        if (bsLocal != null) {
            try {
                bsLocal.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
