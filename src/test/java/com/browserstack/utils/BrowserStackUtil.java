package com.browserstack.utils;

public class BrowserStackUtil {

//    private static final String BROWSERSTACK_USER_ENV = "BROWSERSTACK_USERNAME";
//    private static final String BROWSERSTACK_ACCESS_KEY_ENV = "BROWSERSTACK_ACCESS_KEY";
//    private static final String BROWSERSTACK_HUB_URL_FORMAT = "https://%s:%s@hub.browserstack.com/wd/hub";
//    private static BrowserStackUtil SINGLE_INSTANCE = new BrowserStackUtil();
//
//    private final String browserstackUserName;
//    private final String browserstackAccessKey;
//
//    private BrowserStackUtil() {
//        this.browserstackUserName = System.getenv(BROWSERSTACK_USER_ENV);
//        this.browserstackAccessKey = System.getenv(BROWSERSTACK_ACCESS_KEY_ENV);
//    }
//
//    private String getBrowserstackAccessKey() {
//        return browserstackAccessKey;
//    }
//
//    private URL getHubURL() {
//        String url = String.format(BROWSERSTACK_HUB_URL_FORMAT, browserstackUserName, browserstackAccessKey);
//        try {
//            return new URL(url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static String getAccessKey(){
//        return SINGLE_INSTANCE.getBrowserstackAccessKey();
//    }
//
//    @Step("Initialising the browserstack remote web driver")
//    public static WebDriver getDriver(DesiredCapabilities desiredCapabilities) {
//        return new RemoteWebDriver(SINGLE_INSTANCE.getHubURL(), desiredCapabilities);
//    }

}
