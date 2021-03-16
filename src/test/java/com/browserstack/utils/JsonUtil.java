package com.browserstack.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonUtil {

    private static final String INSTANCE_CONFIG_FILE = "src/test/resources/instances.json";
    private static final String INSTANCES_KEY = "instances";
    private static final String INSTANCE_URL_KEY = "url";

    public static String getInstanceURL(String instanceName) {
        String url = "";

        try {
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(INSTANCE_CONFIG_FILE);
            JSONObject file = (JSONObject) parser.parse(fileReader);
            JSONObject instances = (JSONObject) file.get(INSTANCES_KEY);
            JSONObject instance = (JSONObject) instances.get(instanceName);
            url = (String) instance.get(INSTANCE_URL_KEY);
            return url;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return url;
    }
}
