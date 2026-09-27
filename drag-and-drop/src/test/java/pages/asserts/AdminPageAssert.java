package pages.asserts;

import com.codeborne.selenide.Condition;
import pages.AdminPage;

public class AdminPageAssert {

    private final AdminPage page = new AdminPage();

    public AdminPageAssert nameInputVisible() {
        page.nameInput.shouldBe(Condition.visible);
        return this;
    }

    public AdminPageAssert priceInputVisible() {
        page.priceInput.shouldBe(Condition.visible);
        return this;
    }

    public AdminPageAssert addButtonVisible() {
        page.addButton.shouldBe(Condition.visible);
        return this;
    }

    public AdminPageAssert toastHasText(String text) {
        page.toastContainer.shouldHave(Condition.text(text));
        return this;
    }

    public AdminPageAssert productRowVisible(String name) {
        page.productRows.findBy(Condition.text(name))
                .shouldBe(Condition.visible);
        return this;
    }
}