package com.browsersatck.examples.pages;

import com.browsersatck.examples.utils.WebDriverWaitUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Checkout {

    private static final String CONFIRMATION_ENDPOINT = "/confirmation";
    private static final String FIRST_NAME_ID = "firstNameInput";
    private static final String LAST_NAME_ID = "lastNameInput";
    private static final String ADDRESS_LINE1_ID = "addressLine1Input";
    private static final String PROVINCE_ID = "provinceInput";
    private static final String POST_CODE_ID = "postCodeInput";
    private static final String SUBMIT_BUTTON_ID = "checkout-shipping-continue";

    private static final String FIRST_NAME = "Raveendra";
    private static final String LAST_NAME = "Tudangil";
    private static final String ADDRESS_LINE1 = "Mumbai";
    private static final String PROVINCE = "Maharashtra";
    private static final String POST_CODE = "400068";

    private final WebDriver webDriver;

    public Checkout(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Clicking submit to get confirmation")
    public Confirmation clickSubmit() {
        webDriver.findElement(By.id(FIRST_NAME_ID)).sendKeys(FIRST_NAME);
        webDriver.findElement(By.id(LAST_NAME_ID)).sendKeys(LAST_NAME);
        webDriver.findElement(By.id(ADDRESS_LINE1_ID)).sendKeys(ADDRESS_LINE1);
        webDriver.findElement(By.id(PROVINCE_ID)).sendKeys(PROVINCE);
        webDriver.findElement(By.id(POST_CODE_ID)).sendKeys(POST_CODE);
        webDriver.findElement(By.id(SUBMIT_BUTTON_ID)).click();
        WebDriverWaitUtil.getWebDriverWait(webDriver).until(ExpectedConditions.urlContains(CONFIRMATION_ENDPOINT));
        return new Confirmation(webDriver);
    }

}
