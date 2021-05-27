package com.browserstack.examples.tests.user;

import com.browserstack.examples.extensions.WebDriverTest;
import com.browserstack.examples.helpers.ElementLocatorUtil;
import com.browserstack.utils.UserCredentialUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.browserstack.examples.helpers.CommonSteps.navigateToHome;
import static com.browserstack.examples.helpers.CommonSteps.signIn;
import static com.browserstack.examples.helpers.Constants.AllureTags.EPIC_USER;
import static com.browserstack.examples.helpers.Constants.AllureTags.STORY_ORDER;
import static com.browserstack.examples.helpers.Constants.ElementLocators.ORDERS_BUTTON_ID;
import static com.browserstack.examples.helpers.Constants.ElementLocators.PRODUCT_COST_XPATH;
import static com.browserstack.examples.helpers.Constants.EndPoints.ORDERS;
import static com.browserstack.examples.helpers.Constants.EndPoints.SIGNED_IN;
import static com.browserstack.examples.helpers.Constants.ErrorMessages.*;

@Epic(EPIC_USER)
@Story(STORY_ORDER)
public class OrderTest {

    private static final String EXISTING_ORDERS_ACCOUNT_USER_NAME = "existing_orders_user";

    @WebDriverTest
    public void existingOrdersTest(WebDriver webDriver) {
        navigateToHome(webDriver);
        signIntoExistingOrdersAccount(webDriver);
        int orders = checkExistingOrders(webDriver);
        Assertions.assertNotEquals(orders, 0, () -> EMPTY_CART);
    }

    @Step("Checking orders")
    private int checkExistingOrders(WebDriver webDriver) {
        webDriver.findElement(By.id(ORDERS_BUTTON_ID)).click();
        ElementLocatorUtil.waitUntilURLContains(webDriver,ORDERS,ORDERS_PAGE_NOT_LOADED_ON_TIME);
        ElementLocatorUtil.waitUntilElementAppears(webDriver,By.xpath(PRODUCT_COST_XPATH),ORDERS_NOT_LOADED_ON_TIME);
        return webDriver.findElements(By.xpath(PRODUCT_COST_XPATH)).size();
    }

    @Step("Signing in with existing_orders credentials")
    private void signIntoExistingOrdersAccount(WebDriver webDriver) {
        String password = UserCredentialUtil.getPassword(EXISTING_ORDERS_ACCOUNT_USER_NAME);
        signIn(EXISTING_ORDERS_ACCOUNT_USER_NAME, password,webDriver);
        ElementLocatorUtil.waitUntilURLContains(webDriver, SIGNED_IN, SIGNIN_NOT_COMPLETED_ON_TIME);
    }
}