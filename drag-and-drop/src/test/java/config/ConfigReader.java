package config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }
            PROPERTIES.load(new InputStreamReader(input, StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getStandUrl() {
        return PROPERTIES.getProperty("stand.url");
    }

    public static String getLoggingMode() {
        return PROPERTIES.getProperty("logging.mode");
    }

    public static String getApiUrl() {
        return PROPERTIES.getProperty("api.url");
    }

    public static int getElementTimeout() {
        return Integer.parseInt(PROPERTIES.getProperty("element.timeout"));
    }

    public static String getAdminUsername() {
        return PROPERTIES.getProperty("admin.username");
    }

    public static String getAdminPassword() {
        return PROPERTIES.getProperty("admin.password");
    }

    public static String getProductName() {
        return PROPERTIES.getProperty("product.name");
    }

    public static double getProductPrice() {
        return Double.parseDouble(PROPERTIES.getProperty("product.price"));
    }
}