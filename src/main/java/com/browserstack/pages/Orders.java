package com.browserstack.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Orders extends BasePage{

    private final WebDriver webDriver;

    private static final String ORDERS_ID = "orders";
    private static final String PRODUCT_COST_XPATH = "//span[@class='a-size-small a-color-price']";


    public Orders(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
    }

    public int countOrders() {
        webDriver.findElement(new By.ById(ORDERS_ID)).click();
        return webDriver.findElements(new By.ByXPath(PRODUCT_COST_XPATH)).size();
    }

}
