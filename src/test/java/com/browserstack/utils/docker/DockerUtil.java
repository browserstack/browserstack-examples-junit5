package com.browserstack.utils.docker;

import java.net.MalformedURLException;
import java.net.URL;

public class DockerUtil {

    private static final String DOCKER_WD_URL = "http://localhost:4444/wd/hub";

    public URL getHubURL(){
        try {
            return new URL(DOCKER_WD_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
