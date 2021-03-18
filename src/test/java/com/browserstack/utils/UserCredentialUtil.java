package com.browserstack.utils;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class UserCredentialUtil {

    private static final String CREDENTIALS_FILE="src/test/resources/user.csv";
    private static final Map<String,String> CREDENTIALS_MAP = parseCredentials();

    private static Map<String,String> parseCredentials(){
        Map<String,String> credentialMap = new HashMap<>();

        try {
            FileReader credentialsFile = new FileReader(CREDENTIALS_FILE);
            CSVReader csvReader = new CSVReader(credentialsFile);
            for (String[] row : csvReader) {
                credentialMap.put(row[0], row[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return credentialMap;
    }

    public static String getPassword(String user) {
        return CREDENTIALS_MAP.get(user);
    }
}
