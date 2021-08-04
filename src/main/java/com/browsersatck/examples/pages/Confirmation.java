package com.browsersatck.examples.pages;

import com.browsersatck.examples.utils.WebDriverWaitUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Confirmation {

    private static final String CONFIRMATION_MESSAGE_ID = "confirmation-message";
    private static final String PRODUCT_CLASS = "product";
    private static final String CONTINUE_XPATH = "//button[text()='Continue Shopping »']";
    private static final String CONFIRMATION_ENDPOINT = "/confirmation";

    private final WebDriver webDriver;

    public Confirmation(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Retrieving confirmation message")
    public String getConfirmationMessage() {
        return webDriver.findElement(By.id(CONFIRMATION_MESSAGE_ID)).getText();
    }

    @Step("Retrieving product count")
    public int getProductCount() {
        return webDriver.findElements(By.className(PRODUCT_CLASS)).size();
    }

    @Step("Clicking on Continue")
    public void clickOnContinue() {
        webDriver.findElement(By.xpath(CONTINUE_XPATH)).click();
        WebDriverWaitUtil.getWebDriverWait(webDriver).until(webDriver -> !webDriver.getCurrentUrl().contains(CONFIRMATION_ENDPOINT));
    }
}