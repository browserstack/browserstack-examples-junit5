package com.browserstack.suites.user;

import com.browserstack.NonPageObjectTest;
import com.browserstack.utils.Constants;
import com.browserstack.utils.UserCredentialUtil;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static com.browserstack.utils.Constants.ElementLocators.PRODUCT_IMAGE_SOURCE_ATTRIBUTE;
import static com.browserstack.utils.Constants.ElementLocators.PRODUCT_IMAGE_TAG;
import static com.browserstack.utils.Constants.ErrorMessages.IMAGE_NOT_LOADING;

public class ImageLoadingTest extends NonPageObjectTest {

    private static final String IMAGE_NOT_LOADING_ACCOUNT_USER_NAME = "image_not_loading_user";
    private static final long LOGIN_TIME_OUT = 5;

    @Override
    public void preProcess(DesiredCapabilities desiredCapabilities) {
        applyMask(desiredCapabilities);
    }

    public void signIntoImageNotLoadingAccount(WebDriver webDriver) {
        String password = UserCredentialUtil.getPassword(IMAGE_NOT_LOADING_ACCOUNT_USER_NAME);
        signIn(webDriver, IMAGE_NOT_LOADING_ACCOUNT_USER_NAME, password);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, LOGIN_TIME_OUT);
        webDriverWait.until(ExpectedConditions.urlContains(Constants.EndPoints.SIGNED_IN));
    }

    @Override
    public void run(WebDriver webDriver) {
        navigateToHome(webDriver);
        signIntoImageNotLoadingAccount(webDriver);
        List<WebElement> images = webDriver.findElements(new By.ByTagName(PRODUCT_IMAGE_TAG));
        images.stream().forEach(image ->
                Assertions.assertTrue(image.getAttribute(PRODUCT_IMAGE_SOURCE_ATTRIBUTE).isEmpty(), () -> markFail(IMAGE_NOT_LOADING)));
    }

    @Override
    public void postProcess(WebDriver webDriver) {
        logOut(webDriver);
    }
}
