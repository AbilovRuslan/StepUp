package tests.selenium;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddToCart extends BaseSeleniumTest {

    @Test
    public void addToCartAndVerify() {
        WebElement productCard = driver.findElement(By.xpath(
                "//div[contains(@class, 'product-card') and contains(@data-name, 'Стакан')]"
        ));
        productCard.findElement(By.xpath(".//button[contains(text(),'В корзину')]")).click();

        driver.findElement(By.id("open-cart-btn")).click();

        WebElement cartItem = driver.findElement(By.xpath(
                "//div[@id='cart-items']//div[contains(@class, 'cart-item')]"
        ));
        assertTrue(cartItem.getText().contains("Стакан"));
    }
}