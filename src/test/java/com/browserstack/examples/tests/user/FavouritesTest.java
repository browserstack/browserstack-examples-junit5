package com.browserstack.examples.tests.user;

import com.browsersatck.examples.utils.UserCredentialUtil;
import com.browsersatck.examples.utils.WebDriverWaitUtil;
import com.browserstack.examples.extensions.WebDriverTest;
import com.browserstack.examples.helpers.ElementLocatorUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import static com.browserstack.examples.helpers.CommonSteps.*;
import static com.browserstack.examples.helpers.Constants.AllureTags.EPIC_USER;
import static com.browserstack.examples.helpers.Constants.AllureTags.STORY_FAVOURITES;
import static com.browserstack.examples.helpers.Constants.ElementLocators.*;
import static com.browserstack.examples.helpers.Constants.EndPoints.FAVOURITES;
import static com.browserstack.examples.helpers.Constants.EndPoints.SIGNED_IN;
import static com.browserstack.examples.helpers.Constants.ErrorMessages.*;

@Epic(EPIC_USER)
@Story(STORY_FAVOURITES)
public class FavouritesTest {

    private static final String EXISTING_ORDERS_ACCOUNT_USER_NAME = "existing_orders_user";

    @WebDriverTest(capabilities = {"apply_command_mask"})
    public void favouritesCountTest(WebDriver webDriver) {
        navigateToHome(webDriver);
        signIntoExistingOrdersAccount(webDriver);
        int favourites = testFavourites(webDriver);
        Assertions.assertNotEquals(favourites, 0, FAVOURITES_COUNT);
    }

    @Step("Checking favourites")
    private int testFavourites(WebDriver webDriver) {
        webDriver.findElement(By.xpath(FAVOURITE_BUTTON_XPATH)).click();
        try {
            WebDriverWaitUtil.getWebDriverWait(webDriver).until(waitWebDriver -> !waitWebDriver.findElements(By.xpath(FAVOURITE_BUTTON_CLICKED_XPATH)).isEmpty());
        } catch (TimeoutException e) {
            ElementLocatorUtil.waitUntilElementAppears(webDriver, By.xpath(FAVOURITE_BUTTON_CLICKED_XPATH), FAVOURITES_BUT_NOT_CLICKED_ON_TIME);
        }
        webDriver.findElement(By.id(FAVOURITES_BUTTON_ID)).click();
        ElementLocatorUtil.waitUntilURLContains(webDriver, FAVOURITES, FAVOURITES_PAGE_NOT_LOADED_ON_TIME);
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.className(PRODUCT_CARD_CSS), FAVOURITES_ITEMS_NOT_LOADED_ON_TIME);
        return productCount(webDriver);
    }

    @Step("Signing in with existing_orders credentials")
    private void signIntoExistingOrdersAccount(WebDriver webDriver) {
        String password = UserCredentialUtil.getPassword(EXISTING_ORDERS_ACCOUNT_USER_NAME);
        signIn(EXISTING_ORDERS_ACCOUNT_USER_NAME, password, webDriver);
        ElementLocatorUtil.waitUntilURLContains(webDriver, SIGNED_IN, SIGNIN_NOT_COMPLETED_ON_TIME);
    }
}