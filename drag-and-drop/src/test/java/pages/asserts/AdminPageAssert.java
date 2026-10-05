package pages.asserts;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import pages.AdminPage;

public class AdminPageAssert {

    private final AdminPage page = new AdminPage();

    @Step("Проверить, что уведомление содержит '{text}'")
    public AdminPageAssert toastHasText(String text) {
        page.toastContainer.shouldHave(Condition.text(text));
        return this;
    }
}