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

import static com.browserstack.utils.helpers.Constants.AllureTags.EPIC_USER;
import static com.browserstack.utils.helpers.Constants.AllureTags.FEATURE_PURCHASE;
import static com.browserstack.utils.helpers.Constants.ElementLocators.BAG_QUANTITY_LABEL_CLASS;
import static com.browserstack.utils.helpers.Constants.ElementLocators.BUY_BUTTON_CLASS;
import static com.browserstack.utils.helpers.Constants.EndPoints.SIGNED_IN;
import static com.browserstack.utils.helpers.Constants.ErrorMessages.*;

@Epic(EPIC_USER)
@Feature(FEATURE_PURCHASE)
public class PurchaseTest {

    @Step("Signing in with {0}'s credentials")
    private void signIntoAccount(String user, WebDriver webDriver) {
        String password = UserCredentialsParser.getPassword(user);
        CommonSteps.signIn(user, password,webDriver);
        ElementLocatorUtil.waitUntilURLContains(webDriver, SIGNED_IN, SIGNIN_NOT_COMPLETED_ON_TIME);
    }

    @Step("Checking orders")
    private int checkExistingOrders(WebDriver webDriver) {
        webDriver.findElement(By.className(BUY_BUTTON_CLASS)).click();
        ElementLocatorUtil.waitUntilIntAppears(webDriver, By.className(BAG_QUANTITY_LABEL_CLASS), CART_NOT_LOADED_ON_TIME);
        WebElement cartValue = webDriver.findElement(By.className(BAG_QUANTITY_LABEL_CLASS));
        return Integer.parseInt(cartValue.getText());
    }

    @WebDriverTest(specificCapabilities = {"apply_mask"})
    @CsvSource({"existing_orders_user"})
    @Description("Testing purchase confirmation count")
    public void orderCountTest(String user, WebDriver webDriver) {
        CommonSteps.navigateToHome(webDriver);
        signIntoAccount(user,webDriver);
        int orders = checkExistingOrders(webDriver);
        Assertions.assertNotEquals(orders, 0, EMPTY_CART);
    }

}
