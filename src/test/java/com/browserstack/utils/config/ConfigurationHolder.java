package com.browserstack.utils.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class ConfigurationHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationHolder.class);

    @JsonProperty("application_endpoint")
    private String applicationEndpoint;

    @JsonProperty("driver_type")
    private DriverType driverType;

    @JsonProperty("on_premise_capabilities")
    private GenericCapabilitiesHolder onPremiseCapabilitiesHolder;

    @JsonProperty("on_docker_capabilities")
    private GenericCapabilitiesHolder dockerCapabilitiesHolder;

    @JsonProperty("on_cloud_capabilities")
    private GenericCapabilitiesHolder cloudCapabilitiesHolder;

    @JsonProperty("specific_capabilities")
    private SpecificCapabilitiesHolder specificCapabilitiesHolder;

    public String getApplicationEndpoint() {
        return applicationEndpoint;
    }

    public void setApplicationEndpoint(String applicationEndpoint) {
        this.applicationEndpoint = applicationEndpoint;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    public void setDriverType(DriverType driverType) {
        this.driverType = driverType;
    }

    public GenericCapabilitiesHolder getOnPremiseCapabilitiesHolder() {
        return onPremiseCapabilitiesHolder;
    }

    public void setOnPremiseCapabilitiesHolder(GenericCapabilitiesHolder onPremiseCapabilitiesHolder) {
        this.onPremiseCapabilitiesHolder = onPremiseCapabilitiesHolder;
    }

    public GenericCapabilitiesHolder getDockerCapabilitiesHolder() {
        return dockerCapabilitiesHolder;
    }

    public void setDockerCapabilitiesHolder(GenericCapabilitiesHolder dockerCapabilitiesHolder) {
        this.dockerCapabilitiesHolder = dockerCapabilitiesHolder;
    }

    public GenericCapabilitiesHolder getCloudCapabilitiesHolder() {
        return cloudCapabilitiesHolder;
    }

    public void setCloudCapabilitiesHolder(GenericCapabilitiesHolder cloudCapabilitiesHolder) {
        this.cloudCapabilitiesHolder = cloudCapabilitiesHolder;
    }

    public SpecificCapabilitiesHolder getSpecificCapabilitiesHolder() {
        return specificCapabilitiesHolder;
    }

    public void setSpecificCapabilitiesHolder(SpecificCapabilitiesHolder specificCapabilitiesHolder) {
        this.specificCapabilitiesHolder = specificCapabilitiesHolder;
    }

    public List<PlatformHolder> getActivePlatforms() {
        List<PlatformHolder> activePlatformHolders = Collections.emptyList();
        switch (driverType) {
            case OnPremise:
                activePlatformHolders = onPremiseCapabilitiesHolder.getPlatforms();
                break;
            case OnDocker:
                activePlatformHolders = dockerCapabilitiesHolder.getPlatforms();
                break;
            case OnCloud:
                activePlatformHolders = cloudCapabilitiesHolder.getPlatforms();
                break;
        }
        return activePlatformHolders;
    }
}
