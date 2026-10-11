package tests.selenide;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AddProductSelenide extends BaseSelenideTest {

    @Test
    public void addProductAndVerify() {
        String productName = "Стакан";

        open("/login");
        $("[name='username']").setValue("admin");
        $("[name='password']").setValue("secret123");
        $("button[type='submit']").click();

        open("/admin");
        $("#n-name").setValue(productName);
        $("#n-price").setValue("25");
        $("#add-btn").click();

        open("/");
        $("div[data-price*='25'] h4")
                .shouldBe(visible)
                .shouldHave(text(productName));
    }
}