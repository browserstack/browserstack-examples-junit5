package com.browserstack.suites.e2e;

import com.browserstack.AbstractTest;
import com.browserstack.pages.*;
import com.browserstack.utils.ElementLocatorUtil;
import com.browserstack.utils.JsonUtil;
import com.browserstack.utils.LoggedInNavBarComponent;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.browserstack.utils.Constants.AllureTags.EPIC_END_TO_END;
import static com.browserstack.utils.Constants.AllureTags.STORY_END_TO_END;
import static com.browserstack.utils.Constants.ElementLocators.*;
import static com.browserstack.utils.Constants.ErrorMessages.*;
import static com.browserstack.utils.Constants.Instances.APPLICATION_INSTANCE_PRIVATE;
import static com.browserstack.utils.Constants.Instances.APPLICATION_INSTANCE_PUBLIC;
import static com.browserstack.utils.Constants.Profiles.PROFILE_LOCAL;
import static com.browserstack.utils.Constants.Profiles.PROFILE_LOCAL_PARALLEL;

@Epic(EPIC_END_TO_END)
@Story(STORY_END_TO_END)
public class EndToEndFlowTest extends AbstractTest {

    private static final String ORDER_PLACED_MESSAGE = "Your Order has been successfully placed.";
    private static final int PRODUCT_COUNT = 3;

    private String url;

    @BeforeEach
    public void init(TestInfo testInfo) {
        switch (testInfo.getTags().iterator().next()) {
            case PROFILE_LOCAL:
            case PROFILE_LOCAL_PARALLEL:
                url = JsonUtil.getInstanceURLByName(APPLICATION_INSTANCE_PRIVATE);
                break;
            default:
                url = JsonUtil.getInstanceURLByName(APPLICATION_INSTANCE_PUBLIC);
        }
    }

    @Override
    @Step("Running the test")
    public void run(WebDriver webDriver) {
        HomePage homePage = new HomePage(url, webDriver);
        ElementLocatorUtil.waitUntilTitleIs(webDriver, this, HOME_PAGE_TITLE, HOME_PAGE_NOT_LOADED_ON_TIME);
        ElementLocatorUtil.waitUntilElementVanish(webDriver, this, By.xpath(RELOAD_SPINNER_XPATH), SPINNER_NOT_STOPPED_ON_TIME);
        Login login = homePage.getLoggedOutNavBar().clickSignIn();
        ElementLocatorUtil.waitUntilElementAppears(webDriver, this, By.xpath(USER_INPUT_XPATH), SIGNIN_PAGE_NOT_LOADED_ON_TIME);
        login.loginWithFavUser();
        ElementLocatorUtil.waitUntilElementAppears(webDriver, this, By.className(USERNAME_LABEL_CLASS), SIGNIN_NOT_COMPLETED_ON_TIME);
        Bag bag = homePage.addItemsToCart(PRODUCT_COUNT);
        Checkout checkout = bag.checkout();
        Confirmation confirmation = checkout.clickSubmit();
        String message = confirmation.getConfirmationMessage();
        int count = confirmation.getProductCount();
        Assertions.assertEquals(ORDER_PLACED_MESSAGE, message, () -> markFail(CONFIRMATION_FAILED));
        Assertions.assertEquals(PRODUCT_COUNT, count, () -> markFail(PRODUCT_COUNT_MISMATCH));
        confirmation.clickOnContinue();
        Orders orders = homePage.getLoggedInNavBar().clickOnOrders();
        count = orders.countOrders();
        Assertions.assertEquals(PRODUCT_COUNT, count, () -> markFail(PRODUCT_COUNT_MISMATCH));
    }

    @Override
    @Step("Post processing")
    public void postProcess(WebDriver webDriver) {
        LoggedInNavBarComponent navBarComponent = new LoggedInNavBarComponent(webDriver);
        if (navBarComponent.isLoggedIn()) {
            navBarComponent.logOut();
        }
    }
}
