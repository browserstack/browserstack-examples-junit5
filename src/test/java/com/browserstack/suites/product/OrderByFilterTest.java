package com.browserstack.suites.product;

import com.browserstack.NonPageObjectTest;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.browserstack.utils.Constants.ElementLocators.*;
import static com.browserstack.utils.Constants.ErrorMessages.ORDER_BY_FILTER;

public class OrderByFilterTest extends NonPageObjectTest {

    private static final long LOWEST_HIGHEST_REFRESH_TIME_OUT = 5;

    private void orderByLowestToHighest(WebDriver webDriver) {
        WebElement orderByDropDown = webDriver.findElement(By.cssSelector(ORDER_BY_DROP_DOWN_CSS));
        WebElement lowestToHighestOption = orderByDropDown.findElement(By.xpath(LOWEST_TO_HIGHEST_DROP_DOWN_OPTION_BY_XPATH));
        lowestToHighestOption.click();
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, LOWEST_HIGHEST_REFRESH_TIME_OUT);
        webDriverWait.until(waitWebDriver -> waitWebDriver.findElements(By.xpath(RELOAD_SPINNER_XPATH)).isEmpty());
    }

    private WebElement findProductByIndex(WebDriver webDriver, int index) {
        return webDriver.findElements(By.className(PRODUCT_CARD_CSS)).get(index);
    }

    private int findCostByIndex(WebDriver webDriver, int index) {
        WebElement product = findProductByIndex(webDriver, index);
        String cost = product
                .findElement(By.className(PRODUCT_PRICE_CARD_CLASS))
                .findElement(By.className(PRODUCT_PRICE_VALUE_CARD_CLASS))
                .findElement(By.tagName(PRODUCT_COST_BOLD_TAG)).getText();
        return Integer.parseInt(cost);
    }

    @Override
    public void run(WebDriver webDriver) {
        navigateToHome(webDriver);
        orderByLowestToHighest(webDriver);
        int productCount = productCount(webDriver);
        int lastCost = findCostByIndex(webDriver, 0), currentCost;
        for (int i = 1; i < productCount; i++) {
            currentCost = findCostByIndex(webDriver, i);
            Assertions.assertTrue(currentCost >= lastCost, () -> markFail(ORDER_BY_FILTER));
            lastCost = currentCost;
        }
    }

}