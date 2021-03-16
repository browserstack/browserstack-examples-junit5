package com.browserstack.utils;

import com.opencsv.CSVReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.IterableResult;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.ParsingContext;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.ResultIterator;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParser;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParserSettings;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvRoutines;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.CSSValueList;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserCredentialUtil {

    private static final String CREDENTIALS_FILE="src/test/resources/user.csv";
    private static final Map<String,String> CREDENTIALS_MAP = parseCredentials();

    private static Map<String,String> parseCredentials(){
        Map<String,String> credentialMap = new HashMap<>();

        try {
            FileReader credentialsFile = new FileReader(CREDENTIALS_FILE);
            CSVReader csvReader = new CSVReader(credentialsFile);
            Iterator iterator = csvReader.iterator();
            while (iterator.hasNext()){
                String[] row = (String[]) iterator.next();
                credentialMap.put(row[0],row[1]);
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
