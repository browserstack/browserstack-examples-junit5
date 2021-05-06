package com.browserstack.utils.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DockerCapabilitiesHolder {

    @JsonProperty("hub_url")
    private String hubUrl;

    private List<PlatformHolder> platforms;

    public String getHubUrl() {
        return hubUrl;
    }

    public void setHubUrl(String hubUrl) {
        this.hubUrl = hubUrl;
    }

    public List<PlatformHolder> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<PlatformHolder> platforms) {
        this.platforms = platforms;
    }
}
