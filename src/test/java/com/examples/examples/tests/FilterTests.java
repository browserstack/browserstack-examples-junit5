package com.examples.examples.tests;

import com.examples.examples.config.WebDriverFactory;
import com.examples.examples.extensions.WebDriverTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public class FilterTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterTests.class);


    @WebDriverTest
    public void testSelectingAppleFilterDisplaysNoSamsungDevices(WebDriver webDriver) throws Exception {
        /* =================== Prepare ================= */
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        webDriver.get(WebDriverFactory.getInstance().getTestEndpoint());

        /* =================== Execute ================= */
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .cssSelector(".filters-available-size:nth-child(2) .checkmark"))).click();
        wait.until(waitWebDriver -> waitWebDriver.findElements(By.cssSelector(".spinner")).isEmpty());
        List<WebElement> appleDeviceName = webDriver.findElements(By.cssSelector(".shelf-item__title"));

        /* =================== Verify ================= */
        long samsungDeviceCount = appleDeviceName.stream().filter(e -> e.getText().contains("Galaxy")).count();
        assertEquals(0, samsungDeviceCount);
    }

    @WebDriverTest
    public void testSelectingSamsungFilterDisplaysNoAppleDevices(WebDriver webDriver) throws Exception {
        /* =================== Prepare ================= */
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        webDriver.get(WebDriverFactory.getInstance().getTestEndpoint());

        /* =================== Execute ================= */
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By
                        .cssSelector(".filters-available-size:nth-child(3) .checkmark"))).click();
        wait.until(waitWebDriver -> waitWebDriver.findElements(By.cssSelector(".spinner")).isEmpty());
        List<WebElement> samsungDeviceName = webDriver.findElements(By.cssSelector(".shelf-item__title"));

        /* =================== Verify ================= */
        long appleDeviceCount = samsungDeviceName.stream().filter(e -> e.getText().contains("iPhone")).count();
        assertEquals(0, appleDeviceCount);
    }

}
