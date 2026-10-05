package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    // ==================== ЭЛЕМЕНТЫ ====================

    public SelenideElement adminLink = $("a[href='/admin']");
    public SelenideElement cartButton = $("#open-cart-btn");
    public SelenideElement cartCount = $("#cart-count");
    public ElementsCollection products = $$("div.product-card");
    public ElementsCollection cartItems = $$("#cart-items .cart-item");
    public SelenideElement totalPrice = $("#total-price");
    public SelenideElement makeOrderButton = $("#makeOrder");
    public SelenideElement toastContainer = $("#toast-container");

    // ==================== МЕТОДЫ ====================

    @Step("Перейти в админку")
    public void openAdmin() {
        adminLink.click();
    }

    @Step("Открыть корзину")
    public void openCart() {
        cartButton.click();
    }

    @Step("Добавить товар '{name}' в корзину")
    public void addProductToCart(String name) {
        $("div[data-name='" + name + "'] button[data-action='add-to-cart']").click();
    }

    @Step("Перетащить товар '{name}' в корзину")
    public void dragProductToCart(String name) {
        actions().dragAndDrop(
                $("div[data-name='" + name + "']"),
                cartButton
        ).perform();
    }

    @Step("Увеличить количество товара '{name}'")
    public void increaseQuantity(String name) {
        $("div[data-name='" + name + "'] button[data-action='qty-change'][data-step='1']").click();
    }

    @Step("Уменьшить количество товара '{name}'")
    public void decreaseQuantity(String name) {
        $("div[data-name='" + name + "'] button[data-action='qty-change'][data-step='-1']").click();
    }

    @Step("Удалить первый товар из корзины")
    public void removeFirstItemFromCart() {
        $x("//div[@id='cart-items']//div[contains(@id, 'cart-item')]/button[last()]").click();
    }

    @Step("Оформить заказ")
    public void makeOrder() {
        makeOrderButton.click();
    }
}