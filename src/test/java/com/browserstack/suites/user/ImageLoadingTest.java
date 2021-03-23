package com.browserstack.suites.user;

import com.browserstack.NonPageObjectTest;
import com.browserstack.utils.ElementLocatorUtil;
import com.browserstack.utils.UserCredentialUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;

import static com.browserstack.utils.Constants.AllureTags.EPIC_USER;
import static com.browserstack.utils.Constants.AllureTags.STORY_IMAGE_LOADING;
import static com.browserstack.utils.Constants.ElementLocators.PRODUCT_IMAGE_SOURCE_ATTRIBUTE;
import static com.browserstack.utils.Constants.ElementLocators.PRODUCT_IMAGE_TAG;
import static com.browserstack.utils.Constants.EndPoints.SIGNED_IN;
import static com.browserstack.utils.Constants.ErrorMessages.IMAGE_NOT_LOADING;
import static com.browserstack.utils.Constants.ErrorMessages.SIGNIN_NOT_COMPLETED_ON_TIME;

@Epic(EPIC_USER)
@Story(STORY_IMAGE_LOADING)
public class ImageLoadingTest extends NonPageObjectTest {

    private static final String IMAGE_NOT_LOADING_ACCOUNT_USER_NAME = "image_not_loading_user";

    @Override
    @Step("Preprocessing capabilities")
    public void preProcess(DesiredCapabilities desiredCapabilities) {
        applyMask(desiredCapabilities);
    }

    @Override
    @Step("Running the test")
    public void run(WebDriver webDriver) {
        navigateToHome(webDriver);
        signIntoImageNotLoadingAccount(webDriver);
        List<WebElement> images = webDriver.findElements(By.tagName(PRODUCT_IMAGE_TAG));
        images.stream().forEach(image ->
                Assertions.assertTrue(image.getAttribute(PRODUCT_IMAGE_SOURCE_ATTRIBUTE).isEmpty(), () -> markFail(IMAGE_NOT_LOADING)));
    }

    @Override
    @Step("Post processing")
    public void postProcess(WebDriver webDriver) {
        logOut(webDriver);
    }

    @Step("Signing in with image_not_loading credentials")
    private void signIntoImageNotLoadingAccount(WebDriver webDriver) {
        String password = UserCredentialUtil.getPassword(IMAGE_NOT_LOADING_ACCOUNT_USER_NAME);
        signIn(webDriver, IMAGE_NOT_LOADING_ACCOUNT_USER_NAME, password);
        ElementLocatorUtil.waitUntilURLContains(webDriver, this, SIGNED_IN, SIGNIN_NOT_COMPLETED_ON_TIME);
    }
}
