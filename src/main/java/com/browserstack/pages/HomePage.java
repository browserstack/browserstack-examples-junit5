package com.browserstack.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {

    private static final String ADD_ITEM_BUTTON_CLASS = "shelf-item__buy-btn";
    private static final String CART_CLOSE_BUTTON_CLASS = "float-cart__close-btn";

    private final WebDriver webDriver;

    public HomePage(String url, WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
        navigateToHomePage(url,webDriver);
    }

    @Step("Navigating to the home page")
    private void navigateToHomePage(String url, WebDriver webDriver){
        webDriver.get(url);
    }

    @Step("Adding items to the cart")
    public Bag addItemsToCart(int productCount) {
        for (int i = 0; i < productCount; i++) {
            WebElement itemAddButton = webDriver.findElements(By.className(ADD_ITEM_BUTTON_CLASS)).get(i);
            itemAddButton.click();
            if (i != productCount - 1) {
                webDriver.findElement(By.className(CART_CLOSE_BUTTON_CLASS)).click();
            }
        }
        return new Bag(webDriver);
    }

}
