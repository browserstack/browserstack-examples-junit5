package com.browserstack.utils.config;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class OnPremiseCapabilitiesHolder {

    private List<PlatformHolder> platforms;

}
