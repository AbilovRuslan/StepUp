package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.ConfigReader;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;

public abstract class BaseTest {

    @BeforeAll
    static void globalSetup() {
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));

        Configuration.baseUrl = ConfigReader.getStandUrl();
        Configuration.timeout = ConfigReader.getElementTimeout();
    }

    @BeforeEach
    public void setUp() {
        open("/");
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}