package com.browserstack.utils.docker;

import java.net.MalformedURLException;
import java.net.URL;

public class DockerUtil {

    private static final String DOCKER_WD_HOST = "localhost";
    private static final String DOCKER_WD_PORT = "4444";
    private static final String DOCKER_WD_URL_FORMAT = "http://%s:%s/wd/hub";

    public URL getHubURL() {
        try {
            String url = String.format(DOCKER_WD_URL_FORMAT, DOCKER_WD_HOST, DOCKER_WD_PORT);
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
