package com.browserstack.pages;

import com.browserstack.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Checkout {

    private final WebDriver webDriver;

    private static final String FIRST_NAME_ID = "firstNameInput";
    private static final String LAST_NAME_ID = "lastNameInput";
    private static final String ADDRESS_LINE1_ID = "addressLine1Input";
    private static final String PROVINCE_ID = "provinceInput";
    private static final String POST_CODE_ID = "postCodeInput";
    private static final String SUBMIT_BUTTON_ID = "checkout-shipping-continue";

    private static final String FIRST_NAME ="Raveendra";
    private static final String LAST_NAME ="Raveendra";
    private static final String ADDRESS_LINE1 ="Mumbai";
    private static final String PROVINCE ="Maharashtra";
    private static final String POST_CODE ="400068";

    public Checkout(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public Confirmation clickSubmit() {
        webDriver.findElement(new By.ById(FIRST_NAME_ID)).sendKeys(FIRST_NAME);
        webDriver.findElement(new By.ById(LAST_NAME_ID)).sendKeys(LAST_NAME);
        webDriver.findElement(new By.ById(ADDRESS_LINE1_ID)).sendKeys(ADDRESS_LINE1);
        webDriver.findElement(new By.ById(PROVINCE_ID)).sendKeys(PROVINCE);
        webDriver.findElement(new By.ById(POST_CODE_ID)).sendKeys(POST_CODE);
        webDriver.findElement(new By.ById(SUBMIT_BUTTON_ID)).click();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
        webDriverWait.until(ExpectedConditions.urlContains(Constants.EndPoints.CONFIRMATION));
        return new Confirmation(webDriver);
    }

}
