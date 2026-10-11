package tests.selenide;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AddThreeItemsSelenide extends BaseSelenideTest {

    @Test
    public void addThreeItemsAndCheckNotification() {
        // 1. Создаём товар "Стакан" через админку
        open("/login");
        $("[name='username']").setValue("admin");
        $("[name='password']").setValue("secret123");
        $("button[type='submit']").click();

        open("/admin");
        $("#n-name").setValue("Стакан");
        $("#n-price").setValue("25");
        $("#add-btn").click();

        // 2. Переходим на витрину и добавляем 3 единицы товара в корзину
        open("/");
        for (int i = 0; i < 3; i++) {
            $x("//div[@data-name='Стакан']//button[contains(text(),'В корзину')]").click();
        }

        // 3. Открываем корзину и проверяем сумму
        $("#open-cart-btn").click();
        $("#total-price").shouldHave(text("75"));

        // 4. Оформляем заказ
        $("#makeOrder").click();

        // 5. Проверяем уведомление
        $("#toast-container")
                .shouldBe(visible)
                .shouldHave(text("Заказ принят в обработку"));
    }
}