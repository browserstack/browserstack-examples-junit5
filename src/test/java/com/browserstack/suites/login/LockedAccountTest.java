package com.browserstack.suites.login;

import com.browserstack.NonPageObjectTest;
import com.browserstack.utils.ElementLocatorUtil;
import com.browserstack.utils.UserCredentialUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.browserstack.utils.Constants.AllureTags.EPIC_LOGIN;
import static com.browserstack.utils.Constants.AllureTags.STORY_LOCKED_ACCOUNT;
import static com.browserstack.utils.Constants.ElementLocators.*;
import static com.browserstack.utils.Constants.ErrorMessages.*;

@Epic(EPIC_LOGIN)
@Story(STORY_LOCKED_ACCOUNT)
public class LockedAccountTest extends NonPageObjectTest {

    private static final String ERROR_MESSAGE = "Your account has been locked.";
    private static final String LOCKED_ACCOUNT_USER_NAME = "locked_user";

    @Override
    @Step("Preprocessing capabilities")
    public void preProcess(DesiredCapabilities desiredCapabilities) {
        applyMask(desiredCapabilities);
    }

    @Override
    @Step("Running the test")
    public void run(WebDriver webDriver) {
        navigateToHome(webDriver);
        signIntoLockedAccount(webDriver);
        checkAPIErrorDisplayed(webDriver);
    }

    @Step("Attempting to sign in with locked_user credentials")
    private void signIntoLockedAccount(WebDriver webDriver) {
        String password = UserCredentialUtil.getPassword(LOCKED_ACCOUNT_USER_NAME);
        attemptSignIn(webDriver, LOCKED_ACCOUNT_USER_NAME, password);
        ElementLocatorUtil.waitUntilElementAppears(webDriver, this, By.className(API_ERROR_CLASS), API_ERROR_NOT_LOADED_ON_TIME);
    }

    @Step("Attempting to sign in with username {1} and password {2}")
    private void attemptSignIn(WebDriver webDriver, String userName, String password) {
        WebElement signInButton = webDriver.findElement(By.id(SIGN_IN_BUTTON_ID));
        signInButton.click();
        ElementLocatorUtil.waitUntilElementAppears(webDriver, this, By.xpath(USER_INPUT_XPATH), SIGNIN_PAGE_NOT_LOADED_ON_TIME);
        WebElement userElement = webDriver.findElement(By.xpath(USER_INPUT_XPATH));
        userElement.sendKeys(userName);
        userElement.sendKeys(Keys.ENTER);
        WebElement passwordElement = webDriver.findElement(By.xpath(PASSWORD_INPUT_XPATH));
        passwordElement.sendKeys(password);
        passwordElement.sendKeys(Keys.ENTER);
        WebElement logInButton = webDriver.findElement(By.id(LOGIN_BUTTON_ID));
        logInButton.click();
    }

    @Step("Checking if the API error is displayed")
    private void checkAPIErrorDisplayed(WebDriver webDriver) {
        WebElement apiError = webDriver.findElement(By.className(API_ERROR_CLASS));
        Assertions.assertEquals(apiError.getText(), ERROR_MESSAGE, () -> markFail(LOCKED_ACCOUNT));
    }

}
