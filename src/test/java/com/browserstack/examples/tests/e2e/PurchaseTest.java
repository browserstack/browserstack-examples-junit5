package com.browserstack.examples.tests.e2e;

import com.browserstack.examples.config.WebDriverFactory;
import com.browserstack.examples.extensions.WebDriverTest;
import com.browserstack.examples.helpers.ElementLocatorUtil;
import com.browserstack.pages.*;
import com.browserstack.utils.LoggedInNavBarComponent;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.browserstack.examples.helpers.Constants.AllureTags.EPIC_END_TO_END;
import static com.browserstack.examples.helpers.Constants.AllureTags.STORY_PURCHASE;
import static com.browserstack.examples.helpers.Constants.ElementLocators.*;
import static com.browserstack.examples.helpers.Constants.ErrorMessages.*;

@Epic(EPIC_END_TO_END)
@Story(STORY_PURCHASE)
public class PurchaseTest {

    private static final String ORDER_PLACED_MESSAGE = "Your Order has been successfully placed.";
    private static final String USER = "fav_user";
    private static final int PRODUCT_COUNT = 3;

    @WebDriverTest
    public void orderPlacementTest(WebDriver webDriver) {
        HomePage homePage = new HomePage(WebDriverFactory.getInstance().getTestEndpoint(), webDriver);
        ElementLocatorUtil.waitUntilTitleIs(webDriver, HOME_PAGE_TITLE, HOME_PAGE_NOT_LOADED_ON_TIME);
        ElementLocatorUtil.waitUntilElementVanish(webDriver, By.xpath(RELOAD_SPINNER_XPATH), SPINNER_NOT_STOPPED_ON_TIME);
        Login login = homePage.getNavBarComponent().clickSignIn();
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.id(USER_INPUT_ID), SIGNIN_PAGE_NOT_LOADED_ON_TIME);
        login.loginWithFavUser(USER);
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.className(USERNAME_LABEL_CLASS), SIGNIN_NOT_COMPLETED_ON_TIME);
        Bag bag = homePage.addItemsToCart(PRODUCT_COUNT);
        Checkout checkout = bag.checkout();
        Confirmation confirmation = checkout.clickSubmit();
        String message = confirmation.getConfirmationMessage();
        int count = confirmation.getProductCount();
        Assertions.assertEquals(ORDER_PLACED_MESSAGE, message, CONFIRMATION_FAILED);
        Assertions.assertEquals(PRODUCT_COUNT, count, PRODUCT_COUNT_MISMATCH);
        confirmation.clickOnContinue();
        HomePage newHomePage = new HomePage(login.getNavBarComponent(), webDriver);
        Orders orders = ((LoggedInNavBarComponent) newHomePage.getNavBarComponent()).clickOnOrders();
        count = orders.countOrders();
        Assertions.assertEquals(PRODUCT_COUNT, count, PRODUCT_COUNT_MISMATCH);
    }
}