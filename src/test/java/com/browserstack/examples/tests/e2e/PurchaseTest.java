package com.browserstack.examples.tests.e2e;

import com.browsersatck.examples.pages.*;
import com.browsersatck.examples.utils.LoggedInNavBarComponent;
import com.browserstack.examples.config.WebDriverFactory;
import com.browserstack.examples.extensions.WebDriverTest;
import com.browserstack.examples.helpers.Constants;
import com.browserstack.examples.helpers.ElementLocatorUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Epic(Constants.AllureTags.EPIC_END_TO_END)
@Story(Constants.AllureTags.STORY_PURCHASE)
public class PurchaseTest {

    private static final String ORDER_PLACED_MESSAGE = "Your Order has been successfully placed.";
    private static final String USER = "fav_user";
    private static final int PRODUCT_COUNT = 3;

    @WebDriverTest(capabilities = {"apply_command_mask"})
    public void orderPlacementTest(WebDriver webDriver) {
        HomePage homePage = new HomePage(WebDriverFactory.getInstance().getTestEndpoint(), webDriver);
        ElementLocatorUtil.waitUntilTitleIs(webDriver, Constants.ElementLocators.HOME_PAGE_TITLE, Constants.ErrorMessages.HOME_PAGE_NOT_LOADED_ON_TIME);
        ElementLocatorUtil.waitUntilElementVanish(webDriver, By.xpath(Constants.ElementLocators.RELOAD_SPINNER_XPATH), Constants.ErrorMessages.SPINNER_NOT_STOPPED_ON_TIME);
        Login login = homePage.getNavBarComponent().clickSignIn();
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.id(Constants.ElementLocators.USER_INPUT_ID), Constants.ErrorMessages.SIGNIN_PAGE_NOT_LOADED_ON_TIME);
        login.loginWithFavUser(USER);
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.className(Constants.ElementLocators.USERNAME_LABEL_CLASS), Constants.ErrorMessages.SIGNIN_NOT_COMPLETED_ON_TIME);
        Bag bag = homePage.addItemsToCart(PRODUCT_COUNT);
        Checkout checkout = bag.checkout();
        Confirmation confirmation = checkout.clickSubmit();
        String message = confirmation.getConfirmationMessage();
        int count = confirmation.getProductCount();
        Assertions.assertEquals(ORDER_PLACED_MESSAGE, message, Constants.ErrorMessages.CONFIRMATION_FAILED);
        Assertions.assertEquals(PRODUCT_COUNT, count, Constants.ErrorMessages.PRODUCT_COUNT_MISMATCH);
        confirmation.clickOnContinue();
        HomePage newHomePage = new HomePage(login.getNavBarComponent(), webDriver);
        Orders orders = ((LoggedInNavBarComponent) newHomePage.getNavBarComponent()).clickOnOrders();
        count = orders.countOrders();
        Assertions.assertEquals(PRODUCT_COUNT, count, Constants.ErrorMessages.PRODUCT_COUNT_MISMATCH);
    }
}