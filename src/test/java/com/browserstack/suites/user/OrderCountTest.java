package com.browserstack.suites.user;

import com.browserstack.NonPageObjectTest;
import com.browserstack.utils.UserCredentialUtil;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.browserstack.utils.Constants.ElementLocators.BAG_QUANTITY_LABEL_CLASS;
import static com.browserstack.utils.Constants.ElementLocators.BUY_BUTTON_CLASS;
import static com.browserstack.utils.Constants.EndPoints.SIGNED_IN;
import static com.browserstack.utils.Constants.ErrorMessages.EMPTY_CART;

public class OrderCountTest extends NonPageObjectTest {

    private static final String EXISTING_ORDERS_ACCOUNT_USER_NAME = "existing_orders_user";
    private static final long LOGIN_TIME_OUT = 5;
    private static final long BAG_LABEL_LOAD_TIME_OUT = 10;

    @Override
    public void preProcess(DesiredCapabilities desiredCapabilities) {
        applyMask(desiredCapabilities);
    }

    public int checkExistingOrders(WebDriver webDriver) {
        String password = UserCredentialUtil.getPassword(EXISTING_ORDERS_ACCOUNT_USER_NAME);
        signIn(webDriver, EXISTING_ORDERS_ACCOUNT_USER_NAME, password);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, LOGIN_TIME_OUT);
        webDriverWait.until(ExpectedConditions.urlContains(SIGNED_IN));
        webDriver.findElement(By.className(BUY_BUTTON_CLASS)).click();
        WebElement cartValue = webDriver.findElement(new By.ByClassName(BAG_QUANTITY_LABEL_CLASS));
        webDriverWait = new WebDriverWait(webDriver, BAG_LABEL_LOAD_TIME_OUT);
        webDriverWait.until(ExpectedConditions.visibilityOf(cartValue));
        return Integer.parseInt(cartValue.getText());
    }

    @Override
    public void run(WebDriver webDriver) {
        navigateToHome(webDriver);
        int orders = checkExistingOrders(webDriver);
        Assertions.assertNotEquals(orders, 0, () -> markFail(EMPTY_CART));
    }

    @Override
    public void postProcess(WebDriver webDriver) {
        logOut(webDriver);
    }
}
