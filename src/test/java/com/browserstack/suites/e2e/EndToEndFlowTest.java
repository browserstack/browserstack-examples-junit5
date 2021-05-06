package com.browserstack.suites.e2e;

import com.browserstack.pages.*;
import com.browserstack.utils.config.WebDriverFactory;
import com.browserstack.utils.extensions.WebDriverTest;
import com.browserstack.utils.helpers.ElementLocatorUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.browserstack.utils.helpers.Constants.AllureTags.EPIC_END_TO_END;
import static com.browserstack.utils.helpers.Constants.AllureTags.FEATURE_END_TO_END_FLOW;
import static com.browserstack.utils.helpers.Constants.ElementLocators.*;
import static com.browserstack.utils.helpers.Constants.ErrorMessages.*;

@Epic(EPIC_END_TO_END)
@Feature(FEATURE_END_TO_END_FLOW)
public class EndToEndFlowTest {

    private static final String ORDER_PLACED_MESSAGE = "Your Order has been successfully placed.";

    @WebDriverTest(specificCapabilities = {"apply_mask"})
    @CsvSource({"fav_user,3"})
    @Description("Testing end to end flow of order placement")
    public void orderPlacementTest(String user, String productCount, WebDriver webDriver) {
        HomePage homePage = new HomePage(WebDriverFactory.getInstance().getTestEndpoint(), webDriver);
        ElementLocatorUtil.waitUntilTitleIs(webDriver, HOME_PAGE_TITLE, HOME_PAGE_NOT_LOADED_ON_TIME);
        ElementLocatorUtil.waitUntilElementVanish(webDriver, By.xpath(RELOAD_SPINNER_XPATH), SPINNER_NOT_STOPPED_ON_TIME);
        Login login = homePage.getNavBarComponent().clickOnSignIn();
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.id(USER_INPUT_ID), SIGNIN_PAGE_NOT_LOADED_ON_TIME);
        login.loginWithFavUser(user);
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.className(USERNAME_LABEL_CLASS), SIGNIN_NOT_COMPLETED_ON_TIME);
        int productCountInt = Integer.parseInt(productCount);
        Bag bag = homePage.addItemsToCart(productCountInt);
        Checkout checkout = bag.checkout();
        Confirmation confirmation = checkout.clickSubmit();
        String message = confirmation.getConfirmationMessage();
        int count = confirmation.getProductCount();
        Assertions.assertEquals(ORDER_PLACED_MESSAGE, message, CONFIRMATION_FAILED);
        Assertions.assertEquals(productCountInt, count, PRODUCT_COUNT_MISMATCH);
        confirmation.clickOnContinue();
        HomePage newHomePage = new HomePage(login.getNavBarComponent(), webDriver);
        Orders orders = newHomePage.getNavBarComponent().clickOnOrders();
        count = orders.countOrders();
        Assertions.assertEquals(productCountInt, count, PRODUCT_COUNT_MISMATCH);
    }
}
