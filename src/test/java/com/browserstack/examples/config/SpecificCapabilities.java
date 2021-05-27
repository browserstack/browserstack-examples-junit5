package com.browserstack.examples.config;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SpecificCapabilities {

    private Map<String, List<Capabilities>> specificCapabilitiesMap = new LinkedHashMap<>();

    public Map<String, List<Capabilities>> getSpecificCapabilitiesMap() {
        return specificCapabilitiesMap;
    }

    public void setSpecificCapabilitiesMap(Map<String, List<Capabilities>> specificCapabilitiesMap) {
        this.specificCapabilitiesMap = specificCapabilitiesMap;
    }

    @JsonAnySetter
    public void setSpecificCapabilities(String key, List<Capabilities> value) {
        this.specificCapabilitiesMap.put(key, value);
    }

    public List<Capabilities> getSpecificCapabilities(String key) {
        return this.specificCapabilitiesMap.get(key);
    }
}