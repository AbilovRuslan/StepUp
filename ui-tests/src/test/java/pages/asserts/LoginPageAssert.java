package pages.asserts;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import pages.LoginPage;

public class LoginPageAssert {

    private final LoginPage page = new LoginPage();

    @Step("Проверить, что поле логина видно")
    public LoginPageAssert usernameVisible() {
        page.usernameInput.shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверить, что поле пароля видно")
    public LoginPageAssert passwordVisible() {
        page.passwordInput.shouldBe(Condition.visible);
        return this;
    }

    @Step("Проверить, что кнопка входа видна")
    public LoginPageAssert submitButtonVisible() {
        page.submitButton.shouldBe(Condition.visible);
        return this;
    }
}