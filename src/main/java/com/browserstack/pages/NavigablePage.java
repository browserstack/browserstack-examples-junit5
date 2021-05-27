package com.browserstack.pages;

import com.browserstack.utils.LoggedOutNavBarComponent;
import com.browserstack.utils.NavBarComponent;
import org.openqa.selenium.WebDriver;

public abstract class NavigablePage {

    private NavBarComponent navBarComponent;

    public NavigablePage(WebDriver webDriver) {
        this.navBarComponent = new LoggedOutNavBarComponent(webDriver);
    }

    public NavigablePage(NavBarComponent navBarComponent) {
        this.navBarComponent = navBarComponent;
    }

    public NavBarComponent getNavBarComponent() {
        return navBarComponent;
    }

    public void setNavBarComponent(NavBarComponent navBarComponent) {
        this.navBarComponent = navBarComponent;
    }
}