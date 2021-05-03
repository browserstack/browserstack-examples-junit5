package com.browserstack.utils.config;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import io.qameta.allure.Step;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserCredentialsParser {

    private static final String USER_CREDENTIAL_FILE = "src/test/resources/user.csv";
    private static final Map<String,String> USER_CREDENTIALS = readCredentials();

    private static Map<String, String> readCredentials() {
        Map<String,String> userCredentials = new HashMap<>();
        try {
            CSVReader csvReader = new CSVReader(new FileReader(USER_CREDENTIAL_FILE));
            List<String[]> lines = csvReader.readAll();
            lines.forEach(line -> userCredentials.put(line[0],line[1]));
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return userCredentials;
    }

    @Step("Reading user credentials for {0}")
    public static String getPassword(String user){
       return USER_CREDENTIALS.get(user);
    }
}
