package ru.red_collar.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public static String get(final String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (final InputStream inputStream = PropertiesUtil.class.getClassLoader()
            .getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
