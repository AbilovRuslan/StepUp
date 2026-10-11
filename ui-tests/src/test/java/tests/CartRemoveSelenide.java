package tests;

import config.ConfigReader;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.asserts.MainPageAssert;

@Epic("SmartShop")
@Feature("Корзина")
public class CartRemoveSelenide extends BaseTest {

    private final MainPage mainPage = new MainPage();

    @Test
    @Story("Удаление из корзины")
    public void removeProductFromCart() {
        String name = ConfigReader.getProductName();

        mainPage.dragProductToCart(name);
        mainPage.openCart();

        new MainPageAssert().cartItemVisible(name);

        mainPage.removeFirstItemFromCart();

        new MainPageAssert().cartItemNotVisible(name);
    }
}