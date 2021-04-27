package com.browserstack.utils;

public class JsonUtil {

//    private static final String TEST_CAPABILITY_FILE = "src/test/resources/test_caps.json";
//    private static final String INSTANCES_KEY = "instances";
//    private static final String INSTANCE_KEY = "instance";
//    private static final String DRIVERS_KEY = "drivers";
//    private static final String INSTANCE_URL_KEY = "url";
//    private static final String COMMON_CAPS_KEY = "common-capabilities";
//    private static final String PROFILE_CAPS_KEY = "profile-capabilities";
//    private static final String CAPABILITIES_KEY = "capabilities";
//    private static final String LOCAL_OPTIONS_KEY = "local-options";
//
//
//    public static JSONObject getTestCaps(){
//        JSONParser parser = new JSONParser();
//        JSONObject file = null;
//        try {
//            FileReader fileReader = new FileReader(TEST_CAPABILITY_FILE);
//            file = (JSONObject) parser.parse(fileReader);
//        } catch (IOException|ParseException e) {
//            e.printStackTrace();
//        }
//        return file;
//    }
//
//    public static JSONObject getInstances(){
//        return (JSONObject) getTestCaps().get(INSTANCES_KEY);
//    }
//
//    public static JSONObject getInstance(String instanceName){
//        return (JSONObject) getInstances().get(instanceName);
//    }
//
//    public static String getInstanceURL(String instanceName){
//        return (String) getInstance(instanceName).get(INSTANCE_URL_KEY);
//    }
//
//    public static Map<String, String> getCommonCapabilities(){
//        return (Map<String, String>) getTestCaps().get(COMMON_CAPS_KEY);
//    }
//
//    public static JSONObject getProfiles(){
//        return (JSONObject) getTestCaps().get(PROFILE_CAPS_KEY);
//    }
//
//    public static JSONObject getProfile(String profileName){
//        return (JSONObject) getProfiles().get(profileName);
//    }
//
//    public static Map<String,String> getProfileCapabilities(String profileName){
//        return (Map<String, String>) getProfile(profileName).get(CAPABILITIES_KEY);
//    }
//
//    public static String getProfileInstance(String profileName){
//        return (String) getProfile(profileName).get(INSTANCE_KEY);
//    }
//
//    public static Map<String,String> getOnPremDrivers(){
//        return (Map<String, String>) getProfile(PROFILE_ON_PREMISE).get(DRIVERS_KEY);
//    }
//
//    public static Map<String, String> getLocalOptions(){
//        return (Map<String, String>) getTestCaps().get(LOCAL_OPTIONS_KEY);
//    }
}
