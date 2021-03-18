package com.browserstack.suites.login;

import com.browserstack.NonPageObjectTest;
import com.browserstack.utils.UserCredentialUtil;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.browserstack.utils.Constants.ElementLocators.API_ERROR_CLASS;
import static com.browserstack.utils.Constants.ErrorMessages.LOCKED_ACCOUNT;


public class LockedAccountTest extends NonPageObjectTest {

    private static final String ERROR_MESSAGE = "Your account has been locked.";
    private static final String LOCKED_ACCOUNT_USER_NAME = "locked_user";
    private static final long LOGIN_TIME_OUT = 5;

    @Override
    public void preProcess(DesiredCapabilities desiredCapabilities) {
        applyMask(desiredCapabilities);
        desiredCapabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS,true);
    }

    public void signIntoLockedAccount(WebDriver webDriver) {
        String password = UserCredentialUtil.getPassword(LOCKED_ACCOUNT_USER_NAME);
        signIn(webDriver, LOCKED_ACCOUNT_USER_NAME, password);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, LOGIN_TIME_OUT);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(new By.ByClassName(API_ERROR_CLASS)));
    }

    @Override
    public void run(WebDriver webDriver) {
        navigateToHome(webDriver);
        signIntoLockedAccount(webDriver);
        WebElement apiError = webDriver.findElement(new By.ByClassName(API_ERROR_CLASS));
        Assertions.assertEquals(apiError.getText(), ERROR_MESSAGE, () -> markFail(LOCKED_ACCOUNT));
    }

    @Override
    public void postProcess(WebDriver webDriver) {
       logOut(webDriver);
    }
}
