package com.tatu.mulher.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {

    public static String getConnectionString() {

        Properties prop = new Properties();
        String filePath = "./src/main/java/com/tatu/mulher/config/";
        String fileName = "app.config";

        try (FileInputStream fis = new FileInputStream(filePath + fileName)) {
            prop.load(fis);
        } catch (FileNotFoundException error) {
            System.out.println(error);
        } catch (IOException error) {
            System.out.println(error);
        }

        return prop.getProperty("app.connectionString");
    }

}
