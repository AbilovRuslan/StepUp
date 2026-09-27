package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

    public SelenideElement usernameInput = $("[name='username']");
    public SelenideElement passwordInput = $("[name='password']");
    public SelenideElement submitButton = $("button[type='submit']");

    public void login(String username, String password) {
        open("/login");
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        submitButton.click();
    }
}