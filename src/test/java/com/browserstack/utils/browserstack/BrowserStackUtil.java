package com.browserstack.utils.browserstack;

import com.browserstack.utils.Constants;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class BrowserStackUtil {

    private static final String BROWSERSTACK_USER_ENV = "BROWSERSTACK_USER";
    private static final String BROWSERSTACK_ACCESSKEY_ENV = "BROWSERSTACK_ACCESSKEY";
    private static final String BROWSERSTACK_HUB_URL_FORMAT = "https://%s:%s@hub.browserstack.com/wd/hub";
    private final long PARALLEL_SESSIONS_MAX_ALLOWED;
    private final long QUEUED_SESSIONS_MAX_ALLOWED;


    private final String browserstackUserName;
    private final String browserstackAccessKey;

    public BrowserStackUtil() {
        this.browserstackUserName = System.getenv(BROWSERSTACK_USER_ENV);
        this.browserstackAccessKey = System.getenv(BROWSERSTACK_ACCESSKEY_ENV);
        JSONObject jsonObject = callAPI(Constants.BrowserStackRESTAPIs.GET_PLAN);
        PARALLEL_SESSIONS_MAX_ALLOWED = (long) jsonObject.get("parallel_sessions_max_allowed");
        QUEUED_SESSIONS_MAX_ALLOWED = (long) jsonObject.get("queued_sessions_max_allowed");
    }

    public String getBrowserstackUserName() {
        return browserstackUserName;
    }

    public String getBrowserstackAccessKey() {
        return browserstackAccessKey;
    }

    public URL getHubURL() {
        String url = String.format(BROWSERSTACK_HUB_URL_FORMAT, browserstackUserName, browserstackAccessKey);
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getParallelSessionsMaxAllowed() {
        return (int)PARALLEL_SESSIONS_MAX_ALLOWED;
    }

    public int getQueuedSessionsMaxAllowed() {
        return (int)QUEUED_SESSIONS_MAX_ALLOWED;
    }

    public int getSessionsMaxAllowed() {
        return getParallelSessionsMaxAllowed()+getQueuedSessionsMaxAllowed();
    }

    public int getParallelSessionsRunning(){
        JSONObject jsonObject = callAPI(Constants.BrowserStackRESTAPIs.GET_PLAN);
        return (int) jsonObject.get("parallel_sessions_running");
    }

    public int getQueuedSessions(){
        JSONObject jsonObject = callAPI(Constants.BrowserStackRESTAPIs.GET_PLAN);
        return (int) jsonObject.get("queued_sessions");
    }

    public int getTotalSessions() {
        return getParallelSessionsRunning()+getQueuedSessions();
    }

    public JSONObject callAPI(String url) {
        String plainCredentials = browserstackUserName + ":" + browserstackAccessKey;
        String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
        String authorizationHeader = "Basic " + base64Credentials;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Authorization", authorizationHeader)
                .header("Content-Type", "application/json")
                .build();
        JSONObject responseObject = null;
        try {
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            String body = response.body();
             responseObject = (JSONObject) new JSONParser().parse(body);
        } catch (IOException | InterruptedException | ParseException e) {
            e.printStackTrace();
        }
        return responseObject;

    /*public static void main(String[] args) {
            BrowserStackUtil browserStackUtil = new BrowserStackUtil();
             String url = String.format(BROWSERSTACK_PLAN_URL, browserStackUtil.browserstackUserName, browserStackUtil.getBrowserstackAccessKey());

             try{
            String plainCredentials = browserStackUtil.browserstackUserName + ":" + browserStackUtil.getBrowserstackAccessKey();
            String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
            // Create authorization header
            String authorizationHeader = "Basic " + base64Credentials;

            //java.net.http.HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create HTTP request object
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Authorization", authorizationHeader)
                    .header("Content-Type", "application/json")
                    .build();
            // Send HTTP request
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        }*/
    }
}