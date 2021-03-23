package com.browserstack.suites.offers;

import com.browserstack.NonPageObjectTest;
import com.browserstack.utils.ElementLocatorUtil;
import com.browserstack.utils.UserCredentialUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

import static com.browserstack.utils.Constants.AllureTags.EPIC_OFFERS;
import static com.browserstack.utils.Constants.AllureTags.STORY_OFFERS;
import static com.browserstack.utils.Constants.Capabilities.*;
import static com.browserstack.utils.Constants.ElementLocators.OFFERS_BUTTON_ID;
import static com.browserstack.utils.Constants.EndPoints.OFFERS;
import static com.browserstack.utils.Constants.EndPoints.SIGNED_IN;
import static com.browserstack.utils.Constants.ErrorMessages.*;

@Epic(EPIC_OFFERS)
@Story(STORY_OFFERS)
public class OffersTest extends NonPageObjectTest {

    private static final String FAVOURITE_ACCOUNT_USER_NAME = "existing_orders_user";
    private static final String OFFER_MESSAGE = "We've promotional offers in your location.";
    private static final String OFFER_CLASS = "offer";
    private static final String LOCATION_SCRIPT_FORMAT = "navigator.geolocation.getCurrentPosition = function(success){\n" +
            "    var position = { \"coords\":{\"latitude\":\"%s\",\"longitude\":\"%s\"}};\n" +
            "    success(position);\n" +
            "}";
    private static final String OFFER_LATITUDE = "1";
    private static final String OFFER_LONGITUDE = "103";

    @Override
    @Step("Preprocessing capabilities")
    public void preProcess(DesiredCapabilities desiredCapabilities) {
        setLocationSpecificCapabilities(desiredCapabilities);
    }

    @Override
    @Step("Running the test")
    public void run(WebDriver webDriver) {
        navigateToHome(webDriver);
        signIntoFavouriteAccount(webDriver);
        mockGPS(webDriver);
        checkIfOfferMessageAvailable(webDriver);
        ElementLocatorUtil.waitUntilElementAppears(webDriver, this, By.className(OFFER_CLASS), NO_OFFERS_LOADED);
    }

    @Override
    @Step("Post processing")
    public void postProcess(WebDriver webDriver) {
        logOut(webDriver);
    }

    @Step("Setting location specific capabilities")
    private void setLocationSpecificCapabilities(DesiredCapabilities desiredCapabilities){
        String browser = (String) desiredCapabilities.getCapability(CAPABILITY_BROWSER);
        if (browser != null) {
            if (browser.equals(CAPABILITY_VALUE_CHROME)) {
                desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
            } else if (browser.equals(CAPABILITY_VALUE_FIREFOX)) {
                desiredCapabilities.setCapability(FirefoxDriver.PROFILE, getFirefoxProfile());
            }
        }
    }


    @Step("Signing in with fav_account credentials")
    private void signIntoFavouriteAccount(WebDriver webDriver) {
        String password = UserCredentialUtil.getPassword(FAVOURITE_ACCOUNT_USER_NAME);
        signIn(webDriver, FAVOURITE_ACCOUNT_USER_NAME, password);
        ElementLocatorUtil.waitUntilURLContains(webDriver, this, SIGNED_IN, SIGNIN_NOT_COMPLETED_ON_TIME);
    }

    @Step("Mocking the GPS location")
    private void mockGPS(WebDriver webDriver) {
        String locationScript = String.format(LOCATION_SCRIPT_FORMAT, OFFER_LATITUDE, OFFER_LONGITUDE);
        ((JavascriptExecutor) webDriver).executeScript(locationScript);
    }

    @Step("Checking if offers are available")
    private void checkIfOfferMessageAvailable(WebDriver webDriver) {
        webDriver.findElement(By.id(OFFERS_BUTTON_ID)).click();
        ElementLocatorUtil.waitUntilURLContains(webDriver, this, OFFERS, OFFERS_PAGE_NOT_LOADED_ON_TIME);
        if (!webDriver.getPageSource().contains(OFFER_MESSAGE)) {
            markFail(OFFER_MESSAGES_NOT_FOUND);
        }
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        Map<String, Object> profile = new HashMap<>();
        Map<String, Object> contentSettings = new HashMap<>();
        contentSettings.put("geolocation", 1);
        profile.put("managed_default_content_settings", contentSettings);
        prefs.put("profile", profile);
        options.setExperimentalOption("prefs", prefs);
        return options;
    }

    private FirefoxProfile getFirefoxProfile() {
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("geo.enabled", false);
        firefoxProfile.setPreference("geo.provider.use_corelocation", false);
        firefoxProfile.setPreference("geo.prompt.testing", false);
        firefoxProfile.setPreference("geo.prompt.testing.allow", false);
        return firefoxProfile;
    }
}
