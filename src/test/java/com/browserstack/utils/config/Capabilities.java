package com.browserstack.utils.config;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;
import lombok.ToString;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@ToString
public class Capabilities {

    private Map<String, Object> capabilityMap = new LinkedHashMap<>();

    @JsonAnySetter
    public void setCapabilities(String key, Object value) {
        this.capabilityMap.put(key, value);
    }

    public Object capability(String key) {
        return this.capabilityMap.get(key);
    }

    public DesiredCapabilities convertToDesiredCapabilities(){
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        capabilityMap.forEach(desiredCapabilities::setCapability);
        return desiredCapabilities;
    }
}

