package com.browserstack.pages;

import com.browserstack.utils.LoggedInNavBarComponent;
import com.browserstack.utils.LoggedOutNavBarComponent;
import org.openqa.selenium.WebDriver;

public class BasePage {

    private static final String LOGGED_OUT_ERROR_MESSAGE = "You are logged out";
    private static final String SIGNED_IN_ERROR_MESSAGE = "You are signed out";

    private LoggedInNavBarComponent loggedInNavBar;
    private LoggedOutNavBarComponent loggedOutNavBar;

    public BasePage(WebDriver webDriver) {
        loggedInNavBar = new LoggedInNavBarComponent(webDriver);
        loggedOutNavBar = new LoggedOutNavBarComponent(webDriver);
    }

    public LoggedInNavBarComponent getLoggedInNavBar() {
        if (!loggedInNavBar.isLoggedIn()){
            throw new RuntimeException(LOGGED_OUT_ERROR_MESSAGE);
        }
        return loggedInNavBar;
    }

    public LoggedOutNavBarComponent getLoggedOutNavBar() {
        if (loggedInNavBar.isLoggedIn()){
            throw  new RuntimeException(SIGNED_IN_ERROR_MESSAGE);
        }
        return loggedOutNavBar;
    }
}
