package com.browserstack.suites.user;

import static com.browserstack.utils.Constants.AllureTags.EPIC_USER;
import static com.browserstack.utils.Constants.AllureTags.STORY_ORDER_COUNT;


import com.browserstack.NonPageObjectTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;

@Epic(EPIC_USER)
@Story(STORY_ORDER_COUNT)
public class OrderCountTest extends NonPageObjectTest {

//    private static final String EXISTING_ORDERS_ACCOUNT_USER_NAME = "existing_orders_user";
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
//        signIntoExistingOrdersAccount(webDriver);
//        int orders = checkExistingOrders(webDriver);
//        Assertions.assertNotEquals(orders, 0, () -> markFail(EMPTY_CART));
//    }
//
//    @Override
//    @Step("Post processing")
//    public void postProcess(WebDriver webDriver) {
//        logOut(webDriver);
//    }
//
//    @Step("Checking orders")
//    private int checkExistingOrders(WebDriver webDriver) {
//        webDriver.findElement(By.className(BUY_BUTTON_CLASS)).click();
//        ElementLocatorUtil.waitUntilIntAppears(webDriver, this, By.className(BAG_QUANTITY_LABEL_CLASS), CART_NOT_LOADED_ON_TIME);
//
//        WebElement cartValue = webDriver.findElement(By.className(BAG_QUANTITY_LABEL_CLASS));
//        return Integer.parseInt(cartValue.getText());
//    }
//
//    @Step("Signing in with existing_orders credentials")
//    private void signIntoExistingOrdersAccount(WebDriver webDriver) {
//        String password = UserCredentialUtil.getPassword(EXISTING_ORDERS_ACCOUNT_USER_NAME);
//        signIn(webDriver, EXISTING_ORDERS_ACCOUNT_USER_NAME, password);
//        ElementLocatorUtil.waitUntilURLContains(webDriver, this, SIGNED_IN, SIGNIN_NOT_COMPLETED_ON_TIME);
//    }
}
