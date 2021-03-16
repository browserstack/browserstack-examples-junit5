package com.browserstack.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.browserstack.utils.Constants.EndPoints.CHECKOUT;

public class Bag {

    private static final String CHECKOUT_BUTTON_CLASS = "buy-btn";
    private static final long CHECKOUT_TIME_OUT = 5;

    private final WebDriver webDriver;

    public Bag(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public Checkout checkout() {
        WebElement checkout = webDriver.findElement(new By.ByClassName(CHECKOUT_BUTTON_CLASS));
        checkout.click();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, CHECKOUT_TIME_OUT);
        webDriverWait.until(ExpectedConditions.urlContains(CHECKOUT));
        return new Checkout(webDriver);
    }
}
