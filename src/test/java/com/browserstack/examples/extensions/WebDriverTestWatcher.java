package com.browserstack.examples.extensions;

import java.util.Optional;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This {@link TestWatcher} is responsible for the following,
 * <ul>
 *     <li>If the {@link WebDriver} supports reporting the test status, i.e. the {@link WebDriver} is a {@link RemoteWebDriver} then the status is marked on the cloud provider.</li>
 *     <li>After the test is completed the {@link WebDriver} is also quit such that all the instances of the Browser for that WebDriver are closed.</li>
 * </ul>
 *
 * The assumption in this class is that the {@link WebDriver} instances are one per class so that the user can run
 * parallel tests at a Test Method level.
 *
 * @author Anirudha Khanna
 */
public class WebDriverTestWatcher implements TestWatcher {

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
        TestWatcher.super.testDisabled(context, reason);
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
        markAndCloseWebDriver(context, "passed", "Test passed");
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
        markAndCloseWebDriver(context, "failed", cause.getMessage());
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
        markAndCloseWebDriver(context, "failed", cause.getMessage());
    }

    private void markAndCloseWebDriver(ExtensionContext context, String status, String reason) {
        String testName = context.getDisplayName();
        WebDriver webDriver = context.getStore(WebDriverParameterResolver.STORE_NAMESPACE).get(testName, WebDriver.class);
        try {
            if (webDriver instanceof RemoteWebDriver) {
                ((JavascriptExecutor) webDriver).executeScript(String.format(TEST_STATUS_SCRIPT, status, reason));
            }
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }

}
