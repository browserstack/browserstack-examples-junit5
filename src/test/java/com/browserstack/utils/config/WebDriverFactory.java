package com.browserstack.utils.config;

import com.browserstack.utils.extensions.WebDriverTest;
import com.browserstack.utils.helpers.AllureUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.browserstack.utils.helpers.Constants.AllureParameters.CAPABILITIES;
import static com.browserstack.utils.helpers.Constants.AllureParameters.CAPABILITIES_FILE;

public class WebDriverFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverFactory.class);
    private static final String CAPABILITIES_FILE_PROP = "capabilities.config";
    private static final String DEFAULT_CAPABILITIES_FILE = "capabilities.yml";
    private static final String DEFAULT_BUILD_NAME = "browserstack-examples-junit5";
    private static final String BROWSER_STACK_TEST_NAME_CAPABILITY="name";
    private static final String BROWSER_STACK_LOCAL_CAPABILITY = "browserstack.local";
    private static final String BROWSER_STACK_LOCAL_IDENTIFIER_CAPABILITY = "browserstack.localIdentifier";
    private static final String BROWSER_STACK_LOCAL_OPTION_KEY = "key";
    private static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
    private static final String WEBDRIVER_EDGE_DRIVER = "webdriver.edge.driver";
    private static final String WEBDRIVER_GECKO_DRIVER = "webdriver.gecko.driver";
    private static final String WEBDRIVER_IE_DRIVER = "webdriver.ie.driver";

    private static WebDriverFactory instance;

    private final String defaultBuildSuffix;
    private final ConfigurationHolder configurationHolder;
    private final boolean isLocal;

    private WebDriverFactory() {
        this.defaultBuildSuffix = String.valueOf(System.currentTimeMillis());
        this.configurationHolder = parseWebDriverConfig();
        this.isLocal = setupLocal();
        List<PlatformHolder> platformHolders = configurationHolder.getActivePlatforms();
        LOGGER.debug("Running tests on {} active platforms.", platformHolders.size());
    }

    public static WebDriverFactory getInstance() {
        if (instance == null) {
            synchronized (WebDriverFactory.class) {
                if (instance == null) {
                    instance = new WebDriverFactory();
                }
            }
        }
        return instance;
    }

    private ConfigurationHolder parseWebDriverConfig() {
        String capabilitiesConfigFile = System.getProperty(CAPABILITIES_FILE_PROP, DEFAULT_CAPABILITIES_FILE);
        AllureUtil.addParameter(CAPABILITIES_FILE,capabilitiesConfigFile);
        LOGGER.debug("Using capabilities configuration from FILE :: {}", capabilitiesConfigFile);
        URL resourceURL = WebDriverFactory.class.getClassLoader().getResource(capabilitiesConfigFile);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        ConfigurationHolder configurationHolder;
        try {
            configurationHolder = objectMapper.readValue(resourceURL, ConfigurationHolder.class);
        } catch (IOException ioe) {

            throw new Error("Unable to parse capabilities file " + capabilitiesConfigFile, ioe);
        }
        return configurationHolder;
    }

    private boolean setupLocal() {
        if (configurationHolder.getDriverType().equals(DriverType.OnCloud)&&configurationHolder.getCloudCapabilitiesHolder().getCommonCapabilitiesHolder().getCapabilities().getCapability(BROWSER_STACK_LOCAL_CAPABILITY) != null && configurationHolder.getCloudCapabilitiesHolder().getCommonCapabilitiesHolder().getCapabilities().getCapability(BROWSER_STACK_LOCAL_CAPABILITY) != "false") {
            LocalConfigurationHolder localConfiguration = configurationHolder.getCloudCapabilitiesHolder().getLocalOptions();
            localConfiguration.getLocalOptions().put(BROWSER_STACK_LOCAL_OPTION_KEY, configurationHolder.getCloudCapabilitiesHolder().getAccessKey());
            LocalFactory.createInstance(localConfiguration.getLocalOptions());
            return true;
        }
        return false;
    }

    public String getTestEndpoint() {
        return this.configurationHolder.getApplicationEndpoint();
    }

    public WebDriver createWebDriverForPlatform(PlatformHolder platformHolder, Method testMethod) throws MalformedURLException {
        WebDriver webDriver = null;
        switch (this.configurationHolder.getDriverType()) {
            case OnPremise:
                webDriver = createOnPremiseWebDriver(platformHolder,testMethod);
                break;
            case OnDocker:
                webDriver = createDockerWebDriver(platformHolder,testMethod);
                break;
            case OnCloud:
                webDriver = createRemoteWebDriver(platformHolder, testMethod);
        }
        return webDriver;
    }

    public List<PlatformHolder> getPlatforms() {
        return this.configurationHolder.getActivePlatforms();
    }

    public DriverType getActiveDriver(){
        return configurationHolder.getDriverType();
    }

    private WebDriver createOnPremiseWebDriver(PlatformHolder platformHolder,Method testMethod) {
        WebDriver webDriver;
        DesiredCapabilities otherCapabilities = new DesiredCapabilities();
        if (platformHolder.getCapabilities() != null) {
            otherCapabilities = platformHolder.getCapabilities().convertToDesiredCapabilities();
        }
        otherCapabilities.merge(getSpecificCapabilities(testMethod));
        switch (BrowserType.valueOf(platformHolder.getName())) {
            case chrome:
                System.setProperty(WEBDRIVER_CHROME_DRIVER, Paths.get(platformHolder.getDriverPath()).toString());
                ChromeOptions chromeOptions = new ChromeOptions()
                        .merge(otherCapabilities);
                webDriver = new ChromeDriver(chromeOptions);
                AllureUtil.addParameter(CAPABILITIES,chromeOptions);
                break;
            case edge:
                System.setProperty(WEBDRIVER_EDGE_DRIVER, Paths.get(platformHolder.getDriverPath()).toString());
                EdgeOptions edgeOptions = new EdgeOptions()
                        .merge(otherCapabilities);
                webDriver = new EdgeDriver(edgeOptions);
                AllureUtil.addParameter(CAPABILITIES,edgeOptions);
                break;
            case firefox:
                System.setProperty(WEBDRIVER_GECKO_DRIVER, Paths.get(platformHolder.getDriverPath()).toString());
                FirefoxOptions firefoxOptions = new FirefoxOptions()
                        .merge(otherCapabilities);
                webDriver = new FirefoxDriver(firefoxOptions);
                AllureUtil.addParameter(CAPABILITIES,firefoxOptions);
                break;
            case ie:
                System.setProperty(WEBDRIVER_IE_DRIVER, Paths.get(platformHolder.getDriverPath()).toString());
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions()
                        .merge(otherCapabilities);
                webDriver = new InternetExplorerDriver(internetExplorerOptions);
                AllureUtil.addParameter(CAPABILITIES,internetExplorerOptions);
                break;
            case opera:
                OperaOptions operaOptions = new OperaOptions()
                        .merge(otherCapabilities);
                webDriver = new OperaDriver(operaOptions);
                AllureUtil.addParameter(CAPABILITIES,operaOptions);
                break;
            case safari:
                SafariOptions safariOptions = new SafariOptions()
                        .merge(otherCapabilities);
                webDriver = new SafariDriver(safariOptions);
                AllureUtil.addParameter(CAPABILITIES,safariOptions);
                break;
            default:
                throw new RuntimeException("Unknown platform name : " + platformHolder.getName());
        }
        return webDriver;
    }

    private WebDriver createDockerWebDriver(PlatformHolder platformHolder,Method testMethod) throws MalformedURLException {
        GenericCapabilitiesHolder dockerCapabilitiesHolder = configurationHolder.getDockerCapabilitiesHolder();
        WebDriver webDriver;
        DesiredCapabilities otherCapabilities = new DesiredCapabilities();
        if (platformHolder.getCapabilities() != null) {
            otherCapabilities = platformHolder.getCapabilities().convertToDesiredCapabilities();
        }
        otherCapabilities.merge(getSpecificCapabilities(testMethod));
        switch (BrowserType.valueOf(platformHolder.getName())) {
            case chrome:
                ChromeOptions chromeOptions = new ChromeOptions()
                        .merge(otherCapabilities);
                webDriver = new RemoteWebDriver(new URL(dockerCapabilitiesHolder.getHubUrl()), chromeOptions);
                AllureUtil.addParameter(CAPABILITIES,chromeOptions);
                break;
            case edge:
                EdgeOptions edgeOptions = new EdgeOptions()
                        .merge(otherCapabilities);
                webDriver = new RemoteWebDriver(new URL(dockerCapabilitiesHolder.getHubUrl()), edgeOptions);
                AllureUtil.addParameter(CAPABILITIES,edgeOptions);
                break;
            case firefox:
                FirefoxOptions firefoxOptions = new FirefoxOptions()
                        .merge(otherCapabilities);
                webDriver = new RemoteWebDriver(new URL(dockerCapabilitiesHolder.getHubUrl()), firefoxOptions);
                AllureUtil.addParameter(CAPABILITIES,firefoxOptions);
                break;
            case ie:
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions()
                        .merge(otherCapabilities);
                webDriver = new RemoteWebDriver(new URL(dockerCapabilitiesHolder.getHubUrl()), internetExplorerOptions);
                AllureUtil.addParameter(CAPABILITIES,internetExplorerOptions);
                break;
            case opera:
                OperaOptions operaOptions = new OperaOptions()
                        .merge(otherCapabilities);
                webDriver = new RemoteWebDriver(new URL(dockerCapabilitiesHolder.getHubUrl()), operaOptions);
                AllureUtil.addParameter(CAPABILITIES,operaOptions);
                break;
            case safari:
                SafariOptions safariOptions = new SafariOptions()
                        .merge(otherCapabilities);
                webDriver = new RemoteWebDriver(new URL(dockerCapabilitiesHolder.getHubUrl()), safariOptions);
                AllureUtil.addParameter(CAPABILITIES,safariOptions);
                break;
            default:
                throw new RuntimeException("Unknown platform name : " + platformHolder.getName());
        }
        return webDriver;
    }

    private WebDriver createRemoteWebDriver(PlatformHolder platformHolder, Method testMethod) throws MalformedURLException {
        GenericCapabilitiesHolder cloudCapabilitiesHolder = this.configurationHolder.getCloudCapabilitiesHolder();
        CommonCapabilitiesHolder commonCapabilitiesHolder = cloudCapabilitiesHolder.getCommonCapabilitiesHolder();
        DesiredCapabilities cloudCapabilities = cloudCapabilitiesHolder.convertToDesiredCapabilities();
        DesiredCapabilities commonCapabilities = commonCapabilitiesHolder.convertToDesiredCapabilities(DEFAULT_BUILD_NAME, defaultBuildSuffix);
        DesiredCapabilities platformCapabilities = platformHolder.convertToDesiredCapabilities();
        platformCapabilities.setCapability(BROWSER_STACK_TEST_NAME_CAPABILITY, org.apache.commons.lang3.StringUtils.capitalize(String.format("%s [%s]", testMethod.getName(), platformCapabilities.getCapability(BROWSER_STACK_TEST_NAME_CAPABILITY))));
        DesiredCapabilities specificCapabilities = getSpecificCapabilities(testMethod);
        DesiredCapabilities mergedCapabilities = new DesiredCapabilities()
                .merge(commonCapabilities)
                .merge(platformCapabilities)
                .merge(cloudCapabilities)
                .merge(specificCapabilities);
        if (isLocal) {
            mergedCapabilities.setCapability(BROWSER_STACK_LOCAL_IDENTIFIER_CAPABILITY, LocalFactory.getInstance().getLocalIdentifier());
        }

        AllureUtil.addParameter(CAPABILITIES, mergedCapabilities);
        return new RemoteWebDriver(new URL(cloudCapabilitiesHolder.getHubUrl()), mergedCapabilities);
    }

    private DesiredCapabilities getSpecificCapabilities(Method testMethod){
        DesiredCapabilities specificCapabilities = new DesiredCapabilities();
        WebDriverTest webDriverTest = testMethod.getAnnotation(WebDriverTest.class);
        if (webDriverTest != null) {
            String[] specificCapabilitiesKeys = webDriverTest.specificCapabilities();
            Arrays.asList(specificCapabilitiesKeys)
                    .stream()
                    .forEach(key -> configurationHolder
                            .getSpecificCapabilitiesHolder()
                            .getSpecificCapabilitiesMap()
                            .get(key)
                            .forEach(caps -> specificCapabilities
                                    .merge(caps.convertToDesiredCapabilities())));
        }
        return specificCapabilities;
    }
}