package com.browserstack.pages;

import com.browserstack.utils.UserCredentialUtil;
import com.browserstack.utils.WebDriverWaitUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class Login {

    private static final String USER_INPUT_XPATH = "//input[@id='react-select-2-input']";
    private static final String PASSWORD_INPUT_XPATH = "//*[@id=\"react-select-3-input\"]";
    private static final String LOGIN_BUTTON_ID = "login-btn";
    private static final String USERNAME_LABEL_CLASS = "username";
    private static final String FAV_USER_NAME = "fav_user";

    private final WebDriver webDriver;

    public Login(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Step("Signing in with username {0} and password {1}")
    private void login(String userName, String password) {
        WebElement userElement = webDriver.findElement(By.xpath(USER_INPUT_XPATH));
        userElement.sendKeys(userName);
        userElement.sendKeys(Keys.ENTER);
        WebElement passwordElement = webDriver.findElement(By.xpath(PASSWORD_INPUT_XPATH));
        passwordElement.sendKeys(password);
        passwordElement.sendKeys(Keys.ENTER);
        WebElement logInButton = webDriver.findElement(By.id(LOGIN_BUTTON_ID));
        logInButton.click();
        WebDriverWaitUtil.getWebDriverWait(webDriver).until(ExpectedConditions.visibilityOfElementLocated(By.className(USERNAME_LABEL_CLASS)));

    }

    @Step("Signing in with fav_account credentials")
    public void loginWithFavUser() {
        login(FAV_USER_NAME, UserCredentialUtil.getPassword(FAV_USER_NAME));
    }

}
