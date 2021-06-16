package com.examples.examples.extensions;

import com.examples.examples.config.Platform;
import com.examples.examples.config.WebDriverFactory;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

import java.util.ArrayList;
import java.util.List;

public class WebDriverTestInvocationContext implements TestTemplateInvocationContext {

    private final String methodName;
    private final Platform platform;
    private final WebDriverFactory webDriverFactory;

    public WebDriverTestInvocationContext(String methodName, WebDriverFactory webDriverFactory, Platform platform) {
        this.methodName = methodName;
        this.webDriverFactory = webDriverFactory;
        this.platform = platform;
    }

    @Override
    public String getDisplayName(int invocationIndex) {
        return methodName + "[" + invocationIndex + "]";
    }

    @Override
    public List<Extension> getAdditionalExtensions() {
        List<Extension> additionalExtensions = new ArrayList<>();
        additionalExtensions.add(new WebDriverParameterResolver(this.webDriverFactory, this.platform));
        additionalExtensions.add(new WebDriverTestWatcher());
        return additionalExtensions;
    }
}