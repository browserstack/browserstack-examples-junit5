package com.browserstack.suites.offers;

import com.browserstack.utils.config.UserCredentialsParser;
import com.browserstack.utils.extensions.WebDriverTest;
import com.browserstack.utils.helpers.CommonSteps;
import com.browserstack.utils.helpers.ElementLocatorUtil;
import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static com.browserstack.utils.helpers.Constants.AllureTags.EPIC_OFFERS;
import static com.browserstack.utils.helpers.Constants.AllureTags.FEATURE_OFFERS;
import static com.browserstack.utils.helpers.Constants.ElementLocators.OFFERS_BUTTON_ID;
import static com.browserstack.utils.helpers.Constants.ElementLocators.OFFER_CARD_CLASS;
import static com.browserstack.utils.helpers.Constants.EndPoints.OFFERS;
import static com.browserstack.utils.helpers.Constants.EndPoints.SIGNED_IN;
import static com.browserstack.utils.helpers.Constants.ErrorMessages.*;

@Epic(EPIC_OFFERS)
@Feature(FEATURE_OFFERS)
public class OffersTest {

    private static final String OFFER_MESSAGE = "We've promotional offers in your location.";
    private static final String LOCATION_SCRIPT_FORMAT = "navigator.geolocation.getCurrentPosition = function(success){\n" +
            "    var position = { \"coords\":{\"latitude\":\"%s\",\"longitude\":\"%s\"}};\n" +
            "    success(position);\n" +
            "}";

    @Step("Signing in with {0}'s credentials")
    private void signIntoAccount(String user, WebDriver webDriver) {
        String password = UserCredentialsParser.getPassword(user);
        CommonSteps.signIn(user, password, webDriver);
        ElementLocatorUtil.waitUntilURLContains(webDriver, SIGNED_IN, SIGNIN_NOT_COMPLETED_ON_TIME);
    }

    @Step("Mocking the GPS location")
    private void mockGPS(String latitude, String longitude, WebDriver webDriver) {
        String locationScript = String.format(LOCATION_SCRIPT_FORMAT, latitude, longitude);
        ((JavascriptExecutor) webDriver).executeScript(locationScript);
    }

    @Step("Checking if offers are available")
    private void checkIfOfferMessageAvailable(WebDriver webDriver) {
        webDriver.findElement(By.id(OFFERS_BUTTON_ID)).click();
        ElementLocatorUtil.waitUntilURLContains(webDriver, OFFERS, OFFERS_PAGE_NOT_LOADED_ON_TIME);
        Assertions.assertTrue(webDriver.getPageSource().contains(OFFER_MESSAGE), OFFER_MESSAGES_NOT_FOUND);
    }

    @WebDriverTest
    @CsvSource({"existing_orders_user,1,103"})
    @Description("Testing gps location based offers feature")
    public void gpsOffersTest(String user, String latitude, String longitude, WebDriver webDriver) {
        CommonSteps.navigateToHome(webDriver);
        signIntoAccount(user, webDriver);
        mockGPS(latitude, longitude, webDriver);
        checkIfOfferMessageAvailable(webDriver);
        ElementLocatorUtil.waitUntilElementAppears(webDriver, By.className(OFFER_CARD_CLASS), NO_OFFERS_LOADED);
    }

}