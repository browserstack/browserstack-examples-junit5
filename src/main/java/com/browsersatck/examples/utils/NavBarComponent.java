package com.browsersatck.examples.utils;

import com.browsersatck.examples.pages.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class NavBarComponent {

    private static final String SIGN_IN_BUTTON_ID = "signin";
    private static final String SIGN_OUT_BUTTON_ID = "logout";
    private static final String USER_INPUT_XPATH = "//input[@id='react-select-2-input']";
    private final WebDriver webDriver;

    public NavBarComponent(WebDriver webDriver) {
        this.webDriver = webDriver;
    }


    public boolean isLoggedIn() {
        return !webDriver.findElements(By.id(SIGN_OUT_BUTTON_ID)).isEmpty();
    }

    public Login clickSignIn() {
        webDriver.findElement(By.id(SIGN_IN_BUTTON_ID)).click();
        WebDriverWaitUtil.getWebDriverWait(webDriver).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(USER_INPUT_XPATH)));
        return new Login(webDriver);
    }

}
