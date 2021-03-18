package com.browserstack.utils.onpremise;

import com.browserstack.utils.LoggedInNavBarComponent;
import com.browserstack.utils.ManagedWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

import static com.browserstack.utils.Constants.Limits.ON_PREMISE_PARALLEL_LIMIT;

public class OnPremiseWebDriver extends ManagedWebDriver {

    private static WebDriver onPremiseWebDriver;
    private static final Semaphore BINARY_SEMAPHORE = new Semaphore(ON_PREMISE_PARALLEL_LIMIT);
    private static final Semaphore COUNTING_SEMAPHORE = new Semaphore(Integer.MAX_VALUE);
    private static final Logger LOGGER = LoggerFactory.getLogger(OnPremiseWebDriver.class);

    private OnPremiseWebDriver(WebDriver webDriver){
        onPremiseWebDriver = webDriver;
        setWebDriver(webDriver);
    }

    private static class OnPremiseWebDriverHolder {
        private static OnPremiseWebDriver INSTANCE = null;

        public static boolean isInitialised(){
            return INSTANCE!=null;
        }

        public static OnPremiseWebDriver getInstance() {
            if (!isInitialised()){
                WebDriver webDriver = new ChromeDriver();
                INSTANCE = new OnPremiseWebDriver(webDriver);
            }
            return INSTANCE;
        }

    }

    public static OnPremiseWebDriver getDriver() {
        try {
            COUNTING_SEMAPHORE.acquire();
            BINARY_SEMAPHORE.acquire();
            return OnPremiseWebDriverHolder.getInstance();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void terminate() {
        if (OnPremiseWebDriverHolder.isInitialised()&& COUNTING_SEMAPHORE.availablePermits()==Integer.MAX_VALUE) {
            onPremiseWebDriver.quit();
            LOGGER.debug("Closed the on-premise driver");
        }
    }

    @Override
    public void close() {
        quit();
    }

    @Override
    public void quit() {
        LoggedInNavBarComponent loggedInNavBarComponent = new LoggedInNavBarComponent(onPremiseWebDriver);
        if (loggedInNavBarComponent.isLoggedIn()){
            loggedInNavBarComponent.logOut();
        }
        onPremiseWebDriver.manage().deleteAllCookies();
        BINARY_SEMAPHORE.release();
        COUNTING_SEMAPHORE.release();
    }

}
