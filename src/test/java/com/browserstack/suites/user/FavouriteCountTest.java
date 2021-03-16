package com.browserstack.suites.user;

import com.browserstack.NonPageObjectTest;
import com.browserstack.utils.Constants;
import com.browserstack.utils.UserCredentialUtil;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.browserstack.utils.Constants.ElementLocators.FAVOURITES_BUTTON_ID;
import static com.browserstack.utils.Constants.ElementLocators.FAVOURITE_BUTTON_CLASS;
import static com.browserstack.utils.Constants.ErrorMessages.FAVOURITES_COUNT;

public class FavouriteCountTest extends NonPageObjectTest {

    private static final String EXISTING_ORDERS_ACCOUNT_USER_NAME = "existing_orders_user";
    private static final long LOGIN_TIME_OUT = 5;

    @Override
    public void preProcess(DesiredCapabilities desiredCapabilities) {
        applyMask(desiredCapabilities);
    }

    public int checkExistingFavourites(WebDriver webDriver) {
        String password = UserCredentialUtil.getPassword(EXISTING_ORDERS_ACCOUNT_USER_NAME);
        signIn(webDriver, EXISTING_ORDERS_ACCOUNT_USER_NAME, password);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, LOGIN_TIME_OUT);
        webDriverWait.until(ExpectedConditions.urlContains(Constants.EndPoints.SIGNED_IN));
        webDriver.findElement(By.className(FAVOURITE_BUTTON_CLASS)).click();
        webDriver.findElement(By.id(FAVOURITES_BUTTON_ID)).click();

        //TODO - NEED WAIT TIME
        webDriverWait.until(ExpectedConditions.urlContains(Constants.EndPoints.FAVOURITES));
        return productCount(webDriver);
    }

    @Override
    public void run(WebDriver webDriver) {
        navigateToHome(webDriver);
        int favourites = checkExistingFavourites(webDriver);
        Assertions.assertNotEquals(favourites, 0, () -> markFail(FAVOURITES_COUNT));
    }

    @Override
    public void postProcess(WebDriver webDriver) {
        logOut(webDriver);
    }
}
