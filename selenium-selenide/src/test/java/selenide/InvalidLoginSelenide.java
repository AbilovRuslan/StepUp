package selenide;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;

public class InvalidLoginSelenide extends BaseSelenideTest {

    @Test
    public void invalidLogin() {
        open("/login");
        $("[name='username']").setValue("wrong");
        $("[name='password']").setValue("wrong");
        $("button[type='submit']").click();

        webdriver().shouldHave(url("http://localhost:8080/login?error"));
    }
}