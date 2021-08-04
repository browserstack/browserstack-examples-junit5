package com.browserstack.examples.tests.user;

import com.browsersatck.examples.utils.UserCredentialUtil;
import com.browserstack.examples.extensions.WebDriverTest;
import com.browserstack.examples.helpers.CommonSteps;
import com.browserstack.examples.helpers.Constants;
import com.browserstack.examples.helpers.ElementLocatorUtil;
import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@Feature(Constants.AllureTags.FEATURE_USER)
@Story(Constants.AllureTags.STORY_IMAGE)
public class ImageTest {

    private static final String IMAGE_NOT_LOADING_ACCOUNT_USER_NAME = "image_not_loading_user";

    @Severity(SeverityLevel.MINOR)
    @WebDriverTest(capabilities = {"apply_command_mask"})
    public void imageLoadingTest(WebDriver webDriver) {
        CommonSteps.navigateToHome(webDriver);
        signIntoImageNotLoadingAccount(webDriver);
        List<WebElement> images = webDriver.findElements(By.tagName(Constants.ElementLocators.PRODUCT_IMAGE_TAG));
        images.forEach(image ->
                Assertions.assertTrue(image.getAttribute(Constants.ElementLocators.PRODUCT_IMAGE_SOURCE_ATTRIBUTE).isEmpty(), Constants.ErrorMessages.IMAGE_NOT_LOADING));
    }

    @Step("Signing in with image_not_loading credentials")
    private void signIntoImageNotLoadingAccount(WebDriver webDriver) {
        String password = UserCredentialUtil.getPassword(IMAGE_NOT_LOADING_ACCOUNT_USER_NAME);
        CommonSteps.signIn(IMAGE_NOT_LOADING_ACCOUNT_USER_NAME, password, webDriver);
        ElementLocatorUtil.waitUntilURLContains(webDriver, Constants.EndPoints.SIGNED_IN, Constants.ErrorMessages.SIGNIN_NOT_COMPLETED_ON_TIME);
    }
}