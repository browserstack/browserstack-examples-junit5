package com.browserstack;

import com.browserstack.utils.Constants;
import com.browserstack.utils.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.browserstack.utils.Constants.ElementLocators.*;
import static com.browserstack.utils.Constants.Instances.APPLICATION_INSTANCE_PRIVATE;
import static com.browserstack.utils.Constants.Instances.APPLICATION_INSTANCE_PUBLIC;
import static com.browserstack.utils.Constants.Profiles.PROFILE_LOCAL;
import static com.browserstack.utils.Constants.Profiles.PROFILE_LOCAL_PARALLEL;

public abstract class NonPageObjectTest extends AbstractTest {

    private static final long HOME_PAGE_LOAD_TIME_OUT = 5;
    private static final long SIGN_IN_TIME_OUT = 5;
    private String url;

    @BeforeEach
    public void init(TestInfo testInfo) {
        switch (testInfo.getTags().iterator().next()){
            case PROFILE_LOCAL:
            case PROFILE_LOCAL_PARALLEL:
                url = JsonUtil.getInstanceURL(APPLICATION_INSTANCE_PRIVATE);
                break;
            default:url = JsonUtil.getInstanceURL(APPLICATION_INSTANCE_PUBLIC);
        }
    }

    public void navigateToHome(WebDriver webDriver) {
        webDriver.get(url);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, HOME_PAGE_LOAD_TIME_OUT);
        webDriverWait.until(ExpectedConditions.titleIs(HOME_PAGE_TITLE));
        webDriverWait.until(waitWebDriver -> waitWebDriver.findElements(By.xpath(RELOAD_SPINNER_XPATH)).isEmpty());
    }

    public int productCount(WebDriver webDriver) {
        return webDriver.findElements(By.className(PRODUCT_CARD_CSS)).size();
    }

    public void signIn(WebDriver webDriver, String userName, String password) {
        WebElement signInButton = webDriver.findElement(By.id(SIGN_IN_BUTTON_ID));
        signInButton.click();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, SIGN_IN_TIME_OUT);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(new By.ByXPath(USER_INPUT_XPATH)));
        WebElement user = webDriver.findElement(new By.ByXPath(USER_INPUT_XPATH));
        user.sendKeys(userName);
        user.sendKeys(Keys.ENTER);
        WebElement passwordField = webDriver.findElement(new By.ByXPath(PASSWORD_INPUT_XPATH));
        passwordField.sendKeys(password);
        passwordField.sendKeys(Keys.ENTER);
        WebElement logInButton = webDriver.findElement(By.id(LOGIN_BUTTON_ID));
        logInButton.click();
    }

    public void applyMask(DesiredCapabilities desiredCapabilities){
        desiredCapabilities.setCapability("browserstack.maskCommands", "setValues, getValues, setCookies, getCookies");
    }

    public void logOut(WebDriver webDriver) {
        navigateToHome(webDriver);
        if(webDriver.findElements(new By.ById(SIGN_OUT_BUTTON_ID)).size()!=0){
            webDriver.findElement(new By.ById(SIGN_OUT_BUTTON_ID)).click();
        }
    }

}
