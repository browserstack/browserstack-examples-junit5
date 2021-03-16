package com.browserstack.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage{

    private static final String ADD_ITEM_BUTTON_CLASS = "shelf-item__buy-btn";
    private static final String CHECKOUT_BUTTON_CLASS="buy-btn";
    private final WebDriver webDriver;

    public HomePage(String url, WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
        webDriver.get(url);
    }

    public Bag addItemsToCart(int productCount){
        for (int i=0; i<productCount; i++){
            WebElement itemAddButton = webDriver.findElements(By.className(ADD_ITEM_BUTTON_CLASS)).get(i);
            itemAddButton.click();
        }
        WebElement checkout = webDriver.findElement(new By.ByClassName(CHECKOUT_BUTTON_CLASS));
        checkout.click();
        return new Bag(webDriver);
    }

}
