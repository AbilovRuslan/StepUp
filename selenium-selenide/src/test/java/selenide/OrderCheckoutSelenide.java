package selenide;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderCheckoutSelenide extends BaseSelenideTest {

    @Test
    public void checkoutWithTotalOver300() {
        // 1. Создаём три разных товара через админку
        open("/login");
        $("[name='username']").setValue("admin");
        $("[name='password']").setValue("secret123");
        $("button[type='submit']").click();

        // Товар 1: Стакан (25)
        open("/admin");
        $("#n-name").setValue("Стакан");
        $("#n-price").setValue("25");
        $("#add-btn").click();

        // Товар 2: Ластик (100)
        open("/admin");
        $("#n-name").setValue("Ластик");
        $("#n-price").setValue("100");
        $("#add-btn").click();

        // Товар 3: Карандаш (200)
        open("/admin");
        $("#n-name").setValue("Карандаш");
        $("#n-price").setValue("200");
        $("#add-btn").click();

        // 2. Переходим на витрину и добавляем все три товара в корзину
        open("/");
        $("div[data-name='Стакан'] button:contains('В корзину')").click();
        $("div[data-name='Ластик'] button:contains('В корзину')").click();
        $("div[data-name='Карандаш'] button:contains('В корзину')").click();

        // 3. Открываем корзину и проверяем сумму
        $("#open-cart-btn").click();
        $("#total-price").shouldHave(text("325.0"));

        // 4. Оформляем заказ
        $("#makeOrder").click();

        // 5. Проверяем наличие JS Alert (через try-catch или переключение)
        try {
            switchTo().alert().accept();
            assertTrue(true, "Alert appeared and was accepted");
        } catch (Exception e) {
            assertTrue(false, "Alert did not appear");
        }
    }
}