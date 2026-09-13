import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CartRemoveSelenide extends BaseTest {

    @Test
    public void removeProductFromCart() {
        var product = $("div[data-name='Стакан']");
        var cartButton = $("#open-cart-btn");
        actions().dragAndDrop(product, cartButton).perform();

        cartButton.click();
        $("#cart-items .cart-item").shouldBe(visible);

        // Удаление
        $x("//div[@id='cart-items']//div[contains(@id, 'cart-item')]/button[last()]").click();

        $("#cart-items .cart-item").shouldBe(not(visible));
    }
}