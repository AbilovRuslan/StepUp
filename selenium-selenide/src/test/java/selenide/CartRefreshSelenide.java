package selenide;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

// Сейчас корзина после refresh пустая — известный баг.
// Если поведение приложения изменится и корзина начнёт сохраняться,
// проверку нужно актуализировать.

public class CartRefreshSelenide extends BaseSelenideTest {

    @Test
    public void cartPersistenceAfterRefresh() {
        // 1. Создаём товар "Стакан" через админку
        open("/login");
        $("[name='username']").setValue("admin");
        $("[name='password']").setValue("secret123");
        $("button[type='submit']").click();

        open("/admin");
        $("#n-name").setValue("Стакан");
        $("#n-price").setValue("25");
        $("#add-btn").click();

        // 2. Переходим на витрину и добавляем товар в корзину
        open("/");
        $x("//div[@data-name='Стакан']//button[contains(text(),'В корзину')]").click();

        // 3. Открываем корзину и проверяем наличие товара
        $("#open-cart-btn").click();
        $("#cart-items .cart-item")
                .shouldBe(visible)
                .shouldHave(text("Стакан"));

        // 4. Обновляем страницу
        refresh();

        // 5. Снова открываем корзину и проверяем, что товара НЕТ
        $("#open-cart-btn").click();

        // Проверяем, что товар отсутствует
        $("#cart-items .cart-item")
                .shouldBe(not(visible));
    }
}