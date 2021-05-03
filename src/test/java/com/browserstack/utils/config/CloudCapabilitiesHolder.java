package com.browserstack.utils.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;
import java.util.List;

@Data
@ToString
public class CloudCapabilitiesHolder {

    @JsonProperty("hub_url")
    private String hubUrl;

    private String user;

    @JsonProperty("access_key")
    private String accessKey;

    @JsonProperty("common_capabilities")
    private CommonCapabilitiesHolder commonCapabilitiesHolder;

    @JsonProperty("default_profile")
    private String profile;

    @JsonProperty("profile_capabilities")
    private List<CloudProfileCapabilitiesHolder> profileCapabilities;

    @JsonProperty("local_options")
    private LocalConfigurationHolder localOptions;

    public DesiredCapabilities convertToDesiredCapabilities(){
        DesiredCapabilities cloudCapabilities = new DesiredCapabilities();
        String user=  Arrays.asList(System.getenv("BROWSERSTACK_USERNAME"),this.getUser())
                .stream()
                .filter(StringUtils::isNotEmpty)
                .findFirst()
                .get();
        String accessKey=  Arrays.asList(System.getenv("BROWSERSTACK_ACCESS_KEY"),this.getAccessKey())
                .stream()
                .filter(StringUtils::isNotEmpty)
                .findFirst()
                .get();
        cloudCapabilities.setCapability("browserstack.user", user);
        cloudCapabilities.setCapability("browserstack.key", accessKey);
        return cloudCapabilities;
    }

    public CloudProfileCapabilitiesHolder getActiveCloudProfile(){
        return profileCapabilities.stream()
                .filter(profileCapabilities->profileCapabilities.getName().equals(profile))
                .findFirst()
                .get();
    }

    public List<PlatformHolder> getActivePlatforms() {
        return getActiveCloudProfile()
                .getPlatforms();
    }
}
