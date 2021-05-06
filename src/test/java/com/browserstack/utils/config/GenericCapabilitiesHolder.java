package com.browserstack.utils.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;
import java.util.List;

public class GenericCapabilitiesHolder {

    @JsonProperty("hub_url")
    private String hubUrl;

    private String user;

    @JsonProperty("access_key")
    private String accessKey;

    @JsonProperty("common_capabilities")
    private CommonCapabilitiesHolder commonCapabilitiesHolder;

    private List<PlatformHolder> platforms;

    @JsonProperty("local_options")
    private LocalConfigurationHolder localOptions;

    public String getHubUrl() {
        return hubUrl;
    }

    public void setHubUrl(String hubUrl) {
        this.hubUrl = hubUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public CommonCapabilitiesHolder getCommonCapabilitiesHolder() {
        return commonCapabilitiesHolder;
    }

    public void setCommonCapabilitiesHolder(CommonCapabilitiesHolder commonCapabilitiesHolder) {
        this.commonCapabilitiesHolder = commonCapabilitiesHolder;
    }

    public List<PlatformHolder> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<PlatformHolder> platforms) {
        this.platforms = platforms;
    }

    public LocalConfigurationHolder getLocalOptions() {
        return localOptions;
    }

    public void setLocalOptions(LocalConfigurationHolder localOptions) {
        this.localOptions = localOptions;
    }

    public DesiredCapabilities convertToDesiredCapabilities() {
        DesiredCapabilities cloudCapabilities = new DesiredCapabilities();
        String user = Arrays.asList(System.getenv("BROWSERSTACK_USERNAME"), this.getUser())
                .stream()
                .filter(StringUtils::isNotEmpty)
                .findFirst()
                .get();
        String accessKey = Arrays.asList(System.getenv("BROWSERSTACK_ACCESS_KEY"), this.getAccessKey())
                .stream()
                .filter(StringUtils::isNotEmpty)
                .findFirst()
                .get();
        cloudCapabilities.setCapability("browserstack.user", user);
        cloudCapabilities.setCapability("browserstack.key", accessKey);
        return cloudCapabilities;
    }
}
