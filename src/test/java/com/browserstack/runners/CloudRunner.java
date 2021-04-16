package com.browserstack.runners;

public class CloudRunner extends ProfiledTestRunner {

//    private ProfiledTest test;
//    private DesiredCapabilities desiredCapabilities;
//    private WebDriver webDriver;
//    private static final String BROWSERSTACK_MARK_STATUS_SCRIPT_FORMAT = "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"%s\", \"reason\": \"%s\"}}";
//
//    public CloudRunner(ProfiledTest test, DesiredCapabilities desiredCapabilities) {
//        this.test = test;
//        this.desiredCapabilities = desiredCapabilities;
//        setCloudCapabilities();
//    }
//
//    @Override
//    public void startDriver() {
//        webDriver = BrowserStackUtil.getDriver(desiredCapabilities);
//    }
//
//    @Override
//    public void runTest() {
//        runTest(test,webDriver);
//    }
//
//    @Override
//    public void stopDriver() {
//        markStatus(webDriver,test.getTestDetails().getStatus(),test.getTestDetails().getReason());
//        webDriver.quit();
//    }
//
//    private void setCloudCapabilities(){
//        setCommonAndProfileCapabilities(test.getTestDetails().getProfile(), desiredCapabilities);
//        fineTuneCapabilities(desiredCapabilities);
//        test.preProcess(desiredCapabilities);
//    }
//
//    @Step("Setting common and profile specific capabilities")
//    private void setCommonAndProfileCapabilities(String profile, DesiredCapabilities desiredCapabilities) {
//        Map<String, String> commonCapabilities = JsonUtil.getCommonCapabilities();
//        Map<String, String> profileCapabilities = JsonUtil.getProfileCapabilities(profile);
//        DesiredCapabilities commonDesiredCapabilities = new DesiredCapabilities(commonCapabilities);
//        DesiredCapabilities profileDesiredCapabilities = new DesiredCapabilities(profileCapabilities);
//        desiredCapabilities.merge(commonDesiredCapabilities);
//        desiredCapabilities.merge(profileDesiredCapabilities);
//    }
//
//    @Step("Fine tuning project, build and test name")
//    private void fineTuneCapabilities(DesiredCapabilities desiredCapabilities) {
//
//        String projectProperty = (String) Stream.of(System.getProperty(PROJECT), desiredCapabilities.getCapability(CAPABILITY_PROJECT))
//                .filter(Objects::nonNull)
//                .findFirst()
//                .get();
//
//        String buildProperty = Stream.of(System.getProperty(BUILD), generateDefaultBuildName(desiredCapabilities))
//                .filter(Objects::nonNull)
//                .findFirst()
//                .get();
//
//        String nameProperty = Stream.of(System.getProperty(NAME), generateDefaultTestName(desiredCapabilities))
//                .filter(Objects::nonNull)
//                .findFirst()
//                .get();
//
//        desiredCapabilities.setCapability(CAPABILITY_PROJECT, projectProperty);
//        desiredCapabilities.setCapability(CAPABILITY_BUILD, buildProperty);
//        desiredCapabilities.setCapability(CAPABILITY_NAME, nameProperty);
//    }
//
//    private String generateDefaultTestName(DesiredCapabilities desiredCapabilities) {
//        String profileName = (String) desiredCapabilities.getCapability(CAPABILITY_NAME);
//        String testName = test.getClass().getSimpleName();
//        String os = (String) desiredCapabilities.getCapability(CAPABILITY_OS);
//        String browser = (String) desiredCapabilities.getCapability(CAPABILITY_BROWSER);
//        String device = (String) desiredCapabilities.getCapability(CAPABILITY_DEVICE);
//        return String.format("%s %s [%s/%s]", profileName, testName, os, browser != null ? browser : device);
//    }
//
//    private String generateDefaultBuildName(DesiredCapabilities desiredCapabilities) {
//        String name = (String) desiredCapabilities.getCapability(CAPABILITY_BUILD);
//        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
//        return String.format("%s [%s]", name, dateFormat.format(Date.from(Instant.now())));
//    }
//
//    @Step("Marking test test status at Browserstack")
//    private void markStatus(WebDriver webDriver,String status, String reason){
//        ((JavascriptExecutor)webDriver).executeScript(String.format(BROWSERSTACK_MARK_STATUS_SCRIPT_FORMAT,status,reason));
//    }

}
