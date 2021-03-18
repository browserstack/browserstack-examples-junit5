package com.browserstack.suites.e2e;

import com.browserstack.AbstractTest;
import com.browserstack.pages.*;
import com.browserstack.utils.JsonUtil;
import com.browserstack.utils.LoggedInNavBarComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;

import static com.browserstack.utils.Constants.Instances.APPLICATION_INSTANCE_PRIVATE;
import static com.browserstack.utils.Constants.Instances.APPLICATION_INSTANCE_PUBLIC;
import static com.browserstack.utils.Constants.Profiles.PROFILE_LOCAL;
import static com.browserstack.utils.Constants.Profiles.PROFILE_LOCAL_PARALLEL;

public class EndToEndFlowTest extends AbstractTest {

    private String url;
    private static final String ORDER_PLACED_MESSAGE="Your Order has been successfully placed.";
    private static final int PRODUCT_COUNT = 3;

    @BeforeEach
    public void init(TestInfo testInfo) {
        switch (testInfo.getTags().iterator().next()){
            case PROFILE_LOCAL:
            case PROFILE_LOCAL_PARALLEL:
                url = JsonUtil.getInstanceURL(APPLICATION_INSTANCE_PRIVATE);
                break;
            default:url = JsonUtil.getInstanceURL(APPLICATION_INSTANCE_PUBLIC);
        }
    }

    @Override
    public void run(WebDriver webDriver) {
        HomePage homePage = new HomePage(url, webDriver);
        Login login = homePage.getLoggedOutNavBar().clickSignIn();
        login.loginWithFavUser();
        Bag bag = homePage.addItemsToCart(PRODUCT_COUNT);
        Checkout checkout = bag.checkout();
        Confirmation confirmation = checkout.clickSubmit();
        String message = confirmation.getConfirmationMessage();
        int count = confirmation.getProductCount();
        Assertions.assertEquals(ORDER_PLACED_MESSAGE, message);
        Assertions.assertEquals(PRODUCT_COUNT, count);
        confirmation.clickOnContinue();
        Orders orders = homePage.getLoggedInNavBar().clickOnOrders();
        count = orders.countOrders();
        Assertions.assertEquals(PRODUCT_COUNT, count);
    }

    @Override
    public void postProcess(WebDriver webDriver) {
      LoggedInNavBarComponent navBarComponent = new LoggedInNavBarComponent(webDriver);
      if (navBarComponent.isLoggedIn()){
          navBarComponent.logOut();
      }
    }
}
