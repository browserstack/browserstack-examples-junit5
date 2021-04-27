package com.browserstack.suites.product;

import static com.browserstack.utils.Constants.AllureTags.EPIC_PRODUCT;
import static com.browserstack.utils.Constants.AllureTags.STORY_APPLY_APPLE_AND_SAMSUNG_FILTER;


import com.browserstack.NonPageObjectTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;

@Epic(EPIC_PRODUCT)
@Story(STORY_APPLY_APPLE_AND_SAMSUNG_FILTER)
public class ApplyAppleAndSamsungFilterTest extends NonPageObjectTest {

//    private static final String BRAND_APPLE = "apple";
//    private static final String BRAND_SAMSUNG = "samsung";
//
//    @Override
//    @Step("Running the test")
//    public void run(WebDriver webDriver) {
//        navigateToHome(webDriver);
//        int oldCount = productCount(webDriver);
//        applyAppleSamsungFilter(webDriver);
//        int newCount = productCount(webDriver);
//        verifyCount(oldCount,newCount);
//    }
//
//    @Step("Applying Apple and Samsung Filter")
//    private void applyAppleSamsungFilter(WebDriver webDriver) {
//        WebElement appleFilter = webDriver.findElement(By.xpath(APPLE_FILTER_XPATH));
//        appleFilter.click();
//        waitForSpinner(webDriver);
//        WebElement samsung = webDriver.findElement(By.xpath(SAMSUNG_FILTER_XPATH));
//        samsung.click();
//        waitForSpinner(webDriver);
//    }
//
//    @Step("Verifying the filter reduced the product count before filter {0} and after filter{1}")
//    private void verifyCount(int oldCount, int newCount) {
//        int expectedOldCount = ProductUtil.getProducts().size();
//        int expectedNewCount = ProductUtil.getProductsByBrands(Arrays.asList(BRAND_APPLE,BRAND_SAMSUNG)).size();
//        Assertions.assertEquals(expectedOldCount,oldCount,()->markFail(PRODUCT_COUNT_MISMATCH));
//        Assertions.assertEquals(expectedNewCount,newCount,()->markFail(PRODUCT_COUNT_MISMATCH));
//        Assertions.assertTrue(oldCount > newCount, () -> markFail(APPLY_APPLE_SAMSUNG_FILTER));
//    }
}
