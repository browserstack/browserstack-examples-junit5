package com.browserstack.utils.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CloudProfileCapabilitiesHolder {

    private String name;

    private List<PlatformHolder> platforms;

    @JsonProperty("profile_specific_capabilities")
    private Capabilities profileSpecificCapabilities;

}
