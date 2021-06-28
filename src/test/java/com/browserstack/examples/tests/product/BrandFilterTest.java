package com.browserstack.examples.tests.product;

import com.browserstack.examples.extensions.WebDriverTest;
import com.browserstack.examples.helpers.ProductUtil;
import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collections;

import static com.browserstack.examples.helpers.CommonSteps.*;
import static com.browserstack.examples.helpers.Constants.AllureTags.FEATURE_PRODUCT;
import static com.browserstack.examples.helpers.Constants.AllureTags.STORY_APPLY_BRAND_FILTER;
import static com.browserstack.examples.helpers.Constants.ElementLocators.APPLE_FILTER_XPATH;
import static com.browserstack.examples.helpers.Constants.ElementLocators.SAMSUNG_FILTER_XPATH;
import static com.browserstack.examples.helpers.Constants.ErrorMessages.APPLY_BRAND_FILTER;
import static com.browserstack.examples.helpers.Constants.ErrorMessages.PRODUCT_COUNT_MISMATCH;

@Feature(FEATURE_PRODUCT)
@Story(STORY_APPLY_BRAND_FILTER)
public class BrandFilterTest {

    private static final String BRAND_APPLE = "apple";
    private static final String BRAND_SAMSUNG = "samsung";

    @Severity(SeverityLevel.NORMAL)
    @WebDriverTest
    public void appleFilterTest(WebDriver webDriver) {
        navigateToHome(webDriver);
        int totalCount = productCount(webDriver);
        applyBrandFilter(APPLE_FILTER_XPATH, webDriver);
        int brandCount = productCount(webDriver);
        verifyCount(BRAND_APPLE, totalCount, brandCount);
    }

    @WebDriverTest
    public void samsungFilterTest(WebDriver webDriver) {
        navigateToHome(webDriver);
        int totalCount = productCount(webDriver);
        applyBrandFilter(SAMSUNG_FILTER_XPATH, webDriver);
        int brandCount = productCount(webDriver);
        verifyCount(BRAND_SAMSUNG, totalCount, brandCount);
    }

    @Step("Applying brand filter")
    private void applyBrandFilter(String brandXPath, WebDriver webDriver) {
        WebElement brandFilter = webDriver.findElement(By.xpath(brandXPath));
        brandFilter.click();
        waitForSpinner(webDriver);
    }

    @Step("Verifying the product counts for brand : {0}")
    private void verifyCount(String brand, int totalCount, int brandCount) {
        int expectedTotalCount = ProductUtil.getAllProducts().size();
        int expectedBrandCount = ProductUtil.getProductsByBrands(Collections.singletonList(brand)).size();
        Assertions.assertEquals(expectedTotalCount, totalCount, PRODUCT_COUNT_MISMATCH);
        Assertions.assertEquals(expectedBrandCount, brandCount, PRODUCT_COUNT_MISMATCH);
        Assertions.assertTrue(totalCount > brandCount, APPLY_BRAND_FILTER);
    }
}