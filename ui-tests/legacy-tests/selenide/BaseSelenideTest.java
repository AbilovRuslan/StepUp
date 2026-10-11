package tests.selenide;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;

public abstract class BaseSelenideTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}