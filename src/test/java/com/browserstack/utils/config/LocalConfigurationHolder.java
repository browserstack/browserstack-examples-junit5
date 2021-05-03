package com.browserstack.utils.config;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@ToString
public class LocalConfigurationHolder {

    private Map<String, String> localOptions = new LinkedHashMap<>();

    @JsonAnySetter
    public void setOption(String key, String value) {
        this.localOptions.put(key, value);
    }

    public String getOption(String key) {
        return this.localOptions.get(key);
    }
}
