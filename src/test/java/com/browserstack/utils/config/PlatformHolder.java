package com.browserstack.utils.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

@Data
@ToString
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

    public DesiredCapabilities convertToDesiredCapabilities(){
        DesiredCapabilities platformCapabilities = new DesiredCapabilities();
        platformCapabilities.setCapability("name",name);
        platformCapabilities.setCapability("os", this.getOs());
        platformCapabilities.setCapability("os_version", this.getOsVersion());
        if (StringUtils.isNotEmpty(this.getDevice())) {
            platformCapabilities.setCapability("device", this.getDevice());
            platformCapabilities.setCapability("realMobile",this.getRealMobile());
        }
        else {
            platformCapabilities.setCapability("browser", this.getBrowser());
            platformCapabilities.setCapability("browser_version", this.getBrowserVersion());
        }
        if (capabilities!=null){
            platformCapabilities.merge(capabilities.convertToDesiredCapabilities());
        }
        return platformCapabilities;
    }

}
