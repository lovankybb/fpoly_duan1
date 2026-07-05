package com.fptpolytechnic.duan1.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private static  AppConfig instance;
    private Properties properties;


//     Find and load file application.properties
    private AppConfig(){
        properties = new Properties();

        try(InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")){
            if(input != null){
                properties.load(input);
            }
            else{
                System.out.println("Cannot find file application.properties");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

//    Get static instance
    public static AppConfig getInstance(){
        if(instance == null){
            synchronized (AppConfig.class){ /// Synchronize to be safer when many request come at the same time
                if (instance == null){
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }


    public String getProperty(String key){
        return properties.getProperty(key);
    }


//    if there is no properties with key then return default val
    public String getProperty(String key, String defaultVal){
        return properties.getProperty(key, defaultVal);
    }
}
