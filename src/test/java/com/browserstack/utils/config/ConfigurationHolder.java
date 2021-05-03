package com.browserstack.utils.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Attachment;
import lombok.Data;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

@Data
@ToString
public class ConfigurationHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationHolder.class);

    @JsonProperty("application_endpoint")
    private String applicationEndpoint;

    @JsonProperty("default_execution_context")
    private ExecutionContext executionContext;

    @JsonProperty("on_premise_capabilities")
    private OnPremiseCapabilitiesHolder onPremiseCapabilitiesHolder;

    @JsonProperty("on_docker_capabilities")
    private DockerCapabilitiesHolder dockerCapabilitiesHolder;

    @JsonProperty("on_cloud_capabilities")
    private CloudCapabilitiesHolder cloudCapabilitiesHolder;

    @JsonProperty("specific_capabilities")
    private SpecificCapabilitiesHolder specificCapabilitiesHolder;

    @Attachment
    public List<PlatformHolder> getActivePlatforms() {
        List<PlatformHolder> activePlatformHolders = Collections.emptyList();
        switch (executionContext) {
            case OnPremise:
                activePlatformHolders = onPremiseCapabilitiesHolder.getPlatforms();
                break;
            case OnDocker:
                activePlatformHolders = dockerCapabilitiesHolder.getPlatforms();
                break;
            case OnCloud:
                activePlatformHolders = cloudCapabilitiesHolder.getActivePlatforms();
                break;
        }
        return activePlatformHolders;
    }
}
