package com.browserstack.suites.login;

import com.browserstack.NonPageObjectTest;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.browserstack.utils.Constants.ElementLocators.FAVOURITES_BUTTON_ID;
import static com.browserstack.utils.Constants.EndPoints.SIGNED_IN_FAVOURITES;
import static com.browserstack.utils.Constants.EndPoints.SIGN_IN;
import static com.browserstack.utils.Constants.ErrorMessages.CLICK_FAVOURITES;

public class FavouritesRedirectsLoginTest extends NonPageObjectTest {

    private static final long FAVOURITES_LOAD_TIME_OUT = 5;

    private void navigateToFavourite(WebDriver webDriver) {
        WebElement favouritesOption = webDriver.findElement(By.id(FAVOURITES_BUTTON_ID));
        favouritesOption.click();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, FAVOURITES_LOAD_TIME_OUT);
        webDriverWait.until(ExpectedConditions.urlContains(SIGN_IN));
    }

    @Override
    public void run(WebDriver webDriver) {
        navigateToHome(webDriver);
        navigateToFavourite(webDriver);
        Assertions.assertNotEquals(-1, webDriver.getCurrentUrl().indexOf(SIGNED_IN_FAVOURITES), () -> markFail(CLICK_FAVOURITES));
    }
}
