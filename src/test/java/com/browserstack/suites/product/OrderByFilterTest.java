package com.browserstack.suites.product;

import static com.browserstack.utils.Constants.AllureTags.EPIC_PRODUCT;
import static com.browserstack.utils.Constants.AllureTags.STORY_ORDER_BY_FILTER;


import com.browserstack.NonPageObjectTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;

@Epic(EPIC_PRODUCT)
@Story(STORY_ORDER_BY_FILTER)
public class OrderByFilterTest extends NonPageObjectTest {

//    @Override
//    @Step("Running the test")
//    public void run(WebDriver webDriver) {
//        navigateToHome(webDriver);
//        orderByLowestToHighest(webDriver);
//        int productCount = productCount(webDriver);
//        int lastCost = findCostByIndex(webDriver, 0), currentCost;
//        for (int i = 1; i < productCount; i++) {
//            currentCost = findCostByIndex(webDriver, i);
//            checkingCostOrder(currentCost,lastCost);
//            lastCost = currentCost;
//        }
//    }
//
//    @Step("Applying Order by Lowest to Highest Filter")
//    private void orderByLowestToHighest(WebDriver webDriver) {
//        WebElement orderByDropDown = webDriver.findElement(By.cssSelector(ORDER_BY_DROP_DOWN_CSS));
//        WebElement lowestToHighestOption = orderByDropDown.findElement(By.xpath(LOWEST_TO_HIGHEST_DROP_DOWN_OPTION_BY_XPATH));
//        lowestToHighestOption.click();
//        waitForSpinner(webDriver);
//    }
//
//    @Step("Finding product at index {1}")
//    private WebElement findProductByIndex(WebDriver webDriver, int index) {
//        return webDriver.findElements(By.className(PRODUCT_CARD_CSS)).get(index);
//    }
//
//    @Step("Finding cost of product at index {1}")
//    private int findCostByIndex(WebDriver webDriver, int index) {
//        WebElement product = findProductByIndex(webDriver, index);
//        String cost = product
//                .findElement(By.className(PRODUCT_PRICE_CARD_CLASS))
//                .findElement(By.className(PRODUCT_PRICE_VALUE_CARD_CLASS))
//                .findElement(By.tagName(PRODUCT_COST_BOLD_TAG)).getText();
//        return Integer.parseInt(cost);
//    }
//
//    @Step("Verifying current cost [{0}] >= previous cost [{1}]")
//    private void checkingCostOrder(int currentCost, int lastCost){
//        Assertions.assertTrue(currentCost >= lastCost, () -> markFail(ORDER_BY_FILTER));
//    }

}
