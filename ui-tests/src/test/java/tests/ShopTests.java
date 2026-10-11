package tests;

import config.ConfigReader;
import io.qameta.allure.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.AdminPage;
import pages.LoginPage;
import pages.MainPage;
import pages.asserts.AdminPageAssert;
import pages.asserts.MainPageAssert;

@Epic("SmartShop")
@Feature("Корзина и админка")
public class ShopTests extends BaseTest {

    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final AdminPage adminPage = new AdminPage();

    @Test
    @Tag("smoke")
    @Story("Кейс 2.1")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Добавить три единицы товара, оплатить, проверить уведомление")
    public void addThreeItemsAndCheckNotification() {
        for (int i = 0; i < 3; i++) {
            mainPage.addProductToCart(ConfigReader.getProductName());
        }
        mainPage.openCart();
        new MainPageAssert().totalPriceEquals(ConfigReader.getCartTotalThree());
        mainPage.makeOrder();
        new MainPageAssert().orderNotificationVisible();
    }

    @Test
    @Story("Кейс 2.2")
    @Description("Проверить корректность суммы разных товаров")
    public void checkTotalPriceInCart() {
        mainPage.addProductToCart(ConfigReader.getProductName());
        mainPage.addProductToCart(ConfigReader.getSecondProductName());
        mainPage.openCart();
        new MainPageAssert().totalPriceEquals(ConfigReader.getCartTotalTwo());
    }

    @Test
    @Tag("smoke")
    @Story("Кейс 2.3")
    @Description("Добавить товар через админку, проверить уведомление")
    public void addProductViaAdminAndCheckToast() {
        loginPage.login(
                ConfigReader.getAdminUsername(),
                ConfigReader.getAdminPassword()
        );
        String uniqueName = ConfigReader.getAdminProductPrefix() + "_" + System.currentTimeMillis();
        adminPage.openPage()
                .setName(uniqueName)
                .setPrice(String.valueOf(ConfigReader.getProductPrice()))
                .clickAdd();
        new AdminPageAssert().toastHasText(ConfigReader.getToastProductAdded());
    }

    @Test
    @Story("Кейс 2.4")
    @Description("Отредактировать товар, проверить изменения на витрине")
    public void editProductViaAdminAndVerify() {
        loginPage.login(
                ConfigReader.getAdminUsername(),
                ConfigReader.getAdminPassword()
        );
        String original = ConfigReader.getProductName();
        String updated = ConfigReader.getUpdatedProductName();
        String newPrice = ConfigReader.getUpdatedProductPrice();

        adminPage.openPage().editProduct(original, updated, newPrice);

        mainPage.openMainPage();

        new MainPageAssert()
                .productVisible(updated)
                .productPriceEquals(updated, newPrice);

        adminPage.openPage().editProduct(updated, original, String.valueOf(ConfigReader.getProductPrice()));
    }
}