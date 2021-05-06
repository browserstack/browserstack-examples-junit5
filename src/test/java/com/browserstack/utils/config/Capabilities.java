package com.browserstack.utils.config;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.LinkedHashMap;
import java.util.Map;

public class Capabilities {

    private Map<String, Object> capabilityMap = new LinkedHashMap<>();

    public Map<String, Object> getCapabilityMap() {
        return capabilityMap;
    }

    @JsonAnySetter
    public void setCapability(String key, Object value) {
        this.capabilityMap.put(key, value);
    }

    public Object getCapability(String key) {
        return this.capabilityMap.get(key);
    }

    public DesiredCapabilities convertToDesiredCapabilities() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        capabilityMap.forEach(desiredCapabilities::setCapability);
        return desiredCapabilities;
    }

    @Override
    public String toString() {
        return "Capabilities{" +
                "capabilityMap=" + capabilityMap +
                '}';
    }
}