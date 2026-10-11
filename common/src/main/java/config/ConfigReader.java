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

    private ConfigReader() {}

    // ===== Stand / API =====

    public static String getStandUrl() {
        return PROPERTIES.getProperty("stand.url");
    }

    public static String getApiUrl() {
        return PROPERTIES.getProperty("api.url");
    }

    public static int getElementTimeout() {
        return Integer.parseInt(PROPERTIES.getProperty("element.timeout"));
    }

    public static String getLoggingMode() {
        return PROPERTIES.getProperty("logging.mode");
    }

    // ===== Admin =====

    public static String getAdminUsername() {
        return PROPERTIES.getProperty("admin.username");
    }

    public static String getAdminPassword() {
        return PROPERTIES.getProperty("admin.password");
    }

    // ===== Products (UI) =====

    public static String getProductName() {
        return PROPERTIES.getProperty("product.name");
    }

    public static String getSecondProductName() {
        return PROPERTIES.getProperty("product.name2");
    }

    public static String getAdminProductPrefix() {
        return PROPERTIES.getProperty("product.name.admin");
    }

    public static String getUpdatedProductName() {
        return PROPERTIES.getProperty("product.name.updated");
    }

    public static double getProductPrice() {
        return Double.parseDouble(PROPERTIES.getProperty("product.price"));
    }

    public static String getUpdatedProductPrice() {
        return PROPERTIES.getProperty("product.price.updated");
    }

    // ===== Cart =====

    public static String getCartTotalThree() {
        return PROPERTIES.getProperty("cart.total.price.three");
    }

    public static String getCartTotalTwo() {
        return PROPERTIES.getProperty("cart.total.price.two");
    }

    // ===== Notifications =====

    public static String getToastOrderAccepted() {
        return PROPERTIES.getProperty("toast.order.accepted");
    }

    public static String getToastProductAdded() {
        return PROPERTIES.getProperty("toast.product.added");
    }

    // ===== API paths =====

    public static String getApiGoodsPath() {
        return PROPERTIES.getProperty("api.goods.path");
    }

    public static String getApiAddPath() {
        return PROPERTIES.getProperty("api.add.path");
    }

    public static String getApiListPath() {
        return PROPERTIES.getProperty("api.list.path");
    }

    public static String getApiIdPath() {
        return PROPERTIES.getProperty("api.id.path");
    }

    // ===== HTTP statuses =====
    public static int getStatusOk() { return Integer.parseInt(PROPERTIES.getProperty("api.status.ok")); }
    public static int getStatusBadRequest() { return Integer.parseInt(PROPERTIES.getProperty("api.status.bad.request")); }
    public static int getStatusUnauthorized() { return Integer.parseInt(PROPERTIES.getProperty("api.status.unauthorized")); }
    public static int getStatusNotFound() { return Integer.parseInt(PROPERTIES.getProperty("api.status.not.found")); }

    // ===== Messages =====
    public static String getMessageSuccess() { return PROPERTIES.getProperty("api.message.success"); }

    // ===== Test data =====
    public static int getNotFoundId() { return Integer.parseInt(PROPERTIES.getProperty("api.not.found.id")); }
    public static int getUnauthorizedId() { return Integer.parseInt(PROPERTIES.getProperty("api.unauthorized.id")); }

    public static double getPriceDefault() { return Double.parseDouble(PROPERTIES.getProperty("api.product.price.default")); }
    public static double getPriceLow() { return Double.parseDouble(PROPERTIES.getProperty("api.product.price.low")); }
    public static double getPriceCheap() { return Double.parseDouble(PROPERTIES.getProperty("api.product.price.cheap")); }
    public static double getPriceMid() { return Double.parseDouble(PROPERTIES.getProperty("api.product.price.mid")); }
    public static double getPriceUpdated() { return Double.parseDouble(PROPERTIES.getProperty("api.product.price.updated")); }
    public static double getPriceDelete() { return Double.parseDouble(PROPERTIES.getProperty("api.product.price.delete")); }

    public static String getInvalidPatchBody() { return PROPERTIES.getProperty("api.invalid.patch.body"); }
    public static String getPrefixNevazhno() { return PROPERTIES.getProperty("api.product.prefix.nezvazhno"); }
}