package com.browserstack.utils.docker;

import com.browserstack.utils.ManagedWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DockerWebDriver extends ManagedWebDriver {

    private WebDriver dockerWebDriver;

    private DockerWebDriver() {
        DockerUtil dockerUtil = new DockerUtil();
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities(new ChromeOptions());
        dockerWebDriver = new RemoteWebDriver(dockerUtil.getHubURL(), desiredCapabilities);
        setWebDriver(dockerWebDriver);
    }

    public static DockerWebDriver getDriver() {
        return new DockerWebDriver();
    }

}
