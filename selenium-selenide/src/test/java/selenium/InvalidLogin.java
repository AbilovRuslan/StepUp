package selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvalidLogin extends BaseSeleniumTest {

    @Test
    public void invalidLogin() {
        driver.get("http://localhost:8080/login");
        driver.findElement(By.name("username")).sendKeys("wrong");
        driver.findElement(By.name("password")).sendKeys("wrong");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        assertTrue(driver.getCurrentUrl().contains("login"));
    }
}