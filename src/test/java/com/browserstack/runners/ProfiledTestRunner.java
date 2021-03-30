package com.browserstack.runners;

import com.browserstack.AnnotedTest;
import com.browserstack.ProfiledTest;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import static com.browserstack.utils.Constants.ErrorMessages.PAGE_NOT_LOADED_ON_TIME;
import static com.browserstack.utils.Constants.ErrorMessages.PAGE_NOT_RENDERED_CORRECTLY;

public abstract class ProfiledTestRunner {

    private static final String TEST_EXCEPTION_MESSAGE = "Exception encountered : ";

    public abstract void startDriver();
    public abstract void runTest();
    public abstract void stopDriver();

    public void runTest(ProfiledTest test, WebDriver webDriver) {
        try {
            test.run(webDriver);
        } catch (TimeoutException e) {
            Assertions.fail(test.markFail(PAGE_NOT_LOADED_ON_TIME));
        } catch (NoSuchElementException e) {
            Assertions.fail(test.markFail(PAGE_NOT_RENDERED_CORRECTLY));
        } catch (Exception e) {
            Assertions.fail(test.markFail(TEST_EXCEPTION_MESSAGE + e));
        }
    }
}
