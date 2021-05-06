package com.browserstack.utils.config;


import java.util.List;

public class OnPremiseCapabilitiesHolder {

    private List<PlatformHolder> platforms;

    public List<PlatformHolder> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<PlatformHolder> platforms) {
        this.platforms = platforms;
    }
}
