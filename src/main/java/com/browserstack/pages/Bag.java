package com.browserstack.pages;

import com.browserstack.utils.WebDriverWaitUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Bag {

    private static final String CHECKOUT_BUTTON_CLASS = "buy-btn";
    private static final String CHECKOUT_ENDPOINT = "/checkout";

    private final WebDriver webDriver;

    public Bag(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Completing checkout")
    public Checkout checkout() {
        WebElement checkout = webDriver.findElement(By.className(CHECKOUT_BUTTON_CLASS));
        checkout.click();
        WebDriverWaitUtil.getWebDriverWait(webDriver).until(ExpectedConditions.urlContains(CHECKOUT_ENDPOINT));
        return new Checkout(webDriver);
    }
}
