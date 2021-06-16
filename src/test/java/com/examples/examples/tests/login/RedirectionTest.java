package com.examples.examples.tests.login;

import com.examples.examples.extensions.WebDriverTest;
import com.examples.examples.helpers.ElementLocatorUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.examples.examples.helpers.CommonSteps.navigateToHome;
import static com.examples.examples.helpers.Constants.AllureTags.EPIC_LOGIN;
import static com.examples.examples.helpers.Constants.AllureTags.STORY_REDIRECTION;
import static com.examples.examples.helpers.Constants.ElementLocators.FAVOURITES_BUTTON_ID;
import static com.examples.examples.helpers.Constants.EndPoints.SIGNED_IN_FAVOURITES;
import static com.examples.examples.helpers.Constants.EndPoints.SIGN_IN;
import static com.examples.examples.helpers.Constants.ErrorMessages.CLICK_FAVOURITES;
import static com.examples.examples.helpers.Constants.ErrorMessages.SIGNIN_PAGE_NOT_LOADED_ON_TIME;

@Epic(EPIC_LOGIN)
@Story(STORY_REDIRECTION)
public class RedirectionTest {

    @WebDriverTest(capabilities = {"apply_command_mask"})
    public void favouritesRedirectsLoginTest(WebDriver webDriver) {
        navigateToHome(webDriver);
        clickOnFavourite(webDriver);
        checkRedirect(webDriver);
        Assertions.assertNotEquals(-1, webDriver.getCurrentUrl().indexOf(SIGNED_IN_FAVOURITES), CLICK_FAVOURITES);
    }

    @Step("Clicking favourites to see if it navigates to sign in page")
    private void clickOnFavourite(WebDriver webDriver) {
        WebElement favouritesOption = webDriver.findElement(By.id(FAVOURITES_BUTTON_ID));
        favouritesOption.click();
    }

    @Step("Checking if it redirects to the sign in page")
    private void checkRedirect(WebDriver webDriver) {
        ElementLocatorUtil.waitUntilURLContains(webDriver, SIGN_IN, SIGNIN_PAGE_NOT_LOADED_ON_TIME);
    }

}