package com.browserstack.utils.docker;

import java.net.MalformedURLException;
import java.net.URL;

public class DockerUtil {

    public URL getHubURL(){
        try {
            return new URL("http://localhost:4444/wd/hub");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
