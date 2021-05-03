package com.browserstack.utils.config;

import com.browserstack.local.Local;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LocalFactory extends Thread{

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalFactory.class);
    private final Local LOCAL = new Local();
    private static LocalFactory instance;
    private final String LOCAL_IDENTIFIER= RandomStringUtils.randomAlphabetic(8);

    @Override
    public void run() {
        try {
            if (LOCAL.isRunning()){
                LOCAL.stop();
                LOGGER.debug("Stopped BrowserStack Local with identifier {}.", LOCAL_IDENTIFIER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LocalFactory(Map<String,String> args){
        try {
            args.put("localIdentifier", LOCAL_IDENTIFIER);
            LOCAL.start(args);
            LOGGER.debug("Started BrowserStack Local with identifier {}.", LOCAL_IDENTIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createInstance(Map<String,String> args) {
        instance = new LocalFactory(args);
        Runtime.getRuntime().addShutdownHook(instance);
    }

    public static String getLocalIdentifier(){return instance.LOCAL_IDENTIFIER;}

}
