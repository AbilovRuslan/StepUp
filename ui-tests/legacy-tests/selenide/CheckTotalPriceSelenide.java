package tests.selenide;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class CheckTotalPriceSelenide extends BaseSelenideTest {

    @Test
    public void checkTotalPrice() {
        // 1. Создаём два товара через админку
        open("/login");
        $("[name='username']").setValue("admin");
        $("[name='password']").setValue("secret123");
        $("button[type='submit']").click();

        open("/admin");
        $("#n-name").setValue("Стакан");
        $("#n-price").setValue("25");
        $("#add-btn").click();

        open("/admin");
        $("#n-name").setValue("Ластик");
        $("#n-price").setValue("3.3");
        $("#add-btn").click();

        // 2. Переходим на витрину и добавляем товары в корзину
        open("/");
        $x("//div[@data-name='Стакан']//button[contains(text(),'В корзину')]").click();
        $x("//div[@data-name='Ластик']//button[contains(text(),'В корзину')]").click();

        // 3. Открываем корзину и проверяем сумму
        $("#open-cart-btn").click();
        $("#total-price").shouldHave(text("28.3"));
    }
}