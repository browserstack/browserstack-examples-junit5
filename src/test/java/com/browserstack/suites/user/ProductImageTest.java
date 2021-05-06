package com.browserstack.suites.user;

import com.browserstack.utils.config.UserCredentialsParser;
import com.browserstack.utils.extensions.WebDriverTest;
import com.browserstack.utils.helpers.CommonSteps;
import com.browserstack.utils.helpers.ElementLocatorUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.browserstack.utils.helpers.Constants.AllureTags.EPIC_USER;
import static com.browserstack.utils.helpers.Constants.AllureTags.FEATURE_PRODUCT_IMAGE;
import static com.browserstack.utils.helpers.Constants.ElementLocators.PRODUCT_IMAGE_SOURCE_ATTRIBUTE;
import static com.browserstack.utils.helpers.Constants.ElementLocators.PRODUCT_IMAGE_TAG;
import static com.browserstack.utils.helpers.Constants.EndPoints.SIGNED_IN;
import static com.browserstack.utils.helpers.Constants.ErrorMessages.IMAGE_NOT_LOADING;
import static com.browserstack.utils.helpers.Constants.ErrorMessages.SIGNIN_NOT_COMPLETED_ON_TIME;

@Epic(EPIC_USER)
@Feature(FEATURE_PRODUCT_IMAGE)
public class ProductImageTest {

    @Step("Signing in with {0}'s credentials")
    private void signIntoAccount(String user, WebDriver webDriver) {
        String password = UserCredentialsParser.getPassword(user);
        CommonSteps.signIn(user, password, webDriver);
        ElementLocatorUtil.waitUntilURLContains(webDriver, SIGNED_IN, SIGNIN_NOT_COMPLETED_ON_TIME);
    }

    @WebDriverTest(specificCapabilities = {"apply_mask"})
    @CsvSource({"image_not_loading_user"})
    @Description("Testing image loading feature")
    public void imageLoadingTest(String user, WebDriver webDriver) {
        CommonSteps.navigateToHome(webDriver);
        signIntoAccount(user, webDriver);
        List<WebElement> images = webDriver.findElements(By.tagName(PRODUCT_IMAGE_TAG));
        images.stream().forEach(image ->
                Assertions.assertTrue(image.getAttribute(PRODUCT_IMAGE_SOURCE_ATTRIBUTE).isEmpty(), IMAGE_NOT_LOADING));
    }

}
