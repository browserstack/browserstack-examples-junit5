package com.browserstack.suites.login;

import com.browserstack.utils.config.UserCredentialsParser;
import com.browserstack.utils.extensions.WebDriverTest;
import com.browserstack.utils.helpers.CommonSteps;
import com.browserstack.utils.helpers.ElementLocatorUtil;
import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.browserstack.utils.helpers.Constants.AllureTags.EPIC_LOGIN;
import static com.browserstack.utils.helpers.Constants.AllureTags.FEATURE_LOGIN;
import static com.browserstack.utils.helpers.Constants.ElementLocators.*;
import static com.browserstack.utils.helpers.Constants.ErrorMessages.*;

@Epic(EPIC_LOGIN)
@Feature(FEATURE_LOGIN)
public class LoginTest {

    private static final String ACCOUNT_LOCKED_ERROR_MESSAGE = "Your account has been locked.";

    @Step("Attempting to sign in with {0}'s credentials")
    private void signIntoLockedAccount(String user, String password, WebDriver webDriver) {
        attemptSignIn(user, password,webDriver);
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.className(API_ERROR_CLASS), API_ERROR_NOT_LOADED_ON_TIME);
    }

    @Step("Attempting to sign in with username {0} and password {1}")
    private void attemptSignIn(String user, String password, WebDriver webDriver) {
        WebElement signInButton = webDriver.findElement(By.id(SIGN_IN_BUTTON_ID));
        signInButton.click();
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.id(USER_INPUT_ID), SIGNIN_PAGE_NOT_LOADED_ON_TIME);
        WebElement userElement = webDriver.findElement(By.id(USER_INPUT_ID));
        userElement.sendKeys(user);
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

    @WebDriverTest(specificCapabilities = {"apply_mask"})
    @CsvSource({"locked_user"})
    @Description("Testing account lock feature")
    public void accountAccessibilityTest(String user, WebDriver webDriver){
        CommonSteps.navigateToHome(webDriver);
        String password = UserCredentialsParser.getPassword(user);
        signIntoLockedAccount(user,password,webDriver);
        checkAPIErrorDisplayed(webDriver);
    }
}
