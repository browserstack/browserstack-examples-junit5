package com.browserstack.utils;

import com.browserstack.pages.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class NavBarComponent {

    private final WebDriver webDriver;

    private static final String OFFERS_BUTTON_ID = "offers";
    private static final String SIGN_IN_BUTTON_ID = "signin";
    private static final String SIGN_OUT_BUTTON_ID="logout";

    public NavBarComponent(WebDriver webDriver) {
        this.webDriver = webDriver;
    }


    public boolean isLoggedIn(){
        return !webDriver.findElements(new By.ById(SIGN_OUT_BUTTON_ID)).isEmpty();
    }

    public Login clickSignIn() {
        webDriver.findElement(new By.ById(SIGN_IN_BUTTON_ID)).click();
        return new Login(webDriver);
    }

    public Login clickOffers(){
        webDriver.findElement(new By.ById(OFFERS_BUTTON_ID)).click();
        return new Login(webDriver);
    }

}
