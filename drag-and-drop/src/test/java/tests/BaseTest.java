package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import config.ConfigReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;

public abstract class BaseTest {

    @BeforeEach
    public void setUp() {
        // Настройки из конфига
        Configuration.baseUrl = ConfigReader.getStandUrl();
        Configuration.timeout = ConfigReader.getElementTimeout();

        // Вывод  в консоль (без логина и пароля)
        System.out.println("========== TEST CONFIGURATION ==========");
        System.out.println("Stand URL:       " + ConfigReader.getStandUrl());
        System.out.println("API URL:         " + ConfigReader.getApiUrl());
        System.out.println("Element timeout: " + ConfigReader.getElementTimeout());
        System.out.println("Logging mode:    " + ConfigReader.getLoggingMode());
        System.out.println("Product name:    " + ConfigReader.getProductName());
        System.out.println("Product price:   " + ConfigReader.getProductPrice());
        System.out.println("========================================");

        open("/");
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}