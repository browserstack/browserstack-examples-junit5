package com.browserstack.examples.tests.offers;

import com.browserstack.examples.extensions.WebDriverTest;
import com.browserstack.examples.helpers.ElementLocatorUtil;
import com.browsersatck.examples.utils.UserCredentialUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static com.browserstack.examples.helpers.CommonSteps.navigateToHome;
import static com.browserstack.examples.helpers.CommonSteps.signIn;
import static com.browserstack.examples.helpers.Constants.AllureTags.EPIC_OFFERS;
import static com.browserstack.examples.helpers.Constants.AllureTags.STORY_REGIONAL_OFFERS;
import static com.browserstack.examples.helpers.Constants.ElementLocators.OFFERS_BUTTON_ID;
import static com.browserstack.examples.helpers.Constants.ElementLocators.OFFER_CARD_CLASS;
import static com.browserstack.examples.helpers.Constants.EndPoints.OFFERS;
import static com.browserstack.examples.helpers.Constants.EndPoints.SIGNED_IN;
import static com.browserstack.examples.helpers.Constants.ErrorMessages.*;

@Epic(EPIC_OFFERS)
@Story(STORY_REGIONAL_OFFERS)
public class RegionalOffersTest {

    private static final String FAVOURITE_ACCOUNT_USER_NAME = "existing_orders_user";
    private static final String OFFER_MESSAGE = "We've promotional offers in your location.";
    private static final String LOCATION_SCRIPT_FORMAT = "navigator.geolocation.getCurrentPosition = function(success){\n" +
            "    var position = { \"coords\":{\"latitude\":\"%s\",\"longitude\":\"%s\"}};\n" +
            "    success(position);\n" +
            "}";
    private static final String OFFER_LATITUDE = "20";
    private static final String OFFER_LONGITUDE = "70";


    @WebDriverTest(capabilities = {"apply_command_mask"})
    public void offersLoadedTest(WebDriver webDriver) {
        navigateToHome(webDriver);
        signIntoFavouriteAccount(webDriver);
        mockGPS(webDriver);
        checkIfOfferMessageAvailable(webDriver);
        checkIfOffersAreLoaded(webDriver);
    }

    @Step("Signing in with fav_account credentials")
    private void signIntoFavouriteAccount(WebDriver webDriver) {
        String password = UserCredentialUtil.getPassword(FAVOURITE_ACCOUNT_USER_NAME);
        signIn(FAVOURITE_ACCOUNT_USER_NAME, password, webDriver);
        ElementLocatorUtil.waitUntilURLContains(webDriver, SIGNED_IN, SIGNIN_NOT_COMPLETED_ON_TIME);
    }

    @Step("Mocking the GPS location")
    private void mockGPS(WebDriver webDriver) {
        String locationScript = String.format(LOCATION_SCRIPT_FORMAT, OFFER_LATITUDE, OFFER_LONGITUDE);
        ((JavascriptExecutor) webDriver).executeScript(locationScript);
    }

    @Step("Checking if offers are available")
    private void checkIfOfferMessageAvailable(WebDriver webDriver) {
        webDriver.findElement(By.id(OFFERS_BUTTON_ID)).click();
        ElementLocatorUtil.waitUntilURLContains(webDriver, OFFERS, OFFERS_PAGE_NOT_LOADED_ON_TIME);
        Assertions.assertTrue(webDriver.getPageSource().contains(OFFER_MESSAGE), OFFER_MESSAGES_NOT_FOUND);
    }

    @Step("Checking if offers are loaded")
    private void checkIfOffersAreLoaded(WebDriver webDriver) {
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.className(OFFER_CARD_CLASS), NO_OFFERS_LOADED);
    }
}