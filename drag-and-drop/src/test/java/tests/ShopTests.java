package tests;

import config.ConfigReader;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import pages.AdminPage;
import pages.LoginPage;
import pages.MainPage;
import pages.asserts.AdminPageAssert;
import pages.asserts.MainPageAssert;

import static com.codeborne.selenide.Selenide.open;


public class ShopTests extends BaseTest {

    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final AdminPage adminPage = new AdminPage();

    @Test
    @Description("Добавить три единицы товара, оплатить, проверить уведомление")
    public void addThreeItemsAndCheckNotification() {
        for (int i = 0; i < 3; i++) {
            mainPage.addProductToCart(ConfigReader.getProductName());
        }
        mainPage.openCart();
        new MainPageAssert().totalPriceEquals("75");
        mainPage.makeOrder();
        new MainPageAssert().orderNotificationVisible();
    }

    @Test
    @Description("Проверить корректность суммы разных товаров")
    public void checkTotalPriceInCart() {
        mainPage.addProductToCart("Стакан");
        mainPage.addProductToCart("Ластик");
        mainPage.openCart();
        new MainPageAssert().totalPriceEquals("28.3");
    }

    @Test
    @Description("Добавить товар через админку, проверить уведомление")
    public void addProductViaAdminAndCheckToast() {
        loginPage.login(
                ConfigReader.getAdminUsername(),
                ConfigReader.getAdminPassword()
        );
        String uniqueName = "Стол_" + System.currentTimeMillis();
        adminPage.openPage()
                .setName(uniqueName)
                .setPrice("25")
                .clickAdd();
        new AdminPageAssert().toastHasText("Товар успешно добавлен!");
    }

    @Test
    public void editProductViaAdminAndVerify() {
        loginPage.login(
                ConfigReader.getAdminUsername(),
                ConfigReader.getAdminPassword()
        );
        String original = "Стакан";
        String updated = "Стакан_updated";
        adminPage.openPage().editProduct(original, updated, "99");
        open("/");
        new MainPageAssert()
                .productVisible(updated)
                .productPriceEquals(updated, "99");
        adminPage.openPage().editProduct(updated, original, "25");
    }
}