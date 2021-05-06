package com.browserstack.utils.extensions;

import com.browserstack.utils.helpers.CommonSteps;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Arrays;
import java.util.Optional;

import static com.browserstack.utils.helpers.Constants.ExecutionContexts.ON_DOCKER;
import static com.browserstack.utils.helpers.Constants.ExecutionContexts.ON_PREMISE;

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
        WebDriver webDriver = context.getStore(WebDriverParameterResolver.STORE_NAMESPACE).get(testName, WebDriver.class);
        if (status.equals("failed")) {
            CommonSteps.takeScreenshot(webDriver);
        }
        try {
            String profile = System.getProperty("profile.name");
            if (!Arrays.asList(ON_PREMISE, ON_DOCKER).contains(profile)) {
                ((JavascriptExecutor) webDriver).executeScript(String.format(TEST_STATUS_SCRIPT, status, reason));
                String sessionURL = String.format("https://automate.browserstack.com/dashboard/v2/sessions/%s", ((RemoteWebDriver) webDriver).getSessionId());
                Allure.link("Browserstack Automate", sessionURL);
            }
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }
}
