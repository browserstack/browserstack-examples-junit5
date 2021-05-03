package com.browserstack.utils;

import com.browserstack.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoggedInNavBarComponent extends NavBarComponent {

    private static final String ORDERS_BUTTON_ID = "orders";
    private static final String PRODUCT_COST_XPATH = "//span[@class='a-size-small a-color-price']";

    private WebDriver webDriver;

    public LoggedInNavBarComponent(WebDriver webDriver) {
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
        webDriver.findElement(By.id(ORDERS_BUTTON_ID)).click();
        WebDriverWaitUtil.getWebDriverWait(webDriver).until(webDriver -> !webDriver.findElements(By.xpath(PRODUCT_COST_XPATH)).isEmpty());
        return new Orders(webDriver);
    }

    @Override
    public Favourites clickOnFavourites() {
        throw new UnsupportedOperationException("Method is unimplemented.");
    }

    @Override
    public Login clickOnSignIn() {
        throw new UnsupportedOperationException("Method is unimplemented.");
    }
}
