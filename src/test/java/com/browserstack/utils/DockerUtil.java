package com.browserstack.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class DockerUtil {

    private static final String DOCKER_WD_HOST = "localhost";
    private static final String DOCKER_WD_PORT = "4444";
    private static final String DOCKER_WD_URL_FORMAT = "http://%s:%s/wd/hub";
    private static final DockerUtil SINGLE_INSTANCE = new DockerUtil();

    private DockerUtil(){}

    private URL getHubURL() {
        try {
            String url = String.format(DOCKER_WD_URL_FORMAT, DOCKER_WD_HOST, DOCKER_WD_PORT);
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Step("Initialising the docker remote web driver")
    public static WebDriver getDriver() {
        Map<String, String> onPremiseCaps = JsonUtil.getProfileCapabilities(Constants.Profiles.PROFILE_DOCKER);
        String browser = onPremiseCaps.getOrDefault(Constants.Capabilities.CAPABILITY_BROWSER, Constants.Defaults.DEFAULT_DOCKER_BROWSER);
        DesiredCapabilities desiredCapabilities = null;
        switch (browser) {
            case Constants.Capabilities.CAPABILITY_VALUE_CHROME:
                desiredCapabilities = new DesiredCapabilities(new ChromeOptions());
                break;
            case Constants.Capabilities.CAPABILITY_VALUE_FIREFOX:
                desiredCapabilities = new DesiredCapabilities(new FirefoxOptions());
                break;
        }
        return new RemoteWebDriver(SINGLE_INSTANCE.getHubURL(), desiredCapabilities);
    }
}
