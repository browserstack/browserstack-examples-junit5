package com.browserstack.examples.helpers;

public class Constants {

    public static class EndPoints {
        public static final String SIGN_IN = "/signin";
        public static final String SIGNED_IN_FAVOURITES = "/signin?favourites=true";
        public static final String SIGNED_IN = "/?signin=true";
        public static final String ORDERS = "/orders";
        public static final String FAVOURITES = "/favourites";
        public static final String OFFERS = "/offers";
    }

    public static class ErrorMessages {
        public static final String HOME_PAGE_NOT_LOADED_ON_TIME = "Home page is taking too long to load.";
        public static final String SPINNER_NOT_STOPPED_ON_TIME = "Spinner is taking too long to stop.";
        public static final String SIGNIN_PAGE_NOT_LOADED_ON_TIME = "Sign In page is taking too long to load.";
        public static final String SIGNIN_NOT_COMPLETED_ON_TIME = "Sign In is taking too long to complete.";
        public static final String ORDER_BY_FILTER = "After applying lowest to highest sort option the cost should be in ascending order.";
        public static final String PRODUCT_COUNT_MISMATCH = "Product Count Mismatch";
        public static final String APPLY_BRAND_FILTER = "No change in product count after applying filter.";
        public static final String CLICK_FAVOURITES = "To see favourites, the user should login.";
        public static final String API_ERROR_NOT_LOADED_ON_TIME = "Error Message is taking too long to load.";
        public static final String LOCKED_ACCOUNT = "User should not be able to login with locked account.";
        public static final String IMAGE_NOT_LOADING = "Images should not be loaded for this user.";
        public static final String EMPTY_CART = "Cart should not be empty after adding items into it.";
        public static final String ORDERS_PAGE_NOT_LOADED_ON_TIME = "Orders page is taking too long to load.";
        public static final String ORDERS_NOT_LOADED_ON_TIME = "Orders are empty or taking too long to load.";
        public static final String FAVOURITES_BUT_NOT_CLICKED_ON_TIME = "Favourites button is taking too long to click.";
        public static final String FAVOURITES_PAGE_NOT_LOADED_ON_TIME = "Favourites page is taking too long to load.";
        public static final String FAVOURITES_ITEMS_NOT_LOADED_ON_TIME = "Favourites items are taking too long to load.";
        public static final String FAVOURITES_COUNT = "Favourites should not be empty after marking items favourite.";
        public static final String OFFERS_PAGE_NOT_LOADED_ON_TIME = "Offers page is taking too long to load.";
        public static final String OFFER_MESSAGES_NOT_FOUND = "No offer messages found.";
        public static final String NO_OFFERS_LOADED = "No Offers loaded.";
        public static final String CONFIRMATION_FAILED = "Confirmation Failed";
    }

    public static class ElementLocators {
        public static final String HOME_PAGE_TITLE = "StackDemo";
        public static final String RELOAD_SPINNER_XPATH = "//div[@class='spinner lds-ring']";
        public static final String PRODUCT_CARD_CSS = "shelf-item";
        public static final String SIGN_IN_BUTTON_ID = "signin";
        public static final String USERNAME_LABEL_CLASS = "username";
        public static final String USER_INPUT_ID = "react-select-2-input";
        public static final String PASSWORD_INPUT_ID = "react-select-3-input";
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
        public static final String ORDERS_BUTTON_ID = "orders";
        public static final String PRODUCT_COST_XPATH = "//span[@class='a-size-small a-color-price']";
        public static final String FAVOURITE_BUTTON_XPATH = "//div[@class='shelf-stopper']";
        public static final String FAVOURITE_BUTTON_CLICKED_XPATH = "//button[@class='MuiButtonBase-root MuiIconButton-root Button clicked ']";
        public static final String OFFERS_BUTTON_ID = "offers";
        public static final String OFFER_CARD_CLASS = "offer";
    }

    public static class AllureTags {
        public static final String EPIC_PRODUCT = "Product Test Suite";
        public static final String EPIC_LOGIN = "Login Test Suite";
        public static final String EPIC_USER = "User Test Suite";
        public static final String EPIC_END_TO_END = "End To End Test Suite";
        public static final String EPIC_OFFERS = "Offers Test Suite";
        public static final String STORY_ORDER_BY_FILTER = "Order By Filter Test";
        public static final String STORY_APPLY_BRAND_FILTER = "Apply Brand Filter Test";
        public static final String STORY_REDIRECTION = "Redirection Test";
        public static final String STORY_ACCOUNT_LOCK = "Account Lock Test";
        public static final String STORY_IMAGE = "Image Test";
        public static final String STORY_ORDER = "Order Test";
        public static final String STORY_FAVOURITES = "Favourites Test";
        public static final String STORY_PURCHASE = "Purchase Test";
        public static final String STORY_REGIONAL_OFFERS = "Regional Offers Test";
    }
}