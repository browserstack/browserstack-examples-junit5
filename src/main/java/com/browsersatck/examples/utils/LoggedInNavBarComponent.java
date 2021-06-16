package com.browsersatck.examples.utils;

import com.browsersatck.examples.pages.Orders;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoggedInNavBarComponent extends NavBarComponent {

    private static final String ORDERS_BUTTON_ID = "orders";
    private static final String PRODUCT_COST_XPATH = "//span[@class='a-size-small a-color-price']";
    private static final String LOG_OUT_BUTTON_ID = "logout";

    private WebDriver webDriver;

    public LoggedInNavBarComponent(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
    }

    public Orders clickOnOrders() {
        webDriver.findElement(By.id(ORDERS_BUTTON_ID)).click();
        WebDriverWaitUtil.getWebDriverWait(webDriver).until(webDriver -> !webDriver.findElements(By.xpath(PRODUCT_COST_XPATH)).isEmpty());
        return new Orders(webDriver);
    }

    public void logOut() {
        webDriver.findElement(By.id(LOG_OUT_BUTTON_ID)).click();
    }

}
