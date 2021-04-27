package com.browserstack;

public abstract class ProfiledTest extends AnnotedTest{

//    private static final String PROFILE_SINGLE_TEST_DATA = "/single.csv";
//    private static final String PROFILE_PARALLEL_TEST_DATA = "/parallel.csv";
//    private static final String PROFILE_LOCAL_TEST_DATA = "/single.csv";
//    private static final String PROFILE_LOCAL_PARALLEL_TEST_DATA = "/parallel.csv";
//
//    private ProfiledTestRunner runner;
//
//    public void preProcess(DesiredCapabilities desiredCapabilities) {}
//    public abstract void run(WebDriver webDriver);
//    public void postProcess(WebDriver webDriver) {}
//
//    @Test
//    @Tag(PROFILE_ON_PREMISE)
//    @Description(PROFILE_ON_PREMISE)
//    public final void runOnPremise() {
//        initialiseTest(PROFILE_ON_PREMISE);
//        startTest(new OnPremiseRunner(this));
//    }
//
//    @Test
//    @Tag(PROFILE_DOCKER)
//    @Description(PROFILE_DOCKER)
//    public final void runOnDocker() {
//        initialiseTest(PROFILE_DOCKER);
//        startTest(new DockerRunner(this));
//    }
//
//    @ParameterizedTest()
//    @CsvFileSource(resources = PROFILE_SINGLE_TEST_DATA)
//    @Tag(PROFILE_SINGLE)
//    @Description(PROFILE_SINGLE)
//    public final void single(@AggregateWith(CapabilityConverter.class) DesiredCapabilities desiredCapabilities) {
//        initialiseTest(PROFILE_SINGLE);
//        startTest(new CloudRunner(this,desiredCapabilities));
//    }
//
//    @ParameterizedTest()
//    @CsvFileSource(resources = PROFILE_PARALLEL_TEST_DATA)
//    @Tag(PROFILE_PARALLEL)
//    @Description(PROFILE_PARALLEL)
//    public final void parallel(@AggregateWith(CapabilityConverter.class) DesiredCapabilities desiredCapabilities) {
//        initialiseTest(PROFILE_PARALLEL);
//        startTest(new CloudRunner(this,desiredCapabilities));
//    }
//
//    @ParameterizedTest()
//    @CsvFileSource(resources = PROFILE_LOCAL_TEST_DATA)
//    @Tag(PROFILE_LOCAL)
//    @Description(PROFILE_LOCAL)
//    public final void local(@AggregateWith(CapabilityConverter.class) DesiredCapabilities desiredCapabilities) {
//        initialiseTest(PROFILE_LOCAL);
//        startTest(new CloudLocalRunner(this,desiredCapabilities));
//    }
//
//    @ParameterizedTest()
//    @CsvFileSource(resources = PROFILE_LOCAL_PARALLEL_TEST_DATA)
//    @Tag(PROFILE_LOCAL_PARALLEL)
//    @Description(PROFILE_LOCAL_PARALLEL)
//    public final void localParallel(@AggregateWith(CapabilityConverter.class) DesiredCapabilities desiredCapabilities) {
//        initialiseTest(PROFILE_LOCAL_PARALLEL);
//        startTest(new CloudLocalRunner(this,desiredCapabilities));
//    }
//
//    @AfterEach
//    public final void close() {
//       stopTest();
//    }
//
//    @Step("Initialising the test")
//    private void initialiseTest(String profile){
//        setTestDetails(new TestDetails(profile,STATUS_PASSED,TEST_PASSED_MESSAGE));
//    }
//
//    @Step("Starting the test")
//    private void startTest(ProfiledTestRunner runner){
//        this.runner = runner;
//        runner.startDriver();
//        runner.runTest();
//    }
//
//    @Step("Stopping the test")
//    public final void  stopTest(){
//        runner.stopDriver();
//    }

}
