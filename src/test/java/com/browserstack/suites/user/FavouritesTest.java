package com.browserstack.suites.user;

import com.browserstack.utils.WebDriverWaitUtil;
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
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import static com.browserstack.utils.helpers.Constants.AllureTags.EPIC_USER;
import static com.browserstack.utils.helpers.Constants.AllureTags.FEATURE_FAVOURITES;
import static com.browserstack.utils.helpers.Constants.ElementLocators.*;
import static com.browserstack.utils.helpers.Constants.EndPoints.FAVOURITES;
import static com.browserstack.utils.helpers.Constants.EndPoints.SIGNED_IN;
import static com.browserstack.utils.helpers.Constants.ErrorMessages.*;

@Epic(EPIC_USER)
@Feature(FEATURE_FAVOURITES)
public class FavouritesTest {

    @Step("Signing in with {0}'s credentials")
    private void signIntoAccount(String user, WebDriver webDriver) {
        String password = UserCredentialsParser.getPassword(user);
        CommonSteps.signIn(user, password, webDriver);
        ElementLocatorUtil.waitUntilURLContains(webDriver, SIGNED_IN, SIGNIN_NOT_COMPLETED_ON_TIME);
    }

    @Step("Checking favourites")
    private int markingFavouritesTest(WebDriver webDriver) {
        webDriver.findElement(By.xpath(FAVOURITE_BUTTON_XPATH)).click();
        try {
            WebDriverWaitUtil.getWebDriverWait(webDriver).until(waitWebDriver -> !waitWebDriver.findElements(By.xpath(FAVOURITE_BUTTON_CLICKED_XPATH)).isEmpty());
        } catch (TimeoutException e) {
            ElementLocatorUtil.waitUntilElementAppears(webDriver, By.xpath(FAVOURITE_BUTTON_CLICKED_XPATH), FAVOURITES_BUT_NOT_CLICKED_ON_TIME);
        }
        webDriver.findElement(By.id(FAVOURITES_BUTTON_ID)).click();
        ElementLocatorUtil.waitUntilURLContains(webDriver, FAVOURITES, FAVOURITES_PAGE_NOT_LOADED_ON_TIME);
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.className(PRODUCT_CARD_CSS), FAVOURITES_ITEMS_NOT_LOADED_ON_TIME);
        return CommonSteps.productCount(webDriver);
    }

    @WebDriverTest(specificCapabilities = {"apply_mask"})
    @CsvSource({"existing_orders_user"})
    @Description("Testing marking favourites feature")
    public void markingFavouritesTest(String user, WebDriver webDriver) {
        CommonSteps.navigateToHome(webDriver);
        signIntoAccount(user, webDriver);
        int favourites = markingFavouritesTest(webDriver);
        Assertions.assertNotEquals(favourites, 0, FAVOURITES_COUNT);
    }
}
