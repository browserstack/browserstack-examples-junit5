package com.browserstack.utils.config;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
@ToString
public class SpecificCapabilitiesHolder {


    private Map<String, List<Capabilities>> specificCapabilitiesMap = new LinkedHashMap<>();

    @JsonAnySetter
    public void setSpecificCapabilities(String key, List<Capabilities> value) {
        this.specificCapabilitiesMap.put(key, value);
    }

    public List<Capabilities> getSpecificCapabilities(String key) {
        return this.specificCapabilitiesMap.get(key);
    }
}
