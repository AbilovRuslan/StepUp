package tests.selenide;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DeleteAfterEachTestSelenide extends BaseSelenideTest {

    private String createdName;
    private String createdId;

    @AfterEach
    public void deleteProduct() {
        if (createdId != null) {
            open("/admin");
            $x("//button[@data-action='delete' and @data-id='" + createdId + "']")
                    .shouldBe(visible)
                    .click();
            confirm();
        }
    }

    @Test
    public void addAndDeleteAfterTest() {
        createdName = "Товар_для_удаления_" + System.currentTimeMillis();

        open("/login");
        $("[name='username']").setValue("admin");
        $("[name='password']").setValue("secret123");
        $("button[type='submit']").click();

        open("/admin");
        $("#n-name").setValue(createdName);
        $("#n-price").setValue("50");
        $("#add-btn").click();

        // Сохраняем id созданного товара
        createdId = $("table tbody tr:last-child td:first-child").getText();

        open("/");
        $("div[data-name='" + createdName + "'] h4")
                .shouldBe(visible)
                .shouldHave(text(createdName));
    }
}