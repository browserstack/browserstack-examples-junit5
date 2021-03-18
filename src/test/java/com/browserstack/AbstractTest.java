package com.browserstack;

import com.browserstack.utils.CapabilityConverter;
import com.browserstack.utils.JsonUtil;
import com.browserstack.utils.ManagedWebDriver;
import com.browserstack.utils.browserstack.BrowserStackWebDriver;
import com.browserstack.utils.docker.DockerWebDriver;
import com.browserstack.utils.onpremise.OnPremiseWebDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static com.browserstack.utils.Constants.BrowserStackRESTStatus.*;
import static com.browserstack.utils.Constants.Capabilities.*;
import static com.browserstack.utils.Constants.Profiles.*;
import static com.browserstack.utils.Constants.RuntimeOptions.*;

public abstract class AbstractTest {

    private ManagedWebDriver webDriver;

    private static final String TEST_EXCEPTION_MESSAGE = ": Exception encountered";
    private static final String SINGLE_PROFILE_TEST_DATA = "/single.csv";
    private static final String PARALLEL_PROFILE_TEST_DATA = "/parallel.csv";
    private static final String LOCAL_PROFILE_TEST_DATA = "/single.csv";
    private static final String LOCAL_PARALLEL_PROFILE_TEST_DATA = "/parallel.csv";
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);

    public  void preProcess(DesiredCapabilities desiredCapabilities){}
    public abstract void run(WebDriver webDriver);
    public void postProcess(WebDriver webDriver){}

    private void runTest(){
        try{
            LOGGER.debug("Test started");
            run(webDriver);
        }
        catch (Exception e){
            LOGGER.error("Exception encountered",e);
            markFail(e+TEST_EXCEPTION_MESSAGE);
            throw e;
        }
    }

    @Test
    @Tag(PROFILE_ON_PREMISE)
    public final void runOnPremise() {
        LOGGER.debug("Initialising on premise web driver");
        webDriver = OnPremiseWebDriver.getDriver();
        runTest();
    }

    @Test
    @Tag(DOCKER)
    public final void runOnDocker() {
        LOGGER.debug("Initialising on docker web driver");
        webDriver = DockerWebDriver.getDriver();
        runTest();
    }

   @ParameterizedTest()
   @CsvFileSource(resources = SINGLE_PROFILE_TEST_DATA)
   @Tag(PROFILE_SINGLE)
    public final void single(@AggregateWith(CapabilityConverter.class) DesiredCapabilities desiredCapabilities) {
        LOGGER.debug(String.format("Initialising Single Test[%s] with",this.getClass().getSimpleName()),desiredCapabilities);
        runOnCloud(PROFILE_SINGLE,desiredCapabilities);
        LOGGER.debug(String.format("Completed Single Test[%s] with",this.getClass().getSimpleName()),desiredCapabilities);
    }

    @ParameterizedTest()
    @CsvFileSource(resources = PARALLEL_PROFILE_TEST_DATA)
    @Tag(PROFILE_PARALLEL)
    public final void parallel(@AggregateWith(CapabilityConverter.class) DesiredCapabilities desiredCapabilities) {
        LOGGER.debug(String.format("Initialising Parallel Test[%s] with",this.getClass().getSimpleName()),desiredCapabilities);
        runOnCloud(PROFILE_PARALLEL,desiredCapabilities);
        LOGGER.debug(String.format("Completed Parallel Test[%s] with",this.getClass().getSimpleName()),desiredCapabilities);
    }

    @ParameterizedTest()
    @CsvFileSource(resources = LOCAL_PROFILE_TEST_DATA)
    @Tag(PROFILE_LOCAL)
    public final void local(@AggregateWith(CapabilityConverter.class) DesiredCapabilities desiredCapabilities) {
        LOGGER.debug(String.format("Initialising Local Test[%s] with",this.getClass().getSimpleName()),desiredCapabilities);
        runOnCloud(PROFILE_LOCAL,desiredCapabilities);
        LOGGER.debug(String.format("Completed Local Test[%s] with",this.getClass().getSimpleName()),desiredCapabilities);
    }

    @ParameterizedTest()
    @CsvFileSource(resources = LOCAL_PARALLEL_PROFILE_TEST_DATA)
    @Tag(PROFILE_LOCAL_PARALLEL)
    public final void localParallel(@AggregateWith(CapabilityConverter.class) DesiredCapabilities desiredCapabilities) {
        LOGGER.debug(String.format("Initialising Local Parallel Test[%s] with",this.getClass().getSimpleName()),desiredCapabilities);
        runOnCloud(PROFILE_LOCAL_PARALLEL,desiredCapabilities);
        LOGGER.debug(String.format("Completed Local Parallel Test[%s] with",this.getClass().getSimpleName()),desiredCapabilities);
    }

    @AfterEach
    public final void close() {
        postProcess(webDriver);
        webDriver.quit();
        LOGGER.debug("Closed the webdriver");
    }

    @AfterAll
    public static void cleanUp() {
        OnPremiseWebDriver.terminate();
    }

    public String markFail(String reason) {
        webDriver.setStatus(STATUS_FAILED);
        webDriver.setReason(reason);
        LOGGER.error(String.format("%s has failed[reason : %s]",this.getClass().getSimpleName(),reason));
        return reason;
    }

    private void runOnCloud(String profile,DesiredCapabilities desiredCapabilities) {
        setCommonAndProfileCapabilities(profile,desiredCapabilities);
        fineTuneCapabilities(desiredCapabilities);
        preProcess(desiredCapabilities);
        webDriver = BrowserStackWebDriver.getDriver(profile,desiredCapabilities);
        webDriver.setStatus(STATUS_PASSED);
        webDriver.setReason(TEST_PASSED_MESSAGE);
        runTest();
    }

    private void setCommonAndProfileCapabilities(String profile, DesiredCapabilities desiredCapabilities) {
        Map<String,String> commonCapabilities = JsonUtil.getCommonCapabilities();
        Map<String,String> profileCapabilities = JsonUtil.getProfileCapabilities(profile);
        DesiredCapabilities commonDesiredCapabilities = new DesiredCapabilities(commonCapabilities);
        DesiredCapabilities profileDesiredCapabilities = new DesiredCapabilities(profileCapabilities);
        desiredCapabilities.merge(commonDesiredCapabilities);
        desiredCapabilities.merge(profileDesiredCapabilities);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private void fineTuneCapabilities(DesiredCapabilities desiredCapabilities) {

        String projectProperty = (String) Stream.of(System.getProperty(PROJECT),desiredCapabilities.getCapability(CAPABILITY_PROJECT))
                .filter(Objects::nonNull)
                .findFirst()
                .get();

        String buildProperty = Stream.of(System.getProperty(BUILD),generateDefaultBuildName(desiredCapabilities))
                .filter(Objects::nonNull)
                .findFirst()
                .get();

        String nameProperty = Stream.of(System.getProperty(NAME),generateDefaultTestName(desiredCapabilities))
                .filter(Objects::nonNull)
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
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        return String.format("%s [%s]",name,dateFormat.format(Date.from(Instant.now())));
    }

}
