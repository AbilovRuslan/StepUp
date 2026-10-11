package tests.selenide;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class EditProductViaAdminSelenide extends BaseSelenideTest {

    private String productId;

    @AfterEach
    public void deleteProduct() {
        if (productId != null) {
            open("/admin");
            $x("//button[@data-action='delete' and @data-id='" + productId + "']")
                    .shouldBe(visible)
                    .click();
            confirm();
        }
    }

    @Test
    public void editProductViaAdminAndVerify() {
        String originalName = "Редактируемый_" + System.currentTimeMillis();
        String newName = originalName + "_New";

        open("/login");
        $("[name='username']").setValue("admin");
        $("[name='password']").setValue("secret123");
        $("button[type='submit']").click();

        open("/admin");
        $("#n-name").setValue(originalName);
        $("#n-price").setValue("50");
        $("#add-btn").click();

        productId = $("table tbody tr:last-child td:first-child").getText();

        $x("//button[@data-action='update' and @data-id='" + productId + "']")
                .shouldBe(visible)
                .click();

        $("#n-name").setValue(newName);
        $("#n-price").setValue("60");
        $("#add-btn").click();

        open("/");
        $("div[data-name='" + newName + "'] h4")
                .shouldBe(visible)
                .shouldHave(text(newName));

        // Проверка цены по стилю
        $("div[data-name='" + newName + "'] div[style*='color:var(--primary)']")
                .shouldBe(visible)
                .shouldHave(text("60 ₽"));
    }
}