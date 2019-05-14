package ru.test.avito.credential;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class TokenHandler {

    private TokenHandler() {

    }

    public static String getToken() {
        Properties properties = loadPropertiesFile();
        return properties.getProperty("token");
    }

    private static Properties loadPropertiesFile() {
        Properties properties = new Properties();
        try (InputStream inputStream = TokenHandler.class.getResourceAsStream("/credentials.properties");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            properties.load(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
