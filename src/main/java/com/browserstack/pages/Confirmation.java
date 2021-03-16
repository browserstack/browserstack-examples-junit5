package com.browserstack.pages;

import com.browserstack.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Confirmation {

    private final WebDriver webDriver;

    private static final String CONFIRMATION_MESSAGE_ID = "confirmation-message";
    private static final String PRODUCT_CLASS = "product";
    private static final String CONTINUE_XPATH = "//button[text()='Continue Shopping »']";
    private static final long CONTINUE_TIME_OUT = 5;
    private static final String HOME_ENDPOINT = "/";

    public Confirmation(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public String getConfirmationMessage() {
        return webDriver.findElement(new By.ById(CONFIRMATION_MESSAGE_ID)).getText();
    }

    public int getProductCount() {
        return webDriver.findElements(new By.ByClassName(PRODUCT_CLASS)).size();
    }

    public void clickOnContinue() {
        webDriver.findElement(new By.ByXPath(CONTINUE_XPATH)).click();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, CONTINUE_TIME_OUT);
        webDriverWait.until(webDriver-> !webDriver.getCurrentUrl().contains(Constants.EndPoints.CONFIRMATION));
    }
}

