package com.browserstack.utils.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DockerCapabilitiesHolder {

    @JsonProperty("hub_url")
    private String hubUrl;

    private List<PlatformHolder> platforms;
}
