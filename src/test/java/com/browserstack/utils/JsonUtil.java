package com.browserstack.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

    private static final String TEST_CAPABILITY_FILE = "src/test/resources/test_caps.json";
    private static final String INSTANCES_KEY = "instances";
    private static final String INSTANCE_URL_KEY = "url";
    private static final String COMMON_CAPS_KEY = "common-capabilities";
    private static final String PROFILE_CAPS_KEY = "profile-capabilities";
    private static final String CAPABILITIES_KEY = "capabilities";
    private static final String LOCAL_OPTIONS_KEY = "local-options";

    public static String getInstanceURL(String instanceName) {
        String url = "";
        try {
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(TEST_CAPABILITY_FILE);
            JSONObject file = (JSONObject) parser.parse(fileReader);
            JSONObject instancesJson = (JSONObject) file.get(INSTANCES_KEY);
            JSONObject instanceJson = (JSONObject) instancesJson.get(instanceName);
            url = (String) instanceJson.get(INSTANCE_URL_KEY);
            return url;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static Map<String,String>  getCommonCapabilities() {
        Map<String,String> commonCaps = new HashMap<>();
        try {
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(TEST_CAPABILITY_FILE);
            JSONObject file = (JSONObject) parser.parse(fileReader);
            commonCaps = (Map<String, String>) file.get(COMMON_CAPS_KEY);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return commonCaps;
    }

    public static Map<String,String>  getProfileCapabilities(String profile) {
        Map<String,String> profileCaps = new HashMap<>();
        try {
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(TEST_CAPABILITY_FILE);
            JSONObject file = (JSONObject) parser.parse(fileReader);
            JSONObject profilesJson = (JSONObject)file.get(PROFILE_CAPS_KEY);
            JSONObject profileJson =  (JSONObject)profilesJson.get(profile);
            profileCaps = (Map<String, String>) profileJson.get(CAPABILITIES_KEY);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return profileCaps;
    }

    public static Map<String,String> getLocalOptions(){
        Map<String,String> localOptionsMap = new HashMap<>();
        try {
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(TEST_CAPABILITY_FILE);
            JSONObject file = (JSONObject) parser.parse(fileReader);
            localOptionsMap = (Map<String, String>) file.get(LOCAL_OPTIONS_KEY);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return localOptionsMap;
    }
}
