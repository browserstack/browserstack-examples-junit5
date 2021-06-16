package com.browsersatck.examples.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverWaitUtil {

    private static final long DEFAULT_WAIT_TIME = 30;

    public static WebDriverWait getWebDriverWait(WebDriver webDriver) {
        return getWebDriverWait(webDriver, DEFAULT_WAIT_TIME);
    }

    public static WebDriverWait getWebDriverWait(WebDriver webDriver, long timeOut) {
        return new WebDriverWait(webDriver, timeOut);
    }
}
