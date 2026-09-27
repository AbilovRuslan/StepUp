package pages.asserts;

import com.codeborne.selenide.Condition;
import pages.MainPage;

public class MainPageAssert {

    private final MainPage page = new MainPage();

    // adminLink
    public MainPageAssert adminLinkVisible() {
        page.adminLink.shouldBe(Condition.visible);
        return this;
    }

    // cartButton
    public MainPageAssert cartButtonVisible() {
        page.cartButton.shouldBe(Condition.visible);
        return this;
    }

    // cartCount
    public MainPageAssert cartCountEquals(String count) {
        page.cartCount.shouldHave(Condition.text(count));
        return this;
    }

    // products
    public MainPageAssert productVisible(String name) {
        page.products.findBy(Condition.attribute("data-name", name))
                .shouldBe(Condition.visible);
        return this;
    }

    // cartModal
    public MainPageAssert cartModalVisible() {
        page.cartModal.shouldBe(Condition.visible);
        return this;
    }

    // cartItems
    public MainPageAssert cartItemVisible(String name) {
        page.cartItems.findBy(Condition.text(name))
                .shouldBe(Condition.visible);
        return this;
    }

    // totalPrice
    public MainPageAssert totalPriceEquals(String price) {
        page.totalPrice.shouldHave(Condition.text(price));
        return this;
    }

    // makeOrderButton
    public MainPageAssert makeOrderButtonVisible() {
        page.makeOrderButton.shouldBe(Condition.visible);
        return this;
    }

    public MainPageAssert orderNotificationVisible() {
        page.toastContainer.shouldHave(Condition.text("Заказ принят в обработку"));
        return this;
    }

    public MainPageAssert productPriceEquals(String name, String price) {
        page.products.findBy(Condition.attribute("data-name", name))
                .shouldHave(Condition.text(price));
        return this;
    }
}