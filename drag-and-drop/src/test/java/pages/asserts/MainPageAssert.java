package pages.asserts;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import pages.MainPage;

public class MainPageAssert {

    private final MainPage page = new MainPage();

    @Step("Проверить, что счётчик корзины = '{count}'")
    public MainPageAssert cartCountEquals(String count) {
        page.cartCount.shouldHave(Condition.text(count));
        return this;
    }

    @Step("Проверить, что товар '{name}' виден на витрине")
    public MainPageAssert productVisible(String name) {
        page.products.findBy(Condition.attribute("data-name", name))
                .shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверить, что цена товара '{name}' = '{price}'")
    public MainPageAssert productPriceEquals(String name, String price) {
        page.products.findBy(Condition.attribute("data-name", name))
                .shouldHave(Condition.text(price));
        return this;
    }

    @Step("Проверить, что итоговая цена корзины = '{price}'")
    public MainPageAssert totalPriceEquals(String price) {
        page.totalPrice.shouldHave(Condition.text(price));
        return this;
    }

    @Step("Проверить, что появилось уведомление об обработке заказа")
    public MainPageAssert orderNotificationVisible() {
        page.toastContainer.shouldHave(Condition.text("Заказ принят в обработку"));
        return this;
    }

    @Step("Проверить, что товар '{name}' виден в корзине")
    public MainPageAssert cartItemVisible(String name) {
        page.cartItems.findBy(Condition.text(name))
                .shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверить, что товар '{name}' НЕ виден в корзине")
    public MainPageAssert cartItemNotVisible(String name) {
        page.cartItems.findBy(Condition.text(name))
                .shouldNotBe(Condition.visible);
        return this;
    }
}