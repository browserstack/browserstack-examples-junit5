package com.browserstack.utils;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.openqa.selenium.remote.DesiredCapabilities;

public class CapabilityConverter implements ArgumentsAggregator {

    private static final char SEPARATOR_CHARACTER = ':';

    @Override
    public DesiredCapabilities aggregateArguments(ArgumentsAccessor arguments, ParameterContext parameterContext) throws ArgumentsAggregationException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        for (int i = 0; i < arguments.size(); i++) {
            String capabilityPair = arguments.get(i).toString();
            int separatorIndex = capabilityPair.indexOf(SEPARATOR_CHARACTER);
            desiredCapabilities.setCapability(capabilityPair.substring(0, separatorIndex), capabilityPair.substring(separatorIndex + 1));
        }
        return desiredCapabilities;
    }
}
