package com.browserstack.suites.product;

import com.browserstack.utils.extensions.WebDriverTest;
import com.browserstack.utils.helpers.CommonSteps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.browserstack.utils.helpers.Constants.AllureTags.EPIC_PRODUCT;
import static com.browserstack.utils.helpers.Constants.AllureTags.FEATURE_PRODUCT_ORDERING;
import static com.browserstack.utils.helpers.Constants.ElementLocators.*;
import static com.browserstack.utils.helpers.Constants.ErrorMessages.ORDER_BY_FILTER;

@Epic(EPIC_PRODUCT)
@Feature(FEATURE_PRODUCT_ORDERING)
public class ProductOrderingTest {

    @Step("Applying Order by Lowest to Highest Filter")
    private void orderByLowestToHighest(WebDriver webDriver) {
        WebElement orderByDropDown = webDriver.findElement(By.cssSelector(ORDER_BY_DROP_DOWN_CSS));
        WebElement lowestToHighestOption = orderByDropDown.findElement(By.xpath(LOWEST_TO_HIGHEST_DROP_DOWN_OPTION_BY_XPATH));
        lowestToHighestOption.click();
        CommonSteps.waitForSpinner(webDriver);
    }

    @Step("Finding cost of product at index {1}")
    private int findCostByIndex(WebDriver webDriver, int index) {
        WebElement product = findProductByIndex(webDriver, index);
        String cost = product
                .findElement(By.className(PRODUCT_PRICE_CARD_CLASS))
                .findElement(By.className(PRODUCT_PRICE_VALUE_CARD_CLASS))
                .findElement(By.tagName(PRODUCT_COST_BOLD_TAG)).getText();
        return Integer.parseInt(cost);
    }

    @Step("Finding product at index {1}")
    private WebElement findProductByIndex(WebDriver webDriver, int index) {
        return webDriver.findElements(By.className(PRODUCT_CARD_CSS)).get(index);
    }

    @Step("Verifying current cost [{0}] >= previous cost [{1}]")
    private void checkCostOrder(int currentCost, int previousCost) {
        Assertions.assertTrue(currentCost >= previousCost, ORDER_BY_FILTER);
    }

    @WebDriverTest
    @Description("Testing order by price component")
    public void OrderByPriceTest(WebDriver webDriver) {
        CommonSteps.navigateToHome(webDriver);
        orderByLowestToHighest(webDriver);
        int productCount = CommonSteps.productCount(webDriver);
        int previousCost = findCostByIndex(webDriver, 0), currentCost;
        for (int i = 1; i < productCount; i++) {
            currentCost = findCostByIndex(webDriver, i);
            checkCostOrder(currentCost, previousCost);
            previousCost = currentCost;
        }
    }
}
