
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DragAndDropSelenide extends BaseTest {

    @Test
    public void dragProductToCart() {
        // Находим карточку товара и кнопку корзины
        var product = $("div[data-name='Стакан']");
        var cartButton = $("#open-cart-btn");

        // drag-and-drop
        actions().dragAndDrop(product, cartButton).perform();

        $("#cart-count").shouldHave(text("1"));
    }
}