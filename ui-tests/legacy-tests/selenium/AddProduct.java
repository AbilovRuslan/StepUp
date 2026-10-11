package tests.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddProduct extends BaseSeleniumTest {

    @Test
    public void addProductAndVerify() {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("secret123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        driver.get("http://localhost:8080/admin");
        driver.findElement(By.id("n-name")).sendKeys("Стакан");
        driver.findElement(By.id("n-price")).sendKeys("25");
        driver.findElement(By.id("add-btn")).click();

        driver.get("http://localhost:8080");
        WebElement product = driver.findElement(By.xpath(
                "//div[contains(@class, 'product-card') and contains(@data-price, '25')]//h4"
        ));
        assertTrue(product.getText().contains("Стакан"));
    }
}