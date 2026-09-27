package pages.asserts;

import com.codeborne.selenide.Condition;
import pages.LoginPage;

public class LoginPageAssert {

    private final LoginPage page = new LoginPage();

    // usernameInput
    public LoginPageAssert usernameVisible() {
        page.usernameInput.shouldBe(Condition.visible);
        return this;
    }

    public LoginPageAssert usernameHasText(String text) {
        page.usernameInput.shouldHave(Condition.value(text));
        return this;
    }

    // passwordInput
    public LoginPageAssert passwordVisible() {
        page.passwordInput.shouldBe(Condition.visible);
        return this;
    }

    public LoginPageAssert passwordHasText(String text) {
        page.passwordInput.shouldHave(Condition.value(text));
        return this;
    }

    // submitButton
    public LoginPageAssert submitButtonVisible() {
        page.submitButton.shouldBe(Condition.visible);
        return this;
    }
}