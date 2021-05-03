package com.browserstack.utils;

import com.browserstack.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoggedOutNavBarComponent extends NavBarComponent {

    private static final String SIGN_IN_BUTTON_ID = "signin";
    private static final String USER_INPUT_XPATH = "//input[@id='react-select-2-input']";

    private WebDriver webDriver;

    public LoggedOutNavBarComponent(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public HomePage clickOnHome() {
        throw new UnsupportedOperationException("Method is unimplemented.");
    }

    @Override
    public Offers clickOnOffers() {
        throw new UnsupportedOperationException("Method is unimplemented.");
    }

    @Override
    public Orders clickOnOrders() {
        throw new UnsupportedOperationException("Method is unimplemented.");
    }

    @Override
    public Favourites clickOnFavourites() {
        throw new UnsupportedOperationException("Method is unimplemented.");
    }

    @Override
    public Login clickOnSignIn() {
        webDriver.findElement(By.id(SIGN_IN_BUTTON_ID)).click();
        WebDriverWaitUtil.getWebDriverWait(webDriver).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(USER_INPUT_XPATH)));
        return new Login(webDriver);
    }
}
