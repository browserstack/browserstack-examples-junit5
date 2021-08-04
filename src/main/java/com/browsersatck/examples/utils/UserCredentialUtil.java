package com.browsersatck.examples.utils;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class UserCredentialUtil {

    private static final String CREDENTIALS_FILE = "src/test/resources/user.csv";
    private static final Map<String, String> CREDENTIALS_MAP = parseCredentials();

    private static Map<String, String> parseCredentials() {
        Map<String, String> credentialMap = new HashMap<>();

        try {
            FileReader credentialsFile = new FileReader(CREDENTIALS_FILE);
            CSVReader credentialsReader = new CSVReader(credentialsFile);
            credentialsReader.skip(1);
            for (String[] row : credentialsReader) {
                credentialMap.put(row[0], row[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return credentialMap;
    }

    public static String getPassword(String user) {
        return CREDENTIALS_MAP.get(user);
    }
}
