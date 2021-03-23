package com.browserstack.utils;

public class Constants {

    public static class Instances {
        public static final String APPLICATION_INSTANCE_PRIVATE = "private";
        public static final String APPLICATION_INSTANCE_PUBLIC = "public";
    }

    public static class Profiles {
        public static final String PROFILE_ON_PREMISE = "on-premise";
        public static final String DOCKER = "docker";
        public static final String PROFILE_SINGLE = "single";
        public static final String PROFILE_PARALLEL = "parallel";
        public static final String PROFILE_LOCAL = "local";
        public static final String PROFILE_LOCAL_PARALLEL = "local-parallel";
    }

    public static class EndPoints {
        public static final String SIGN_IN = "/signin";
        public static final String SIGNED_IN = "/?signin=true";
        public static final String SIGNED_IN_FAVOURITES = "/signin?favourites=true";
        public static final String FAVOURITES = "/favourites";
        public static final String OFFERS = "/offers";
    }

    public static class ErrorMessages {

        public static final String HOME_PAGE_NOT_LOADED_ON_TIME = "Home page is taking too long to load.";
        public static final String SPINNER_NOT_STOPPED_ON_TIME = "Spinner is taking too long to stop.";
        public static final String SIGNIN_PAGE_NOT_LOADED_ON_TIME = "Sign In page is taking too long to load.";
        public static final String SIGNIN_NOT_COMPLETED_ON_TIME = "Sign In is taking too long to complete.";
        public static final String API_ERROR_NOT_LOADED_ON_TIME = "Error Message is taking too long to load.";
        public static final String CART_NOT_LOADED_ON_TIME = "Cart is taking too long to load.";
        public static final String FAVOURITES_BUT_NOT_CLICKED_ON_TIME = "Favourites button is taking too long to click.";
        public static final String FAVOURITES_PAGE_NOT_LOADED_ON_TIME = "Favourites page is taking too long to load.";
        public static final String FAVOURITES_ITEMS_NOT_LOADED_ON_TIME = "Favourites items are taking too long to load.";
        public static final String OFFERS_PAGE_NOT_LOADED_ON_TIME = "Offers page is taking too long to load.";
        public static final String OFFER_MESSAGES_NOT_FOUND = "No offer messages found.";
        public static final String NO_OFFERS_LOADED = "No Offers loaded.";
        public static final String CONFIRMATION_FAILED = "Confirmation Failed";
        public static final String PRODUCT_COUNT_MISMATCH = "Product Count Mismatch";
        public static final String PAGE_NOT_LOADED_ON_TIME = "A page is taking too long to load.";
        public static final String PAGE_NOT_RENDERED_CORRECTLY = "Page are not rendered correctly.";


        public static final String ORDER_BY_FILTER = "After applying lowest to highest sort option the cost should be in ascending order.";
        public static final String APPLY_APPLE_SAMSUNG_FILTER = "No change in product count after applying filter.";
        public static final String CLICK_FAVOURITES = "To see favourites, the user should login.";
        public static final String LOCKED_ACCOUNT = "User should not be able to login with locked account.";
        public static final String IMAGE_NOT_LOADING = "Images should not be loaded for this user.";
        public static final String EMPTY_CART = "Cart should not be empty after adding items into it.";
        public static final String FAVOURITES_COUNT = "Favourites should not be empty after marking items favourite.";


        public static final String CHECKOUT_FAILED = "Checkout Failed";

    }

    public static class ElementLocators {
        public static final String HOME_PAGE_TITLE = "StackDemo";
        public static final String RELOAD_SPINNER_XPATH = "//div[@class='spinner lds-ring']";
        public static final String PRODUCT_CARD_CSS = "shelf-item";
        public static final String SIGN_IN_BUTTON_ID = "signin";
        public static final String USERNAME_LABEL_CLASS = "username";
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
        public static final String FAVOURITE_BUTTON_XPATH = "//div[@class='shelf-stopper']";
        public static final String FAVOURITE_BUTTON_CLICKED_XPATH="//button[@class='MuiButtonBase-root MuiIconButton-root Button clicked ']";

        public static final String OFFERS_BUTTON_ID = "offers";
        public static final String SIGN_OUT_BUTTON_ID = "logout";
    }

    public static class BrowserStackRESTStatus {
        public static final String STATUS_PASSED = "passed";
        public static final String STATUS_FAILED = "failed";
        public static final String TEST_PASSED_MESSAGE = "All assertions are passed";
    }

    public static class RuntimeOptions {
        public static final String PROJECT = "project";
        public static final String BUILD = "build";
        public static final String NAME = "name";
    }

    public static class Capabilities {
        public static final String CAPABILITY_MASK_COMMAND = "browserstack.maskCommands";
        public static final String CAPABILITY_PROJECT = "project";
        public static final String CAPABILITY_BUILD = "build";
        public static final String CAPABILITY_NAME = "name";
        public static final String CAPABILITY_OS = "os";
        public static final String CAPABILITY_BROWSER = "browser";
        public static final String CAPABILITY_DEVICE = "device";
        public static final String CAPABILITY_REAL_MOBILE = "realMobile";
        public static final String CAPABILITY_BS_LOCAL = "browserstack.local";
        public static final String CAPABILITY_BS_LOCAL_IDENTIFIER = "browserstack.localIdentifier";
        public static final String CAPABILITY_BS_GPS_LOCATION = "browserstack.gpsLocation";
        public static final String LOCAL_OPTION_KEY = "key";
        public static final String LOCAL_OPTION_LOCAL_IDENTIFIER = "localIdentifier";

        public static final String CAPABILITY_VALUE_CHROME = "Chrome";
        public static final String CAPABILITY_VALUE_FIREFOX = "Firefox";
        public static final String CAPABILITY_VALUE_DEFAULT_MASK = "setValues, getValues, setCookies, getCookies";
        public static final String CAPABILITY_VALUE_GPS_MUMBAI = "20,78";

    }

    public static class Limits {
        public static int ON_PREMISE_PARALLEL_LIMIT = 1;
        public static int BROWSERSTACK_PARALLEL_LIMIT = 5;
    }

    public static class AllureTags{
        public static final String EPIC_END_TO_END = "End To End Test Suite";
        public static final String EPIC_LOGIN= "Login Test Suite";
        public static final String EPIC_OFFERS= "Offers Test Suite";
        public static final String EPIC_PRODUCT= "Product Test Suite";
        public static final String EPIC_USER= "User Test Suite";
        public static final String STORY_END_TO_END = "End To End Test";
        public static final String STORY_FAVOURITES_REDIRECTS_LOGIN = "Favourites Redirects Login Test";
        public static final String STORY_LOCKED_ACCOUNT = "Locked Account Test";
        public static final String STORY_OFFERS = "Offers Test";
        public static final String STORY_APPLY_APPLE_AND_SAMSUNG_FILTER = "Apply Apple And Samsung Filter Test";
        public static final String STORY_ORDER_BY_FILTER = "Order By Filter Test";
        public static final String STORY_FAVOURITE_COUNT="FavouriteCountTest";
        public static final String STORY_IMAGE_LOADING ="ImageLoadingTest";
        public static final String STORY_ORDER_COUNT ="OrderCountTest";

    }
}
