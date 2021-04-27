package com.browserstack.suites.login;

import static com.browserstack.utils.Constants.AllureTags.EPIC_LOGIN;
import static com.browserstack.utils.Constants.AllureTags.STORY_FAVOURITES_REDIRECTS_LOGIN;


import com.browserstack.NonPageObjectTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;

@Epic(EPIC_LOGIN)
@Story(STORY_FAVOURITES_REDIRECTS_LOGIN)
public class FavouritesRedirectsLoginTest extends NonPageObjectTest {

//    @Override
//    @Step("Running the test")
//    public void run(WebDriver webDriver) {
//        navigateToHome(webDriver);
//        clickOnFavourite(webDriver);
//        checkRedirect(webDriver);
//        Assertions.assertNotEquals(-1, webDriver.getCurrentUrl().indexOf(SIGNED_IN_FAVOURITES), () -> markFail(CLICK_FAVOURITES));
//    }
//
//    @Step("Clicking favourites to see if it navigates to sign in page")
//    private void clickOnFavourite(WebDriver webDriver) {
//        WebElement favouritesOption = webDriver.findElement(By.id(FAVOURITES_BUTTON_ID));
//        favouritesOption.click();
//    }
//
//    @Step("Checking if it redirects to the sign in page")
//    private void checkRedirect(WebDriver webDriver) {
//        ElementLocatorUtil.waitUntilURLContains(webDriver, this, SIGN_IN, SIGNIN_PAGE_NOT_LOADED_ON_TIME);
//    }

}
