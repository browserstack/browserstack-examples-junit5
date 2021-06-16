package com.browserstack.examples.helpers;

import com.browsersatck.examples.utils.WebDriverWaitUtil;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ElementLocatorUtil {

    public static void waitUntilTitleIs(WebDriver webDriver, String title, String timeOutMessage) {
        try {
            WebDriverWaitUtil.getWebDriverWait(webDriver).until(ExpectedConditions.titleIs(title));
        } catch (TimeoutException e) {
            Assertions.fail(timeOutMessage);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    public static void waitUntilElementVanish(WebDriver webDriver, By by, String timeOutMessage) {
        try {
            WebDriverWaitUtil.getWebDriverWait(webDriver).until(waitWebDriver -> waitWebDriver.findElements(by).isEmpty());
        } catch (TimeoutException e) {
            Assertions.fail(timeOutMessage);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    public static void waitUntilURLContains(WebDriver webDriver, String url, String timeOutMessage) {
        try {
            WebDriverWaitUtil.getWebDriverWait(webDriver).until(ExpectedConditions.urlContains(url));
        } catch (TimeoutException e) {
            Assertions.fail(timeOutMessage);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    public static void waitUntilElementAppears(WebDriver webDriver, By by, String timeOutMessage) {
        try {
            WebDriverWaitUtil.getWebDriverWait(webDriver).until(waitWebDriver -> !waitWebDriver.findElements(by).isEmpty());
        } catch (TimeoutException e) {
            Assertions.fail(timeOutMessage);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}