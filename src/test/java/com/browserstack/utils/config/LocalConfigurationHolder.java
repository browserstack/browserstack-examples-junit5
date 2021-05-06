package com.browserstack.utils.config;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.LinkedHashMap;
import java.util.Map;

public class LocalConfigurationHolder {

    private Map<String, String> localOptions = new LinkedHashMap<>();

    public Map<String, String> getLocalOptions() {
        return localOptions;
    }

    public void setLocalOptions(Map<String, String> localOptions) {
        this.localOptions = localOptions;
    }

    @JsonAnySetter
    public void setOption(String key, String value) {
        this.localOptions.put(key, value);
    }

    public String getOption(String key) {
        return this.localOptions.get(key);
    }
}
