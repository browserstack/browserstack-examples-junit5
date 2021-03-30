package com.browserstack;

public class TestDetails {

    private String profile;
    private String status;
    private String reason;

    public TestDetails(String profile, String status, String reason) {
        this.profile = profile;
        this.status = status;
        this.reason = reason;
    }

    public String getProfile() {
        return profile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
