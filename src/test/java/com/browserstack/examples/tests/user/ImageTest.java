package com.browserstack.examples.tests.user;

import com.browserstack.examples.extensions.WebDriverTest;
import com.browserstack.examples.helpers.CommonSteps;
import com.browserstack.examples.helpers.ElementLocatorUtil;
import com.browserstack.utils.UserCredentialUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.browserstack.examples.helpers.CommonSteps.signIn;
import static com.browserstack.examples.helpers.Constants.AllureTags.EPIC_USER;
import static com.browserstack.examples.helpers.Constants.AllureTags.STORY_IMAGE;
import static com.browserstack.examples.helpers.Constants.ElementLocators.PRODUCT_IMAGE_SOURCE_ATTRIBUTE;
import static com.browserstack.examples.helpers.Constants.ElementLocators.PRODUCT_IMAGE_TAG;
import static com.browserstack.examples.helpers.Constants.EndPoints.SIGNED_IN;
import static com.browserstack.examples.helpers.Constants.ErrorMessages.IMAGE_NOT_LOADING;
import static com.browserstack.examples.helpers.Constants.ErrorMessages.SIGNIN_NOT_COMPLETED_ON_TIME;

@Epic(EPIC_USER)
@Story(STORY_IMAGE)
public class ImageTest {

    private static final String IMAGE_NOT_LOADING_ACCOUNT_USER_NAME = "image_not_loading_user";

    @WebDriverTest
    public void imageLoadingTest(WebDriver webDriver) {
        CommonSteps.navigateToHome(webDriver);
        signIntoImageNotLoadingAccount(webDriver);
        List<WebElement> images = webDriver.findElements(By.tagName(PRODUCT_IMAGE_TAG));
        images.stream().forEach(image ->
                Assertions.assertTrue(image.getAttribute(PRODUCT_IMAGE_SOURCE_ATTRIBUTE).isEmpty(), IMAGE_NOT_LOADING));
    }

    @Step("Signing in with image_not_loading credentials")
    private void signIntoImageNotLoadingAccount(WebDriver webDriver) {
        String password = UserCredentialUtil.getPassword(IMAGE_NOT_LOADING_ACCOUNT_USER_NAME);
        signIn(IMAGE_NOT_LOADING_ACCOUNT_USER_NAME, password,webDriver);
        ElementLocatorUtil.waitUntilURLContains(webDriver, SIGNED_IN, SIGNIN_NOT_COMPLETED_ON_TIME);
    }
}