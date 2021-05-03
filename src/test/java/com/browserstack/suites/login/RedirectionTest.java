package com.browserstack.suites.login;

import com.browserstack.utils.extensions.WebDriverTest;
import com.browserstack.utils.helpers.CommonSteps;
import com.browserstack.utils.helpers.ElementLocatorUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.browserstack.utils.helpers.Constants.AllureTags.*;
import static com.browserstack.utils.helpers.Constants.ElementLocators.FAVOURITES_BUTTON_ID;
import static com.browserstack.utils.helpers.Constants.EndPoints.SIGNED_IN_FAVOURITES;
import static com.browserstack.utils.helpers.Constants.EndPoints.SIGN_IN;
import static com.browserstack.utils.helpers.Constants.ErrorMessages.CLICK_FAVOURITES;
import static com.browserstack.utils.helpers.Constants.ErrorMessages.SIGNIN_PAGE_NOT_LOADED_ON_TIME;

@Epic(EPIC_LOGIN)
@Feature(FEATURE_REDIRECTION)
public class RedirectionTest {

    @Step("Clicking favourites to see if it navigates to sign in page")
    private void clickOnFavourite(WebDriver webDriver) {
        WebElement favouritesOption = webDriver.findElement(By.id(FAVOURITES_BUTTON_ID));
        favouritesOption.click();
    }

    @Step("Checking if it redirects to the sign in page")
    private void checkRedirect(WebDriver webDriver) {
        ElementLocatorUtil.waitUntilURLContains(webDriver, SIGN_IN, SIGNIN_PAGE_NOT_LOADED_ON_TIME);
    }

    @WebDriverTest
    public void favouritesRedirectsLogin(WebDriver webDriver) {
        CommonSteps.navigateToHome(webDriver);;
        clickOnFavourite(webDriver);
        checkRedirect(webDriver);
        Assertions.assertNotEquals(-1, webDriver.getCurrentUrl().indexOf(SIGNED_IN_FAVOURITES), CLICK_FAVOURITES);
    }

}
