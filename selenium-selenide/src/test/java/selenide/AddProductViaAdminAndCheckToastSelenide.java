package selenide;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AddProductViaAdminAndCheckToastSelenide extends BaseSelenideTest {

    @Test
    public void addProductViaAdminAndCheckToast() {
        String productName = "Товар_из_админки_" + System.currentTimeMillis();

        open("/login");
        $("[name='username']").setValue("admin");
        $("[name='password']").setValue("secret123");
        $("button[type='submit']").click();

        open("/admin");
        $("#n-name").setValue(productName);
        $("#n-price").setValue("100");
        $("#add-btn").click();

        $("#toast-container")
                .shouldBe(visible)
                .shouldHave(text("Товар успешно добавлен!"));
    }
}