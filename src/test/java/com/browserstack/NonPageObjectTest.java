package com.browserstack;

public abstract class NonPageObjectTest extends ProfiledTest {

//    private String url;
//
//    @BeforeEach
//    @Step("Initialising the test")
//    public void init(TestInfo testInfo) {
//        String profile = testInfo.getTags().iterator().next();
//        String instance = JsonUtil.getProfileInstance(profile);
//        url = JsonUtil.getInstanceURL(instance);
//    }
//
//    @Step("Navigating to the home page")
//    public void navigateToHome(WebDriver webDriver) {
//        webDriver.get(url);
//        ElementLocatorUtil.waitUntilTitleIs(webDriver, this, HOME_PAGE_TITLE, HOME_PAGE_NOT_LOADED_ON_TIME);
//        waitForSpinner(webDriver);
//    }
//
//    @Step("Finding the product count")
//    public int productCount(WebDriver webDriver) {
//        return webDriver.findElements(By.className(PRODUCT_CARD_CSS)).size();
//    }
//
//   @Step("Signing in with username {1} and password {2}")
//    public void signIn(WebDriver webDriver, String userName, String password) {
//        WebElement signInButton = webDriver.findElement(By.id(SIGN_IN_BUTTON_ID));
//        signInButton.click();
//        ElementLocatorUtil.waitUntilElementAppears(webDriver, this, By.id(USER_INPUT_ID), SIGNIN_PAGE_NOT_LOADED_ON_TIME);
//        WebElement userElement = webDriver.findElement(By.id(USER_INPUT_ID));
//        userElement.sendKeys(userName);
//        userElement.sendKeys(Keys.ENTER);
//        WebElement passwordElement = webDriver.findElement(By.id(PASSWORD_INPUT_ID));
//        passwordElement.sendKeys(password);
//        passwordElement.sendKeys(Keys.ENTER);
//        WebElement logInButton = webDriver.findElement(By.id(LOGIN_BUTTON_ID));
//        logInButton.click();
//        ElementLocatorUtil.waitUntilElementAppears(webDriver, this, By.className(USERNAME_LABEL_CLASS), SIGNIN_NOT_COMPLETED_ON_TIME);
//    }
//
//   @Step("Apply mask for sensitive data")
//    public void applyMask(DesiredCapabilities desiredCapabilities) {
//        desiredCapabilities.setCapability(CAPABILITY_MASK_COMMAND, CAPABILITY_VALUE_DEFAULT_MASK);
//    }
//
//   @Step("Logging out")
//    public void logOut(WebDriver webDriver) {
//        if (webDriver.getTitle().equals(HOME_PAGE_TITLE)) {
//            navigateToHome(webDriver);
//            if (webDriver.findElements(By.id(SIGN_OUT_BUTTON_ID)).size() != 0) {
//                webDriver.findElement(By.id(SIGN_OUT_BUTTON_ID)).click();
//            }
//        }
//    }
//
//    @Step("Waiting for the spinner to stop")
//    public void waitForSpinner(WebDriver webDriver) {
//        ElementLocatorUtil.waitUntilElementVanish(webDriver, this, By.xpath(RELOAD_SPINNER_XPATH), SPINNER_NOT_STOPPED_ON_TIME);
//    }

}
