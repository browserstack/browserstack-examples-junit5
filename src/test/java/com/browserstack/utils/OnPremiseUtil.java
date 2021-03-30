package com.browserstack.utils;

import com.browserstack.utils.Constants;
import com.browserstack.utils.JsonUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Map;


public class OnPremiseUtil {

    @Step("Initialising the on-premise web driver")
    public static WebDriver getDriver() {
            WebDriver webDriver = null;
            Map<String,String> onPremiseCaps = JsonUtil.getProfileCapabilities(Constants.Profiles.PROFILE_ON_PREMISE);
            String browser = onPremiseCaps.getOrDefault(Constants.Capabilities.CAPABILITY_BROWSER,Constants.Defaults.DEFAULT_ON_PREM_BROWSER);
            switch (browser){
                case Constants.Capabilities.CAPABILITY_VALUE_CHROME: webDriver = new ChromeDriver();
                    break;
                case Constants.Capabilities.CAPABILITY_VALUE_FIREFOX: webDriver = new FirefoxDriver();
                    break;
            }
            return webDriver;
    }

}
