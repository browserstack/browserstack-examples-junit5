package com.browserstack.examples.tests.user;

import com.browserstack.examples.extensions.WebDriverTest;
import com.browserstack.examples.helpers.CommonSteps;
import com.browserstack.examples.helpers.Constants;
import com.browserstack.examples.helpers.ElementLocatorUtil;
import com.browsersatck.examples.utils.UserCredentialUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Epic(Constants.AllureTags.EPIC_USER)
@Story(Constants.AllureTags.STORY_ORDER)
public class OrderTest {

    private static final String EXISTING_ORDERS_ACCOUNT_USER_NAME = "existing_orders_user";

    @WebDriverTest(capabilities = {"apply_command_mask"})
    public void existingOrdersTest(WebDriver webDriver) {
        CommonSteps.navigateToHome(webDriver);
        signIntoExistingOrdersAccount(webDriver);
        int orders = checkExistingOrders(webDriver);
        Assertions.assertNotEquals(orders, 0, Constants.ErrorMessages.EMPTY_CART);
    }

    @Step("Checking orders")
    private int checkExistingOrders(WebDriver webDriver) {
        webDriver.findElement(By.id(Constants.ElementLocators.ORDERS_BUTTON_ID)).click();
        ElementLocatorUtil.waitUntilURLContains(webDriver, Constants.EndPoints.ORDERS, Constants.ErrorMessages.ORDERS_PAGE_NOT_LOADED_ON_TIME);
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.xpath(Constants.ElementLocators.PRODUCT_COST_XPATH), Constants.ErrorMessages.ORDERS_NOT_LOADED_ON_TIME);
        return webDriver.findElements(By.xpath(Constants.ElementLocators.PRODUCT_COST_XPATH)).size();
    }

    @Step("Signing in with existing_orders credentials")
    private void signIntoExistingOrdersAccount(WebDriver webDriver) {
        String password = UserCredentialUtil.getPassword(EXISTING_ORDERS_ACCOUNT_USER_NAME);
        CommonSteps.signIn(EXISTING_ORDERS_ACCOUNT_USER_NAME, password, webDriver);
        ElementLocatorUtil.waitUntilURLContains(webDriver, Constants.EndPoints.SIGNED_IN, Constants.ErrorMessages.SIGNIN_NOT_COMPLETED_ON_TIME);
    }
}