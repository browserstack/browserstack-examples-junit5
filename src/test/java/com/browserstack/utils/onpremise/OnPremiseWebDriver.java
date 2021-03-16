package com.browserstack.utils.onpremise;

import com.browserstack.utils.LoggedInNavBarComponent;
import com.browserstack.utils.ManagedWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.Semaphore;

public class OnPremiseWebDriver extends ManagedWebDriver {

    private static WebDriver onPremiseWebDriver;
    private static final Semaphore BINARY_SEMAPHORE = new Semaphore(1);
    private static final Semaphore COUNTING_SEMAPHORE = new Semaphore(Integer.MAX_VALUE);

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
