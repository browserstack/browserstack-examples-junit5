package com.browserstack.examples.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class WebDriverConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverConfiguration.class);

    private String testEndpoint;

    private DriverType driverType;

    @JsonProperty("onPremDriver")
    private OnPremDriverConfig onPremDriverConfig;

    @JsonProperty("cloudDriver")
    private RemoteDriverConfig cloudDriverConfig;

    public List<Platform> getActivePlatforms() {
        List<Platform> activePlatforms = Collections.emptyList();
        switch (driverType) {
            case cloudDriver:
                activePlatforms = cloudDriverConfig.getPlatforms();
                break;
            case onPremDriver:
                activePlatforms = onPremDriverConfig.getPlatforms();
                break;
        }

        return activePlatforms;
    }

    public String getTestEndpoint() {
        return testEndpoint;
    }

    public void setTestEndpoint(String testEndpoint) {
        this.testEndpoint = testEndpoint;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public void setDriverType(DriverType driverType) {
        this.driverType = driverType;
    }

    public OnPremDriverConfig getOnPremDriverConfig() {
        return onPremDriverConfig;
    }

    public void setOnPremDriverConfig(OnPremDriverConfig onPremDriverConfig) {
        this.onPremDriverConfig = onPremDriverConfig;
    }

    public RemoteDriverConfig getCloudDriverConfig() {
        return cloudDriverConfig;
    }

    public void setCloudDriverConfig(RemoteDriverConfig cloudDriverConfig) {
        this.cloudDriverConfig = cloudDriverConfig;
    }
}
