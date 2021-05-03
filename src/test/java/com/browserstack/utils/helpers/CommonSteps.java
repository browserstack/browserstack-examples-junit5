package com.browserstack.utils.helpers;

import com.browserstack.utils.config.WebDriverFactory;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.*;

import static com.browserstack.utils.helpers.Constants.ElementLocators.*;
import static com.browserstack.utils.helpers.Constants.ErrorMessages.*;

public class CommonSteps {

    @Step("Navigating to the home page")
    public static void navigateToHome(WebDriver webDriver) {
        webDriver.get(WebDriverFactory.getInstance().getTestEndpoint());
        ElementLocatorUtil.waitUntilTitleIs(webDriver, HOME_PAGE_TITLE, HOME_PAGE_NOT_LOADED_ON_TIME);
        waitForSpinner(webDriver);
    }

    @Step("Waiting for the spinner to stop")
    public static void waitForSpinner(WebDriver webDriver) {
        ElementLocatorUtil.waitUntilElementVanish(webDriver, By.xpath(RELOAD_SPINNER_XPATH), SPINNER_NOT_STOPPED_ON_TIME);
    }

    @Step("Finding the product count")
    public static int productCount(WebDriver webDriver) {
        return webDriver.findElements(By.className(PRODUCT_CARD_CSS)).size();
    }

    @Step("Signing in with username {0} and password {1}")
    public static void signIn(String userName, String password, WebDriver webDriver) {
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
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.className(USERNAME_LABEL_CLASS), SIGNIN_NOT_COMPLETED_ON_TIME);
    }

    @Attachment
    @Step("Taking screenshot")
    public static byte[] takeScreenshot(WebDriver webDriver) {
        return ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.BYTES);
    }
    
}
