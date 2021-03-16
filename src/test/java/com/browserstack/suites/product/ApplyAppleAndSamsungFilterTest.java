package com.browserstack.suites.product;

import com.browserstack.NonPageObjectTest;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.browserstack.utils.Constants.ElementLocators.*;
import static com.browserstack.utils.Constants.ErrorMessages.APPLY_APPLE_SAMSUNG_FILTER;

public class ApplyAppleAndSamsungFilterTest extends NonPageObjectTest {

    private static final long APPLE_FILTER_TIME_OUT = 5;
    private static final long SAMSUNG_FILTER_TIME_OUT = 5;

    private void applyAppleSamsungFilter(WebDriver webDriver) {
        WebElement appleFilter = webDriver.findElement(new By.ByXPath(APPLE_FILTER_XPATH));
        appleFilter.click();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, APPLE_FILTER_TIME_OUT);
        webDriverWait.until(waitWebDriver -> waitWebDriver.findElements(By.xpath(RELOAD_SPINNER_XPATH)).isEmpty());

        WebElement samsung = webDriver.findElement(new By.ByXPath(SAMSUNG_FILTER_XPATH));
        samsung.click();
        webDriverWait = new WebDriverWait(webDriver, SAMSUNG_FILTER_TIME_OUT);
        webDriverWait.until(waitWebDriver -> waitWebDriver.findElements(By.xpath(RELOAD_SPINNER_XPATH)).isEmpty());
    }

    @Override
    public void run(WebDriver webDriver) {
        navigateToHome(webDriver);
        int oldCount = productCount(webDriver);
        applyAppleSamsungFilter(webDriver);
        int newCount = productCount(webDriver);
        Assertions.assertTrue(oldCount > newCount, () -> markFail(APPLY_APPLE_SAMSUNG_FILTER));
    }
}
