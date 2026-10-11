package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class AdminPage {

    public SelenideElement nameInput = $("#n-name");
    public SelenideElement priceInput = $("#n-price");
    public SelenideElement addButton = $("#add-btn");
    public SelenideElement toastContainer = $("#toast-container");
    public ElementsCollection productRows = $$("#tbody tr");

    @Step("Открыть админку")
    public AdminPage openPage() {
        open("/admin");
        return this;
    }

    @Step("Ввести имя товара '{name}'")
    public AdminPage setName(String name) {
        nameInput.setValue(name);
        return this;
    }

    @Step("Ввести цену товара '{price}'")
    public AdminPage setPrice(String price) {
        priceInput.setValue(price);
        return this;
    }

    @Step("Нажать 'Создать'")
    public AdminPage clickAdd() {
        addButton.click();
        return this;
    }

    @Step("Создать товар '{name}' с ценой '{price}'")
    public AdminPage createProduct(String name, String price) {
        setName(name);
        setPrice(price);
        clickAdd();
        return this;
    }

    @Step("Редактировать товар '{currentName}' → '{newName}' с ценой '{newPrice}'")
    public AdminPage editProduct(String currentName, String newName, String newPrice) {
        String id = $x("//tr[td//input[@value='" + currentName + "']]//button[@data-action='update']")
                .getAttribute("data-id");

        $("#nm-" + id).setValue(newName);
        $("#pr-" + id).setValue(newPrice);
        $x("//button[@data-action='update' and @data-id='" + id + "']").click();

        return this;
    }
}