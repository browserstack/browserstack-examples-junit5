package com.browserstack.utils;

import com.browserstack.pages.Orders;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoggedInNavBarComponent extends NavBarComponent {

    private WebDriver webDriver;

    private static final String ORDERS_BUTTON_ID = "orders";
    private static final String PRODUCT_COST_XPATH = "//span[@class='a-size-small a-color-price']";
    private static final long CLICK_ORDERS_TIME_OUT = 5;
    private static final String LOG_OUT_BUTTON_ID = "logout";

    public LoggedInNavBarComponent(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
    }

    public Orders clickOnOrders(){
        webDriver.findElement(new By.ById(ORDERS_BUTTON_ID)).click();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver,CLICK_ORDERS_TIME_OUT);
        webDriverWait.until(webDriver->!webDriver.findElements(new By.ByXPath(PRODUCT_COST_XPATH)).isEmpty());
        return new Orders(webDriver);
    }

    public void logOut(){
        webDriver.findElement(new By.ById(LOG_OUT_BUTTON_ID)).click();
    }

}
