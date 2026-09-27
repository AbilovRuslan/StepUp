package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class MainPage {



    public SelenideElement adminLink = $("a[href='/admin']");
    public SelenideElement cartButton = $("#open-cart-btn");
    public SelenideElement cartCount = $("#cart-count");
    public ElementsCollection products = $$("div.product-card");

    public SelenideElement cartModal = $("#cartModal");
    public ElementsCollection cartItems = $$("#cart-items .cart-item");
    public SelenideElement totalPrice = $("#total-price");
    public SelenideElement makeOrderButton = $("#makeOrder");

    //  МЕТОДЫ

    // 1. Перейти в админку
    public void openAdmin() {
        adminLink.click();
    }

    // 2. Открыть корзину
    public void openCart() {
        cartButton.click();
    }

    // 3. Добавить товар кнопкой "В корзину"
    public void addProductToCart(String name) {
        $("div[data-name='" + name + "'] button[data-action='add-to-cart']").click();
    }

    // 4. Добавить товар drag-and-drop
    public void dragProductToCart(String name) {
        actions().dragAndDrop(
                $("div[data-name='" + name + "']"),
                cartButton
        ).perform();
    }

    // 5. Увеличить количество
    public void increaseQuantity(String name) {
        $("div[data-name='" + name + "'] button[data-action='qty-change'][data-step='1']").click();
    }

    // 6. Уменьшить количество
    public void decreaseQuantity(String name) {
        $("div[data-name='" + name + "'] button[data-action='qty-change'][data-step='-1']").click();
    }

    // 7. Удалить первый товар из корзины
    public void removeFirstItemFromCart() {
        $x("//div[@id='cart-items']//div[contains(@id, 'cart-item')]/button[last()]").click();
    }

    // 8. Оформить заказ
    public void makeOrder() {
        makeOrderButton.click();
    }

    public SelenideElement toastContainer = $("#toast-container");
}