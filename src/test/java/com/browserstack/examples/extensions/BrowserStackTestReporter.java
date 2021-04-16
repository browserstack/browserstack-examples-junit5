package com.browserstack.examples.extensions;

import java.util.Optional;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public class BrowserStackTestReporter implements TestWatcher {

    private static final String TEST_STATUS_SCRIPT = "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"%s\", \"reason\": \"%s\"}}";

    /**
     * Invoked after a disabled test has been skipped.
     *
     * <p>The default implementation does nothing. Concrete implementations can
     * override this method as appropriate.
     *
     * @param context the current extension context; never {@code null}
     * @param reason  the reason the test is disabled; never {@code null} but
     */
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {

    }

    /**
     * Invoked after a test has completed successfully.
     *
     * <p>The default implementation does nothing. Concrete implementations can
     * override this method as appropriate.
     *
     * @param context the current extension context; never {@code null}
     */
    @Override
    public void testSuccessful(ExtensionContext context) {
        String testName = context.getDisplayName();
        WebDriver webDriver = (WebDriver) context.getStore(WebDriverParameterResolver.STORE_NAMESPACE).get(testName);
        if (webDriver instanceof RemoteWebDriver) {
            try {
                ((JavascriptExecutor) webDriver).executeScript(String.format(TEST_STATUS_SCRIPT, "passed", "Test Passed"));
            } finally {
                webDriver.quit();
            }
        }
    }

    /**
     * Invoked after a test has been aborted.
     *
     * <p>The default implementation does nothing. Concrete implementations can
     * override this method as appropriate.
     *
     * @param context the current extension context; never {@code null}
     * @param cause   the throwable responsible for the test being aborted; may be {@code null}
     */
    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        String testName = context.getDisplayName();
        WebDriver webDriver = (WebDriver) context.getStore(WebDriverParameterResolver.STORE_NAMESPACE).get(testName);
        if (webDriver instanceof RemoteWebDriver) {
            try {
                ((JavascriptExecutor) webDriver).executeScript(String.format(TEST_STATUS_SCRIPT, "failed", cause.getMessage()));
            } finally {
                webDriver.quit();
            }
        }
    }

    /**
     * Invoked after a test has failed.
     *
     * <p>The default implementation does nothing. Concrete implementations can
     * override this method as appropriate.
     *
     * @param context the current extension context; never {@code null}
     * @param cause   the throwable that caused test failure; may be {@code null}
     */
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String testName = context.getDisplayName();
        WebDriver webDriver = (WebDriver) context.getStore(WebDriverParameterResolver.STORE_NAMESPACE).get(testName);
        if (webDriver instanceof RemoteWebDriver) {
            try {
                ((JavascriptExecutor) webDriver).executeScript(String.format(TEST_STATUS_SCRIPT, "failed", cause.getMessage()));
            } finally {
                webDriver.quit();
            }
        }
    }
}
