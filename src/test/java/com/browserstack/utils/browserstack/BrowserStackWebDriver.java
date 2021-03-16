package com.browserstack.utils.browserstack;


import com.browserstack.local.Local;
import com.browserstack.utils.ManagedWebDriver;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.Map;

public class BrowserStackWebDriver extends ManagedWebDriver {

    private static final String BROWSERSTACK_LOCAL_CAPABILITY = "browserstack.local";
    private static final int LOCAL_IDENTIFIER_LENGTH = 8;
    private static final String LOCAL_OPTIONS = "local_options";
    private static final String LOCAL_SPLITTER = ",";
    private static final char LOCAL_SEPARATOR = ':';

    private WebDriver bsWebDriver;
    private Local bsLocal;

    public static BrowserStackWebDriver getDriver(DesiredCapabilities desiredCapabilities) {
        return new BrowserStackWebDriver(desiredCapabilities);
    }

    private BrowserStackWebDriver(DesiredCapabilities desiredCapabilities) {
        BrowserStackUtil browserStackUtil = new BrowserStackUtil();
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

        bsWebDriver = new RemoteWebDriver(browserStackUtil.getHubURL(), desiredCapabilities);
        setWebDriver(bsWebDriver);
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
        if (bsLocal != null) {
            try {
                bsLocal.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
