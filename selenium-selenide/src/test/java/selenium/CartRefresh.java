package selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartRefresh extends BaseSeleniumTest {

    @Test
    public void cartPersistenceAfterRefresh() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement productCard = driver.findElement(By.xpath(
                "//div[contains(@class, 'product-card') and contains(@data-name, 'Стакан')]"
        ));
        productCard.findElement(By.xpath(".//button[contains(text(),'В корзину')]")).click();

        driver.findElement(By.id("open-cart-btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='cart-items']//div[contains(@class, 'cart-item')]")
        ));

        driver.navigate().refresh();

        driver.findElement(By.id("open-cart-btn")).click();
        WebElement cartItemAfterRefresh = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@id='cart-items']//div[contains(@class, 'cart-item')]")
                )
        );
        assertTrue(cartItemAfterRefresh.getText().contains("Стакан"));
    }
}