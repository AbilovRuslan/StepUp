package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class AdminPage {

    public SelenideElement nameInput = $("#n-name");
    public SelenideElement priceInput = $("#n-price");
    public SelenideElement addButton = $("#add-btn");
    public SelenideElement toastContainer = $("#toast-container");
    public ElementsCollection productRows = $$("#tbody tr");

    public AdminPage openPage() {
        open("/admin");
        return this;
    }

    public AdminPage setName(String name) {
        nameInput.setValue(name);
        return this;
    }

    public AdminPage setPrice(String price) {
        priceInput.setValue(price);
        return this;
    }

    public AdminPage clickAdd() {
        addButton.click();
        return this;
    }

    // Редактирование товара в строке таблицы
    public AdminPage editProduct(String currentName, String newName, String newPrice) {
        // 1. Находим data-id
        String id = $x("//tr[td//input[@value='" + currentName + "']]//button[@data-action='update']")
                .getAttribute("data-id");

        // 2. Меняем имя и цену
        $("#nm-" + id).setValue(newName);
        $("#pr-" + id).setValue(newPrice);

        // 3. Нажимаем "Сохранить"
        $x("//button[@data-action='update' and @data-id='" + id + "']").click();

        return this;
    }
}