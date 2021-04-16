package com.browserstack.runners;

public class OnPremiseRunner extends ProfiledTestRunner {

//    private WebDriver webDriver;
//    private ProfiledTest test;
//
//    public OnPremiseRunner(ProfiledTest test) {
//        this.test = test;
//    }
//
//
//    @Override
//    public void startDriver() {
//        Map<String,String> drivers = JsonUtil.getOnPremDrivers();
//        for (String key:drivers.keySet()){
//            System.setProperty(key,drivers.get(key));
//        }
//        webDriver = OnPremiseUtil.getDriver();
//    }
//
//    @Override
//    public void runTest(){
//        runTest(test,webDriver);
//    }
//
//    @Override
//    public void stopDriver() {
//        test.postProcess(webDriver);
//        webDriver.close();
//    }
}
