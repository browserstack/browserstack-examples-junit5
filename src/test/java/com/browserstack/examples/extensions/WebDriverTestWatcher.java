package com.browserstack.examples.extensions;

import com.browserstack.examples.config.DriverType;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Optional;

public class WebDriverTestWatcher implements TestWatcher {

    private static final String TEST_STATUS_SCRIPT = "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"%s\", \"reason\": \"%s\"}}";

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        TestWatcher.super.testDisabled(context, reason);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        markAndCloseWebDriver(context, "passed", "Test passed");
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        markAndCloseWebDriver(context, "failed", cause.getMessage());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        markAndCloseWebDriver(context, "failed", cause.getMessage());
    }

    private void markAndCloseWebDriver(ExtensionContext context, String status, String reason) {
        String testName = context.getDisplayName();
        WebDriver webDriver = context.getStore(WebDriverParameterResolver.STORE_NAMESPACE).get(testName+".webDriver", WebDriver.class);
        boolean toMark = context.getStore(WebDriverParameterResolver.STORE_NAMESPACE).get(testName+".toMark", Boolean.class);
        try {
            if (toMark) {
                ((JavascriptExecutor) webDriver).executeScript(String.format(TEST_STATUS_SCRIPT, status, reason));
            }
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }

}
