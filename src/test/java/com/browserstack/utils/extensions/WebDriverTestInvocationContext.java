package com.browserstack.utils.extensions;

import com.browserstack.utils.config.PlatformHolder;
import com.browserstack.utils.config.WebDriverFactory;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.util.ArrayList;
import java.util.List;

public class WebDriverTestInvocationContext implements TestTemplateInvocationContext {

    private final String methodName;
    private final PlatformHolder platformHolder;
    private final WebDriverFactory webDriverFactory;
    private final String[] parameters;

    public WebDriverTestInvocationContext(String methodName, WebDriverFactory webDriverFactory, PlatformHolder platformHolder,String[] parameters) {
        this.methodName = methodName;
        this.webDriverFactory = webDriverFactory;
        this.platformHolder = platformHolder;
        this.parameters = parameters;
    }

    @Override
    public String getDisplayName(int invocationIndex) {
        return methodName + "[" + invocationIndex + "]";
    }

    @Override
    public List<Extension> getAdditionalExtensions() {
        List<Extension> additionalExtensions = new ArrayList<>();
        additionalExtensions.add(new WebDriverParameterResolver(this.webDriverFactory, this.platformHolder));
        if (parameters!=null){
            for (int i=0; i<parameters.length; i++){
                additionalExtensions.add(new CSVParameterResolver(i,parameters[i]));
            }
        }
        additionalExtensions.add(new WebDriverTestWatcher());
        return additionalExtensions;
    }
}
