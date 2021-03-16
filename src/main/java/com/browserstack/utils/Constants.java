package com.browserstack.utils;

public class Constants {

 //   public static final String DEVICE_SET_MOBILE = "mobile";
    public static final String APPLICATION_END_POINT = "app";
    public static final String APPLICATION_INSTANCE_PUBLIC = "public";
    public static final String APPLICATION_INSTANCE_PRIVATE = "private";
    public static final String APPLICATION_URL_PUBLIC = "https://bstackdemo.com/";
    public static final String APPLICATION_URL_PRIVATE = "http://localhost:3000/";

    public static class Profiles {
        public static final String PROFILE_ON_PREMISE = "on-premise";
        public static final String DOCKER = "docker";
        public static final String PROFILE_SINGLE = "single";
        public static final String PROFILE_PARALLEL = "parallel";
        public static final String PROFILE_LOCAL = "local";
    }

    public static class Environments {
        public static final String ON_PREMISE = "on-premise";
        public static final String BROWSERSTACK = "browserstack";
        public static final String DOCKER = "docker";
    }

    public static class Defaults {
        public static final String DEFAULT_INSTANCE = "public";
        public static final String DEFAULT_PROFILE = "single";
    }

    public static class EndPoints {
        public static final String SIGN_IN = "/signin";
        public static final String SIGNED_IN = "/?signin=true";
        public static final String SIGNED_IN_FAVOURITES = "/signin?favourites=true";
        public static final String FAVOURITES = "/favourites";
        public static final String OFFERS = "/offers";
        public static final String CHECKOUT = "/checkout";
        public static final String CONFIRMATION = "/confirmation";
    }

    public static class ErrorMessages {
        public static final String ORDER_BY_FILTER = "After applying lowest to highest sort option the cost should be in ascending order";
        public static final String APPLY_APPLE_SAMSUNG_FILTER = "No change in product count after applying filter";
        public static final String CLICK_FAVOURITES = "To see favourites, the user should login";
        public static final String LOCKED_ACCOUNT = "User should not be able to login with locked account";
        public static final String IMAGE_NOT_LOADING = "Images should not be loaded for this user";
        public static final String EMPTY_CART = "Cart should not be empty after adding items into it";
        public static final String FAVOURITES_COUNT = "Favourites should not be empty after marking items favourite";
        public static final String LOCAL_TESTING_ENABLED = "Local Testing enabled";
        public static final String NO_OFFERS_FOUND = "No Offers found";
    }

    public static class ElementLocators {
        public static final String HOME_PAGE_TITLE = "StackDemo";
        public static final String RELOAD_SPINNER_XPATH = "//div[@class='spinner lds-ring']";
        public static final String PRODUCT_CARD_CSS = "shelf-item";
        public static final String SIGN_IN_BUTTON_ID = "signin";
        public static final String USER_INPUT_XPATH = "//input[@id='react-select-2-input']";
        public static final String PASSWORD_INPUT_XPATH = "//*[@id=\"react-select-3-input\"]";
        public static final String LOGIN_BUTTON_ID = "login-btn";
        public static final String ORDER_BY_DROP_DOWN_CSS = "select";
        public static final String LOWEST_TO_HIGHEST_DROP_DOWN_OPTION_BY_XPATH = "//option[text() = 'Lowest to highest']";
        public static final String PRODUCT_PRICE_CARD_CLASS = "shelf-item__price";
        public static final String PRODUCT_PRICE_VALUE_CARD_CLASS = "val";
        public static final String PRODUCT_COST_BOLD_TAG = "b";
        public static final String APPLE_FILTER_XPATH = "//div[@class='filters']//span[text()='Apple']";
        public static final String SAMSUNG_FILTER_XPATH = "//div[@class='filters']//span[text()='Samsung']";
        public static final String FAVOURITES_BUTTON_ID = "favourites";
        public static final String API_ERROR_CLASS = "api-error";
        public static final String PRODUCT_IMAGE_TAG = "img";
        public static final String PRODUCT_IMAGE_SOURCE_ATTRIBUTE = "src";
        public static final String BUY_BUTTON_CLASS = "shelf-item__buy-btn";
        public static final String BAG_QUANTITY_LABEL_CLASS = "bag__quantity";
        public static final String FAVOURITE_BUTTON_CLASS = "shelf-stopper";
        public static final String OFFERS_BUTTON_ID = "offers";
        public static final String SIGN_OUT_BUTTON_ID="logout";
    }

    public static class BrowserStackRESTAPIs{
        public static final String GET_PLAN = "https://api.browserstack.com/automate/plan.json";
        public static final String GET_BROWSERS = "https://api.browserstack.com/automate/browsers.json";

    }

    public static class BrowserStackRESTStatus {
        public static final String STATUS_PASSED = "passed";
        public static final String STATUS_FAILED = "failed";
        public static final String TEST_PASSED_MESSAGE = "All assertions are passed";
    }

    public static class RuntimeOptions{
        public static final String PROFILE = "profile";
        public static final String PROJECT = "project";
        public static final String BUILD = "build";
        public static final String NAME = "name";
    }

    public static class Capabilities{
        public static final String CAPABILITY_PROJECT="project";
        public static final String CAPABILITY_BUILD="build";
        public static final String CAPABILITY_NAME="name";
        public static final String CAPABILITY_OS="os";
        public static final String CAPABILITY_BROWSER="browser";
        public static final String CAPABILITY_DEVICE="device";
        public static final String CAPABILITY_BS_LOCAL="browserstack.local";
        public static final String CAPABILITY_BS_GPS_LOCATION="browserstack.gpsLocation";

        public static final String CAPABILITY_VALUE_CHROME = "Chrome";
        public static final String CAPABILITY_VALUE_FIREFOX = "Firefox";
        public static final String CAPABILITY_VALUE_GPS_MUMBAI = "20,78";
    }
}
