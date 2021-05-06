package com.browserstack.utils.config;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;

public class CommonCapabilitiesHolder {

    private String project;

    private String buildPrefix;

    private Capabilities capabilities;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getBuildPrefix() {
        return buildPrefix;
    }

    public void setBuildPrefix(String buildPrefix) {
        this.buildPrefix = buildPrefix;
    }

    public Capabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
    }

    public DesiredCapabilities convertToDesiredCapabilities(String defaultBuildPrefix, String defaultBuildSuffix) {
        DesiredCapabilities commonCapabilities = new DesiredCapabilities();
        commonCapabilities.setCapability("project", this.getProject());
        commonCapabilities.setCapability("build", createBuildName(defaultBuildPrefix, defaultBuildSuffix));
        if (this.getCapabilities() != null) {
            this.getCapabilities().getCapabilityMap().forEach(commonCapabilities::setCapability);
        }
        return commonCapabilities;
    }

    private String createBuildName(String defaultBuildPrefix, String defaultBuildSuffix) {
        String buildPrefix = Arrays.asList(this.buildPrefix, defaultBuildPrefix)
                .stream()
                .filter(StringUtils::isNotEmpty)
                .findFirst()
                .get();
        String buildSuffix = Arrays.asList(System.getenv("BUILD_ID"), defaultBuildSuffix)
                .stream()
                .filter(StringUtils::isNotEmpty)
                .findFirst()
                .get();
        return buildPrefix + "-" + buildSuffix;
    }
}