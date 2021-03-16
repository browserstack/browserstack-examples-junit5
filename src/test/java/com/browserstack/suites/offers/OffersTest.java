package com.browserstack.suites.offers;

import com.browserstack.NonPageObjectTest;
import com.browserstack.utils.Constants;
import com.browserstack.utils.ManagedWebDriver;
import com.browserstack.utils.UserCredentialUtil;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

import static com.browserstack.utils.Constants.Capabilities.*;
import static com.browserstack.utils.Constants.ElementLocators.OFFERS_BUTTON_ID;
import static com.browserstack.utils.Constants.ErrorMessages.LOCAL_TESTING_ENABLED;
import static com.browserstack.utils.Constants.ErrorMessages.NO_OFFERS_FOUND;


public class OffersTest extends NonPageObjectTest {

    private static final String FAVOURITE_ACCOUNT_USER_NAME = "existing_orders_user";
    private static final long LOGIN_TIME_OUT = 10;
    private static final String OFFER_MESSAGE="We've promotional offers in your location.";
    private DesiredCapabilities desiredCapabilities;

    @Override
    public void preProcess(DesiredCapabilities desiredCapabilities) {
        applyMask(desiredCapabilities);
        boolean isMobile = desiredCapabilities.is(CAPABILITY_DEVICE);
        boolean isLocal = desiredCapabilities.is(CAPABILITY_BS_LOCAL);
        String browser = (String) desiredCapabilities.getCapability(CAPABILITY_BROWSER);

        if (!isLocal){          // Failing/Skipping for local

            if (isMobile){          // For Mobile without local using BS capability
                desiredCapabilities.setCapability(CAPABILITY_BS_GPS_LOCATION, CAPABILITY_VALUE_GPS_MUMBAI);
            }
            else{               // For Desktop without local using Mock location JS
                switch (browser){
                    //Todo : Need to have this logic for various browsers
                    case CAPABILITY_VALUE_CHROME:ChromeOptions options = new ChromeOptions();
                        Map< String, Object > prefs = new HashMap<>();
                        Map < String, Object > profile = new HashMap <> ();
                        Map < String, Object > contentSettings = new HashMap <> ();
                        contentSettings.put("geolocation", 1);
                        profile.put("managed_default_content_settings", contentSettings);
                        prefs.put("profile", profile);
                        options.setExperimentalOption("prefs", prefs);
                        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
                        break;
                    case CAPABILITY_VALUE_FIREFOX:
                        // Not working
                        FirefoxProfile firefoxProfile = new FirefoxProfile();
                        firefoxProfile.setPreference("geo.enabled", false);
                        firefoxProfile.setPreference("geo.provider.use_corelocation", false);
                        firefoxProfile.setPreference("geo.prompt.testing", false);
                        firefoxProfile.setPreference("geo.prompt.testing.allow", false);
                        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                        capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
                }
            }
        }

        this.desiredCapabilities = desiredCapabilities;
    }

    private void mockDesktopGPS(WebDriver webDriver) {
        ManagedWebDriver managedWebDriver = (ManagedWebDriver)webDriver;
        WebDriver rootDriver = managedWebDriver.getWebDriver();
        JavascriptExecutor jse = (JavascriptExecutor) rootDriver;
        jse.executeScript("window.navigator.geolocation.getCurrentPosition=function(success){"+
                    "var position = {\"coords\" : {\"latitude\": \"20\",\"longitude\": \"78\"}};"+
                    "success(position);}");
    }

    public boolean checkIfOffersAvailable(WebDriver webDriver) {
        String password = UserCredentialUtil.getPassword(FAVOURITE_ACCOUNT_USER_NAME);
        signIn(webDriver, FAVOURITE_ACCOUNT_USER_NAME, password);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, LOGIN_TIME_OUT);
        webDriverWait.until(ExpectedConditions.urlContains(Constants.EndPoints.SIGNED_IN));
        webDriver.findElement(By.id(OFFERS_BUTTON_ID)).click();
        webDriverWait.until(ExpectedConditions.urlContains(Constants.EndPoints.OFFERS));
        return webDriver.getPageSource().contains(OFFER_MESSAGE);
    }

    @Override
    public void run(WebDriver webDriver) {
        if (desiredCapabilities==null){
            desiredCapabilities = new DesiredCapabilities();
        }
        boolean isMobile = desiredCapabilities.is(CAPABILITY_DEVICE);
        boolean isLocal = desiredCapabilities.is(CAPABILITY_BS_LOCAL);
        if (isLocal){
            Assertions.fail(markFail(LOCAL_TESTING_ENABLED));
            return;
        }
        if (!isMobile){
            mockDesktopGPS(webDriver);
        }
        navigateToHome(webDriver);
        Assertions.assertTrue(checkIfOffersAvailable(webDriver), () -> markFail(NO_OFFERS_FOUND));
    }

    @Override
    public void postProcess(WebDriver webDriver) {
        logOut(webDriver);
    }


}
