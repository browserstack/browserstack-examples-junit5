package com.browserstack.runners;

import com.browserstack.ProfiledTest;
import com.browserstack.utils.DockerUtil;
import org.openqa.selenium.WebDriver;

public class DockerRunner extends ProfiledTestRunner {

    private WebDriver webDriver;
    private ProfiledTest test;

    public DockerRunner(ProfiledTest test) {
        this.test = test;
    }


    @Override
    public void startDriver() {
        webDriver = DockerUtil.getDriver();
    }

    @Override
    public void runTest(){
        runTest(test,webDriver);
    }

    @Override
    public void stopDriver() {
        test.postProcess(webDriver);
        webDriver.quit();
    }
}
