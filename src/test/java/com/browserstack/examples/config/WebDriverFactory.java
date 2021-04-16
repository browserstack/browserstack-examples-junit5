package com.browserstack.examples.config;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public class WebDriverFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverFactory.class);
    private static final String CAPABILITIES_FILE_PROP = "capabilities.config";
    private static final String DEFAULT_CAPABILITIES_FILE = "capabilities.yml";
    private static final String BROWSERSTACK_USERNAME = "BROWSERSTACK_USERNAME";
    private static final String BROWSERSTACK_ACCESS_KEY = "BROWSERSTACK_ACCESS_KEY";
    private static final String BUILD_ID = "BUILD_ID";
    private static final String DEFAULT_BUILD_NAME = "browserstack-examples-junit5";


    private static final WebDriverFactory INSTANCE = new WebDriverFactory();

    private WebDriverConfiguration webDriverConfiguration;
    private final AtomicBoolean isInitialized = new AtomicBoolean(false);
    private final String defaultBuildSuffix = String.valueOf(System.currentTimeMillis());

    public static WebDriverFactory getInstance() throws IOException {
        if (!INSTANCE.isInitialized.get()) {
            INSTANCE.init();
        }
        return INSTANCE;
    }

    public void init() throws IOException {
        isInitialized.set(true);
        String capabilitiesConfigFile = System.getProperty(CAPABILITIES_FILE_PROP, DEFAULT_CAPABILITIES_FILE);
        LOGGER.debug("Using capabilities configuration from FILE :: {}", capabilitiesConfigFile);
        URL resourceURL = WebDriverFactory.class.getClassLoader().getResource(capabilitiesConfigFile);

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        webDriverConfiguration = objectMapper.readValue(resourceURL, WebDriverConfiguration.class);
        List<Platform> platforms = webDriverConfiguration.getActivePlatforms();
        LOGGER.debug("Running tests on {} active platforms.", platforms.size());
    }

    public WebDriver createWebDriverForPlatform(Platform platform, String testName) throws MalformedURLException {
        WebDriver webDriver = null;
        switch (this.webDriverConfiguration.getDriverType()) {
            case localDriver:
                webDriver = createLocalWebDriver(platform);
                break;
            case remoteDriver:
                webDriver = createRemoteWebDriver(platform, testName);
        }
        return webDriver;
    }

    public String getTestEndpoint() {
        return this.webDriverConfiguration.getTestEndpoint();
    }

    private WebDriver createRemoteWebDriver(Platform platform, String testName) throws MalformedURLException {
        RemoteDriverConfig remoteDriverConfig = this.webDriverConfiguration.getRemoteDriverConfig();
        CommonCapabilities commonCapabilities = remoteDriverConfig.getCommonCapabilities();
        DesiredCapabilities platformCapabilities = new DesiredCapabilities();
        if (StringUtils.isNotEmpty(platform.getDevice())) {
            platformCapabilities.setCapability("device", platform.getDevice());
        }
        platformCapabilities.setCapability("browser", platform.getBrowser());
        platformCapabilities.setCapability("browser_version", platform.getBrowserVersion());
        platformCapabilities.setCapability("os", platform.getOs());
        platformCapabilities.setCapability("os_version", platform.getOsVersion());
        platformCapabilities.setCapability("name", testName);
        platformCapabilities.setCapability("project", commonCapabilities.getProject());
        platformCapabilities.setCapability("build", createBuildName(commonCapabilities.getBuildPrefix()));

        if (commonCapabilities.getCapabilities() != null) {
            commonCapabilities.getCapabilities().getCapabilityMap().forEach(platformCapabilities::setCapability);
        }

        if (platform.getCapabilities() != null) {
            platform.getCapabilities().getCapabilityMap().forEach(platformCapabilities::setCapability);
        }
        String user = remoteDriverConfig.getUser();
        if (StringUtils.isNoneEmpty(System.getenv(BROWSERSTACK_USERNAME))) {
            user = System.getenv(BROWSERSTACK_USERNAME);
        }
        String accessKey = remoteDriverConfig.getAccessKey();
        if (StringUtils.isNoneEmpty(System.getenv(BROWSERSTACK_ACCESS_KEY))) {
            accessKey = System.getenv(BROWSERSTACK_ACCESS_KEY);
        }
        platformCapabilities.setCapability("browserstack.user", user);
        platformCapabilities.setCapability("browserstack.key", accessKey);
        return new RemoteWebDriver(new URL(remoteDriverConfig.getHubUrl()), platformCapabilities);
    }

    private WebDriver createLocalWebDriver(Platform platform) {
        return null;
    }

    public List<Platform> getPlatforms() {
        return this.webDriverConfiguration.getActivePlatforms();
    }

    private String createBuildName(String buildPrefix) {
        if (StringUtils.isEmpty(buildPrefix)) {
            buildPrefix = DEFAULT_BUILD_NAME;
        }
        String buildName = buildPrefix;

        String buildSuffix = this.defaultBuildSuffix;
        if (StringUtils.isNotEmpty(System.getenv(BUILD_ID))) {
            buildSuffix = System.getenv(BUILD_ID);
        }
        return buildName + "-" + buildSuffix;
    }



}
