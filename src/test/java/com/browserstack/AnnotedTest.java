package com.browserstack;

import io.qameta.allure.Step;

import static com.browserstack.utils.Constants.BrowserStackRESTStatus.*;

public abstract class AnnotedTest{

    private TestDetails testDetails;

    public TestDetails getTestDetails() {
        return testDetails;
    }

    public void setTestDetails(TestDetails testDetails) {
        this.testDetails = testDetails;
    }

    @Step("Marking test fail")
    public final String  markFail(String reason) {
        testDetails.setStatus(STATUS_FAILED);
        testDetails.setReason(reason);
        return reason;
    }
}
