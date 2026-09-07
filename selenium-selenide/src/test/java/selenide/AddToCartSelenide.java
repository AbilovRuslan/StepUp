package selenide;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class AddToCartSelenide extends BaseSelenideTest {

    @Test
    public void addToCartAndVerify() {
        // 1. Логин и создание товара
        open("/login");
        $("[name='username']").setValue("admin");
        $("[name='password']").setValue("secret123");
        $("button[type='submit']").click();

        open("/admin");
        $("#n-name").setValue("Стакан");
        $("#n-price").setValue("25");
        $("#add-btn").click();

        // 2. Добавление в корзину через XPath
        open("/");
        $x("//div[@data-name='Стакан']//button[contains(text(),'В корзину')]").click();

        // 3. Проверка корзины
        $("#open-cart-btn").click();
        $("#cart-items .cart-item")
                .shouldBe(visible)
                .shouldHave(text("Стакан"));
    }
}