package com.browserstack;

import com.browserstack.utils.*;
import com.browserstack.utils.browserstack.BrowserStackWebDriver;
import com.browserstack.utils.docker.DockerWebDriver;
import com.browserstack.utils.onpremise.OnPremiseWebDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static com.browserstack.utils.Constants.BrowserStackRESTStatus.*;
import static com.browserstack.utils.Constants.Capabilities.*;
import static com.browserstack.utils.Constants.Profiles.*;
import static com.browserstack.utils.Constants.RuntimeOptions.*;

public abstract class AbstractTest {

    private ManagedWebDriver webDriver;

    private static final String TEST_EXCEPTION_MESSAGE = ": Exception encountered";
    private static final String SINGLE_PROFILE_TEST_DATA = "/Single.csv";
    private static final String PARALLEL_PROFILE_TEST_DATA = "/Parallel.csv";
    private static final String LOCAL_PROFILE_TEST_DATA = "/Local.csv";

    public  void preProcess(DesiredCapabilities desiredCapabilities){}
    public abstract void run(WebDriver webDriver);
    public void postProcess(WebDriver webDriver){}

    private void runTest(){
        try{
            run(webDriver);
        }
        catch (Exception e){
            markFail(e+TEST_EXCEPTION_MESSAGE);
        }
    }

    @Test
    @Tag(PROFILE_ON_PREMISE)
    public final void runOnPremise() {
        webDriver = OnPremiseWebDriver.getDriver();
        runTest();
    }

    @Test
    @Tag(DOCKER)
    public final void runOnDocker() {
        webDriver = DockerWebDriver.getDriver();
        runTest();
    }

    private void runOnCloud(DesiredCapabilities desiredCapabilities) {
        fineTuneCloudCapabilities(desiredCapabilities);
        preProcess(desiredCapabilities);
        webDriver = BrowserStackWebDriver.getDriver(desiredCapabilities);
        webDriver.setStatus(STATUS_PASSED);
        webDriver.setReason(TEST_PASSED_MESSAGE);
        runTest();
    }

    @ParameterizedTest()
   @CsvFileSource(resources = SINGLE_PROFILE_TEST_DATA)
   @Tag(PROFILE_SINGLE)
    public final void single(@AggregateWith(CapabilityConverter.class) DesiredCapabilities desiredCapabilities) {
        runOnCloud(desiredCapabilities);
    }

    @ParameterizedTest()
    @CsvFileSource(resources = PARALLEL_PROFILE_TEST_DATA)
    @Tag(PROFILE_PARALLEL)
    public final void parallel(@AggregateWith(CapabilityConverter.class) DesiredCapabilities desiredCapabilities) {
        runOnCloud(desiredCapabilities);
    }

    @ParameterizedTest()
    @CsvFileSource(resources = LOCAL_PROFILE_TEST_DATA)
    @Tag(PROFILE_LOCAL)
    public final void local(@AggregateWith(CapabilityConverter.class) DesiredCapabilities desiredCapabilities) {
        runOnCloud(desiredCapabilities);
    }

    @AfterEach
    public final void close() {
        postProcess(webDriver);
        webDriver.quit();
    }

    @AfterAll
    public static void cleanUp() {
        OnPremiseWebDriver.terminate();
    }

    private void fineTuneCloudCapabilities(DesiredCapabilities desiredCapabilities) {

        String projectProperty = (String) Arrays
                .asList(System.getProperty(PROJECT),desiredCapabilities.getCapability(CAPABILITY_PROJECT))
                .stream()
                .filter(project->project!=null)
                .findFirst()
                .get();

        String buildProperty = Arrays
                .asList(System.getProperty(BUILD),generateDefaultBuildName(desiredCapabilities))
                .stream()
                .filter(project->project!=null)
                .findFirst()
                .get();

        String nameProperty = Arrays
                .asList(System.getProperty(NAME),generateDefaultTestName(desiredCapabilities))
                .stream()
                .filter(project->project!=null)
                .findFirst()
                .get();

        desiredCapabilities.setCapability(CAPABILITY_PROJECT,projectProperty);
        desiredCapabilities.setCapability(CAPABILITY_BUILD,buildProperty);
        desiredCapabilities.setCapability(CAPABILITY_NAME,nameProperty);
    }

    private String generateDefaultTestName(DesiredCapabilities desiredCapabilities){
        String profileName = (String) desiredCapabilities.getCapability(CAPABILITY_NAME);
        String testName = this.getClass().getSimpleName();
        String os = (String) desiredCapabilities.getCapability(CAPABILITY_OS);
        String browser = (String) desiredCapabilities.getCapability(CAPABILITY_BROWSER);
        String device = (String) desiredCapabilities.getCapability(CAPABILITY_DEVICE);
        return String.format("%s %s [%s/%s]",profileName,testName,os,browser!=null?browser:device);
    }

    private String generateDefaultBuildName(DesiredCapabilities desiredCapabilities){
        String name = (String) desiredCapabilities.getCapability(CAPABILITY_BUILD);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM");
        return String.format("%s %s",name,dateFormat.format(Date.from(Instant.now())));
    }

    public String markFail(String reason) {
        webDriver.setStatus(STATUS_FAILED);
        webDriver.setReason(reason);
        return reason;
    }

}
