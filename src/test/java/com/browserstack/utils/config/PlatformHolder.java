package com.browserstack.utils.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PlatformHolder {

    private String name;

    private String os;

    @JsonProperty("os_version")
    private String osVersion;

    private String browser;

    @JsonProperty("browser_version")
    private String browserVersion;

    private String device;

    @JsonProperty("real_mobile")
    private Boolean realMobile;

    @JsonProperty("driver_path")
    private String driverPath;

    private Capabilities capabilities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Boolean getRealMobile() {
        return realMobile;
    }

    public void setRealMobile(Boolean realMobile) {
        this.realMobile = realMobile;
    }

    public String getDriverPath() {
        return driverPath;
    }

    public void setDriverPath(String driverPath) {
        this.driverPath = driverPath;
    }

    public Capabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
    }

    public DesiredCapabilities convertToDesiredCapabilities() {
        DesiredCapabilities platformCapabilities = new DesiredCapabilities();
        platformCapabilities.setCapability("name", name);
        platformCapabilities.setCapability("os", this.getOs());
        platformCapabilities.setCapability("os_version", this.getOsVersion());
        if (StringUtils.isNotEmpty(this.getDevice())) {
            platformCapabilities.setCapability("device", this.getDevice());
            platformCapabilities.setCapability("realMobile", this.getRealMobile());
        } else {
            platformCapabilities.setCapability("browser", this.getBrowser());
            platformCapabilities.setCapability("browser_version", this.getBrowserVersion());
        }
        if (capabilities != null) {
            platformCapabilities.merge(capabilities.convertToDesiredCapabilities());
        }
        return platformCapabilities;
    }
}
