package com.browserstack.suites.user;

import static com.browserstack.utils.Constants.AllureTags.EPIC_USER;
import static com.browserstack.utils.Constants.AllureTags.STORY_IMAGE_LOADING;


import com.browserstack.NonPageObjectTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;

@Epic(EPIC_USER)
@Story(STORY_IMAGE_LOADING)
public class ImageLoadingTest extends NonPageObjectTest {

//    private static final String IMAGE_NOT_LOADING_ACCOUNT_USER_NAME = "image_not_loading_user";
//
//    @Override
//    @Step("Preprocessing capabilities")
//    public void preProcess(DesiredCapabilities desiredCapabilities) {
//        applyMask(desiredCapabilities);
//    }
//
//    @Override
//    @Step("Running the test")
//    public void run(WebDriver webDriver) {
//        navigateToHome(webDriver);
//        signIntoImageNotLoadingAccount(webDriver);
//        List<WebElement> images = webDriver.findElements(By.tagName(PRODUCT_IMAGE_TAG));
//        images.stream().forEach(image ->
//                Assertions.assertTrue(image.getAttribute(PRODUCT_IMAGE_SOURCE_ATTRIBUTE).isEmpty(), () -> markFail(IMAGE_NOT_LOADING)));
//    }
//
//    @Override
//    @Step("Post processing")
//    public void postProcess(WebDriver webDriver) {
//        logOut(webDriver);
//    }
//
//    @Step("Signing in with image_not_loading credentials")
//    private void signIntoImageNotLoadingAccount(WebDriver webDriver) {
//        String password = UserCredentialUtil.getPassword(IMAGE_NOT_LOADING_ACCOUNT_USER_NAME);
//        signIn(webDriver, IMAGE_NOT_LOADING_ACCOUNT_USER_NAME, password);
//        ElementLocatorUtil.waitUntilURLContains(webDriver, this, SIGNED_IN, SIGNIN_NOT_COMPLETED_ON_TIME);
//    }
}
