package com.browserstack.utils;

import org.openqa.selenium.WebDriver;

public class LoggedOutNavBarComponent extends NavBarComponent {

    private WebDriver webDriver;

    public LoggedOutNavBarComponent(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
    }
}
