package com.browserstack.utils.extensions;

import com.browserstack.utils.config.PlatformHolder;
import com.browserstack.utils.config.WebDriverFactory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.commons.util.AnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
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
        List<PlatformHolder> platformHolders = webDriverFactory.getPlatforms();
        CsvSource csvSource = context.getRequiredTestMethod().getAnnotation(CsvSource.class);
        String[] parameters = csvSource!=null?csvSource.value():null;
        WebDriverTest webDriverTest = context.getRequiredTestMethod().getAnnotation(WebDriverTest.class);

        platformHolders.forEach(p -> {
            if (parameters==null){
                for (int i=0; i<webDriverTest.repetitionCount(); i++){
                    webDriverTestInvocationContexts.add(new WebDriverTestInvocationContext(testMethodName, webDriverFactory, p,null));
                }
            }
            else {
                for (String parameter:parameters){
                    String[] parameterList = parameter.split(",");
                    for (int i=0; i<webDriverTest.repetitionCount(); i++) {
                        webDriverTestInvocationContexts.add(new WebDriverTestInvocationContext(testMethodName, webDriverFactory, p, parameterList));
                    }
                }
            }
        });

        return webDriverTestInvocationContexts.stream();
    }
}
