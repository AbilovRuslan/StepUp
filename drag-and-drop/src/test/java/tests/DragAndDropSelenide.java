package tests;

import config.ConfigReader;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.asserts.MainPageAssert;

@Epic("SmartShop")
@Feature("Корзина")
public class DragAndDropSelenide extends BaseTest {

    private final MainPage mainPage = new MainPage();

    @Test
    @Story("Drag-and-Drop")
    @Description("Перетащить товар в корзину и проверить счётчик")
    public void dragProductToCart() {
        String name = ConfigReader.getProductName();

        mainPage.dragProductToCart(name);

        new MainPageAssert().cartCountEquals("1");
    }
}