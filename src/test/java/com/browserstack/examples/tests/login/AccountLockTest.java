package com.browserstack.examples.tests.login;

import com.browserstack.examples.extensions.WebDriverTest;
import com.browserstack.examples.helpers.CommonSteps;
import com.browserstack.examples.helpers.ElementLocatorUtil;
import com.browserstack.utils.UserCredentialUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.browserstack.examples.helpers.Constants.AllureTags.EPIC_LOGIN;
import static com.browserstack.examples.helpers.Constants.AllureTags.STORY_ACCOUNT_LOCK;
import static com.browserstack.examples.helpers.Constants.ElementLocators.*;
import static com.browserstack.examples.helpers.Constants.ErrorMessages.*;

@Epic(EPIC_LOGIN)
@Story(STORY_ACCOUNT_LOCK)
public class AccountLockTest {

    private static final String ACCOUNT_LOCKED_ERROR_MESSAGE = "Your account has been locked.";
    private static final String LOCKED_ACCOUNT_USER_NAME = "locked_user";

    @WebDriverTest(capabilities = {"apply_command_mask"})
    public void lockMessageTest(WebDriver webDriver) {
        CommonSteps.navigateToHome(webDriver);
        signIntoLockedAccount(webDriver);
        checkAPIErrorDisplayed(webDriver);
    }

    @Step("Attempting to sign in with locked_user credentials")
    private void signIntoLockedAccount(WebDriver webDriver) {
        String password = UserCredentialUtil.getPassword(LOCKED_ACCOUNT_USER_NAME);
        attemptSignIn(webDriver, LOCKED_ACCOUNT_USER_NAME, password);
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.className(API_ERROR_CLASS), API_ERROR_NOT_LOADED_ON_TIME);
    }

    @Step("Attempting to sign in with username {1} and password {2}")
    private void attemptSignIn(WebDriver webDriver, String userName, String password) {
        WebElement signInButton = webDriver.findElement(By.id(SIGN_IN_BUTTON_ID));
        signInButton.click();
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.id(USER_INPUT_ID), SIGNIN_PAGE_NOT_LOADED_ON_TIME);
        WebElement userElement = webDriver.findElement(By.id(USER_INPUT_ID));
        userElement.sendKeys(userName);
        userElement.sendKeys(Keys.ENTER);
        WebElement passwordElement = webDriver.findElement(By.id(PASSWORD_INPUT_ID));
        passwordElement.sendKeys(password);
        passwordElement.sendKeys(Keys.ENTER);
        WebElement logInButton = webDriver.findElement(By.id(LOGIN_BUTTON_ID));
        logInButton.click();
    }

    @Step("Checking if the API error is displayed")
    private void checkAPIErrorDisplayed(WebDriver webDriver) {
        WebElement apiError = webDriver.findElement(By.className(API_ERROR_CLASS));
        Assertions.assertEquals(apiError.getText(), ACCOUNT_LOCKED_ERROR_MESSAGE, LOCKED_ACCOUNT);
    }

}