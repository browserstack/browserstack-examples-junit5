package com.browserstack.examples.extensions;

import com.browserstack.examples.config.Platform;
import com.browserstack.examples.config.WebDriverFactory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.util.AnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WebDriverTestExtension implements TestTemplateInvocationContextProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverTestExtension.class);

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        if (!context.getTestMethod().isPresent()) {
            return false;
        }

        Method testMethod = context.getTestMethod().get();
        LOGGER.debug("Supports Test Template on Method :: {}", testMethod.getName());
        return AnnotationUtils.isAnnotated(testMethod, WebDriverTest.class);
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        String testMethodName = context.getRequiredTestMethod().getName();
        final List<TestTemplateInvocationContext> webDriverTestInvocationContexts = new ArrayList<>();
        final WebDriverFactory webDriverFactory = WebDriverFactory.getInstance();
        List<Platform> platforms = webDriverFactory.getPlatforms();
        platforms.forEach( p -> {
            webDriverTestInvocationContexts.add(new WebDriverTestInvocationContext(testMethodName, webDriverFactory, p));
        });

        return webDriverTestInvocationContexts.stream();
    }
}