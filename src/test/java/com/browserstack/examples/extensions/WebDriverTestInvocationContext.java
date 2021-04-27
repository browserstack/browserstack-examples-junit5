package com.browserstack.examples.extensions;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import com.browserstack.examples.config.Platform;
import com.browserstack.examples.config.WebDriverFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public class WebDriverTestInvocationContext implements TestTemplateInvocationContext {

    private final String methodName;
    private final Platform platform;
    private final WebDriverFactory webDriverFactory;

    public WebDriverTestInvocationContext(String methodName, WebDriverFactory webDriverFactory, Platform platform) {
        this.methodName = methodName;
        this.webDriverFactory = webDriverFactory;
        this.platform = platform;
    }

    /**
     * Get the display name for this invocation.
     *
     * <p>The supplied {@code invocationIndex} is incremented by the framework
     * with each test invocation. Thus, in the case of multiple active
     * {@linkplain TestTemplateInvocationContextProvider providers}, only the
     * first active provider receives indices starting with {@code 1}.
     *
     * <p>The default implementation returns the supplied {@code invocationIndex}
     * wrapped in brackets &mdash; for example, {@code [1]}, {@code [42]}, etc.
     *
     * @param invocationIndex the index of this invocation (1-based).
     * @return the display name for this invocation; never {@code null} or blank
     */
    @Override
    public String getDisplayName(int invocationIndex) {
        return methodName + "[" + invocationIndex + "]";
    }

    /**
     * Get the additional {@linkplain Extension extensions} for this invocation.
     *
     * <p>The extensions provided by this method will only be used for this
     * invocation of the test template. Thus, it does not make sense to return
     * an extension that acts solely on the container level (e.g.
     * {@link BeforeAllCallback}).
     *
     * <p>The default implementation returns an empty list.
     *
     * @return the additional extensions for this invocation; never {@code null}
     * or containing {@code null} elements, but potentially empty
     */
    @Override
    public List<Extension> getAdditionalExtensions() {
        List<Extension> additionalExtensions = new ArrayList<>();
        additionalExtensions.add(new WebDriverParameterResolver(this.methodName, this.webDriverFactory, this.platform));
        additionalExtensions.add(new WebDriverTestWatcher());
        return additionalExtensions;
    }
}
