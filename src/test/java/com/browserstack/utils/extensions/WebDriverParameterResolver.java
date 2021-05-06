package com.browserstack.utils.extensions;

import com.browserstack.utils.config.PlatformHolder;
import com.browserstack.utils.config.WebDriverFactory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

public class WebDriverParameterResolver implements ParameterResolver {

    public static final ExtensionContext.Namespace STORE_NAMESPACE =
            ExtensionContext.Namespace.create("com.browserstack.examples");

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverParameterResolver.class);

    private final WebDriverFactory webDriverFactory;
    private final PlatformHolder platformHolder;


    public WebDriverParameterResolver(WebDriverFactory webDriverFactory, PlatformHolder platformHolder) {
        this.webDriverFactory = webDriverFactory;
        this.platformHolder = platformHolder;
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(WebDriver.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext,
                                   ExtensionContext extensionContext) throws ParameterResolutionException {
        Method testMethod = extensionContext.getRequiredTestMethod();
        WebDriver webDriver = createWebDriver(testMethod);
        if (webDriver == null) {
            throw new ParameterResolutionException("Unable to create WebDriver for Platform :: "
                    + this.platformHolder.getName() + " method :: " + testMethod.getName());
        }
        extensionContext.getStore(STORE_NAMESPACE).put(extensionContext.getDisplayName(), webDriver);
        return webDriver;
    }

    private WebDriver createWebDriver(Method testMethod) {
        WebDriver webDriver;
        try {
            webDriver = this.webDriverFactory.createWebDriverForPlatform(platformHolder, testMethod);
        } catch (MalformedURLException malformedURLException) {
            LOGGER.error("Caught exception when creating WebDriver for Platform :: {}", platformHolder, malformedURLException);
            throw new ParameterResolutionException(platformHolder.toString(), malformedURLException);
        }
        return webDriver;
    }
}
