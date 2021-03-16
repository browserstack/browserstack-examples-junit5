package com.browserstack.pages;

import com.browserstack.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login {

    private final WebDriver webDriver;

    private static final String userXpath = "//input[@id='react-select-2-input']";
    private static final String passwordXpath = "//input[@id='react-select-3-input']";
    private static final String loginButtonId = "login-btn";


    public Login(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    private void login(String userName, String password) {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 30);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(new By.ByXPath(userXpath)));
        WebElement user = webDriver.findElement(new By.ByXPath(userXpath));
        user.sendKeys(userName);
        user.sendKeys(Keys.ENTER);
        WebElement passwordField = webDriver.findElement(new By.ByXPath(passwordXpath));
        passwordField.sendKeys(password);
        passwordField.sendKeys(Keys.ENTER);
        WebElement logInButton = webDriver.findElement(By.id(loginButtonId));
        logInButton.click();
        webDriverWait.until(ExpectedConditions.urlContains(Constants.EndPoints.SIGNED_IN));
    }

    public void loginWithFavUser() {
        login("fav_user", "testingisfun99");
    }

}
