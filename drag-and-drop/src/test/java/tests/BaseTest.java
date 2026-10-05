package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.ConfigReader;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;

public abstract class BaseTest {

    @BeforeEach
    public void setUp() {
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));

        Configuration.baseUrl = ConfigReader.getStandUrl();
        Configuration.timeout = ConfigReader.getElementTimeout();

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
        SelenideLogger.removeListener("AllureSelenide");
    }
}