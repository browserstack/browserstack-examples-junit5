package com.browserstack.suites.product;

import com.browserstack.utils.extensions.WebDriverTest;
import com.browserstack.utils.helpers.CommonSteps;
import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.browserstack.utils.helpers.Constants.AllureTags.EPIC_PRODUCT;
import static com.browserstack.utils.helpers.Constants.AllureTags.FEATURE_BRAND;
import static com.browserstack.utils.helpers.Constants.ElementLocators.BRAND_FILTER_XPATH_FORMAT;

@Epic(EPIC_PRODUCT)
@Feature(FEATURE_BRAND)
public class BrandsTest {

    @Step("Applying {0} Filter")
    private void applyBrandFilter(String brand, WebDriver webDriver) {
       String brandXPath = String.format(BRAND_FILTER_XPATH_FORMAT,brand);
        WebElement brandFilter = webDriver.findElement(By.xpath(brandXPath));
        brandFilter.click();
        CommonSteps.waitForSpinner(webDriver);
    }

    @Step("Comparing counts : expected[{0}] and  actual[{1}]")
    private void verifyProductCount(int expectedCount,int actualCount) {
        Assertions.assertEquals(expectedCount,actualCount,"Expected count : "+expectedCount+" but found :"+actualCount);
    }

    @WebDriverTest
    @CsvSource({"Apple,9","Samsung,7"})
    @Description("Testing brand filter component")
    public void brandsFilterTest(String brand, String expectedCount, WebDriver webDriver){
        CommonSteps.navigateToHome(webDriver);
        applyBrandFilter(brand,webDriver);
        int actualCount = CommonSteps.productCount(webDriver);
        verifyProductCount(Integer.parseInt(expectedCount),actualCount);
    }
}
