package com.browserstack;

import com.browserstack.utils.CapabilityConverter;
import com.browserstack.utils.JsonUtil;
import com.browserstack.utils.ManagedWebDriver;
import com.browserstack.utils.browserstack.BrowserStackWebDriver;
import com.browserstack.utils.docker.DockerWebDriver;
import com.browserstack.utils.onpremise.OnPremiseWebDriver;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static com.browserstack.utils.Constants.BrowserStackRESTStatus.*;
import static com.browserstack.utils.Constants.Capabilities.*;
import static com.browserstack.utils.Constants.ErrorMessages.PAGE_NOT_LOADED_ON_TIME;
import static com.browserstack.utils.Constants.ErrorMessages.PAGE_NOT_RENDERED_CORRECTLY;
import static com.browserstack.utils.Constants.Profiles.*;
import static com.browserstack.utils.Constants.RuntimeOptions.*;

public abstract class AbstractTest {

    private static final String TEST_EXCEPTION_MESSAGE = "Exception encountered : ";
    private static final String SINGLE_PROFILE_TEST_DATA = "/single.csv";
    private static final String PARALLEL_PROFILE_TEST_DATA = "/parallel.csv";
    private static final String LOCAL_PROFILE_TEST_DATA = "/single.csv";
    private static final String LOCAL_PARALLEL_PROFILE_TEST_DATA = "/parallel.csv";

    private ManagedWebDriver webDriver;

    public void preProcess(DesiredCapabilities desiredCapabilities) {
    }

    public abstract void run(WebDriver webDriver);

    public void postProcess(WebDriver webDriver) {
    }


    @Test
    @Tag(PROFILE_ON_PREMISE)
    public final void runOnPremise() {
        JsonUtil.setOnPremDriverPath();
        webDriver = OnPremiseWebDriver.getDriver();
        runTest();
    }

    @Test
    @Tag(DOCKER)
    public final void runOnDocker() {
        webDriver = DockerWebDriver.getDriver();
        runTest();
    }

    @ParameterizedTest()
    @CsvFileSource(resources = SINGLE_PROFILE_TEST_DATA)
    @Tag(PROFILE_SINGLE)
    public final void single(@AggregateWith(CapabilityConverter.class) DesiredCapabilities desiredCapabilities) {
        runOnCloud(PROFILE_SINGLE, desiredCapabilities);
    }

    @ParameterizedTest()
    @CsvFileSource(resources = PARALLEL_PROFILE_TEST_DATA)
    @Tag(PROFILE_PARALLEL)
    public final void parallel(@AggregateWith(CapabilityConverter.class) DesiredCapabilities desiredCapabilities) {
        runOnCloud(PROFILE_PARALLEL, desiredCapabilities);
    }

    @ParameterizedTest()
    @CsvFileSource(resources = LOCAL_PROFILE_TEST_DATA)
    @Tag(PROFILE_LOCAL)
    public final void local(@AggregateWith(CapabilityConverter.class) DesiredCapabilities desiredCapabilities) {
        runOnCloud(PROFILE_LOCAL, desiredCapabilities);
    }

    @ParameterizedTest()
    @CsvFileSource(resources = LOCAL_PARALLEL_PROFILE_TEST_DATA)
    @Tag(PROFILE_LOCAL_PARALLEL)
    public final void localParallel(@AggregateWith(CapabilityConverter.class) DesiredCapabilities desiredCapabilities) {
        runOnCloud(PROFILE_LOCAL_PARALLEL, desiredCapabilities);
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

    @Step("Marking test fail")
    public String markFail(String reason) {
        webDriver.setStatus(STATUS_FAILED);
        webDriver.setReason(reason);
        return reason;
    }


    private void runTest() {
        try {
            run(webDriver);
        } catch (TimeoutException e) {
            markFail(PAGE_NOT_LOADED_ON_TIME);
        } catch (NoSuchElementException e) {
            markFail(PAGE_NOT_RENDERED_CORRECTLY);
        } catch (Exception e) {
            markFail(TEST_EXCEPTION_MESSAGE + e);
            throw e;
        }
    }

    private void runOnCloud(String profile, DesiredCapabilities desiredCapabilities) {
        setCommonAndProfileCapabilities(profile, desiredCapabilities);
        fineTuneCapabilities(desiredCapabilities);
        preProcess(desiredCapabilities);
        webDriver = BrowserStackWebDriver.getDriver(profile, desiredCapabilities);
        webDriver.setStatus(STATUS_PASSED);
        webDriver.setReason(TEST_PASSED_MESSAGE);
        runTest();
    }

    @Step("Setting common common and profile specific capabilities")
    private void setCommonAndProfileCapabilities(String profile, DesiredCapabilities desiredCapabilities) {
        Map<String, String> commonCapabilities = JsonUtil.getCommonCapabilities();
        Map<String, String> profileCapabilities = JsonUtil.getProfileCapabilities(profile);
        DesiredCapabilities commonDesiredCapabilities = new DesiredCapabilities(commonCapabilities);
        DesiredCapabilities profileDesiredCapabilities = new DesiredCapabilities(profileCapabilities);
        desiredCapabilities.merge(commonDesiredCapabilities);
        desiredCapabilities.merge(profileDesiredCapabilities);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Step("Fine tuning project, build and test name")
    private void fineTuneCapabilities(DesiredCapabilities desiredCapabilities) {

        String projectProperty = (String) Stream.of(System.getProperty(PROJECT), desiredCapabilities.getCapability(CAPABILITY_PROJECT))
                .filter(Objects::nonNull)
                .findFirst()
                .get();

        String buildProperty = Stream.of(System.getProperty(BUILD), generateDefaultBuildName(desiredCapabilities))
                .filter(Objects::nonNull)
                .findFirst()
                .get();

        String nameProperty = Stream.of(System.getProperty(NAME), generateDefaultTestName(desiredCapabilities))
                .filter(Objects::nonNull)
                .findFirst()
                .get();

        desiredCapabilities.setCapability(CAPABILITY_PROJECT, projectProperty);
        desiredCapabilities.setCapability(CAPABILITY_BUILD, buildProperty);
        desiredCapabilities.setCapability(CAPABILITY_NAME, nameProperty);
    }

    private String generateDefaultTestName(DesiredCapabilities desiredCapabilities) {
        String profileName = (String) desiredCapabilities.getCapability(CAPABILITY_NAME);
        String testName = this.getClass().getSimpleName();
        String os = (String) desiredCapabilities.getCapability(CAPABILITY_OS);
        String browser = (String) desiredCapabilities.getCapability(CAPABILITY_BROWSER);
        String device = (String) desiredCapabilities.getCapability(CAPABILITY_DEVICE);
        return String.format("%s %s [%s/%s]", profileName, testName, os, browser != null ? browser : device);
    }

    private String generateDefaultBuildName(DesiredCapabilities desiredCapabilities) {
        String name = (String) desiredCapabilities.getCapability(CAPABILITY_BUILD);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        return String.format("%s [%s]", name, dateFormat.format(Date.from(Instant.now())));
    }

}
