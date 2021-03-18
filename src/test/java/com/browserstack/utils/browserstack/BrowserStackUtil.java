package com.browserstack.utils.browserstack;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserStackUtil {

    private static final String BROWSERSTACK_USER_ENV = "BROWSERSTACK_USER";
    private static final String BROWSERSTACK_ACCESS_KEY_ENV = "BROWSERSTACK_ACCESSKEY";
    private static final String BROWSERSTACK_HUB_URL_FORMAT = "https://%s:%s@hub.browserstack.com/wd/hub";

    private final String browserstackUserName;
    private final String browserstackAccessKey;

    public BrowserStackUtil() {
        this.browserstackUserName = System.getenv(BROWSERSTACK_USER_ENV);
        this.browserstackAccessKey = System.getenv(BROWSERSTACK_ACCESS_KEY_ENV);
    }

    public String getBrowserstackUserName() {
        return browserstackUserName;
    }

    public String getBrowserstackAccessKey() {
        return browserstackAccessKey;
    }

    public URL getHubURL() {
        String url = String.format(BROWSERSTACK_HUB_URL_FORMAT, browserstackUserName, browserstackAccessKey);
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}