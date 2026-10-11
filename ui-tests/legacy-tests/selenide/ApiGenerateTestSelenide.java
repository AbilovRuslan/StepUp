package tests.selenide;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class ApiGenerateTestSelenide extends BaseSelenideTest {

    private Long createdId;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @AfterEach
    public void tearDown() {
        if (createdId != null) {
            given().auth().basic("admin", "secret123")
                    .delete("/goods/" + createdId)
                    .then()
                    .statusCode(200);
        }
    }

    @Test
    public void createViaApiAndCheckUI() {
        String name = "API_Товар_" + System.currentTimeMillis();
        String json = String.format("""
                {
                    "name": "%s",
                    "price": 99.0,
                    "quantity": 1
                }
                """, name);

        Response response = given()
                .auth().basic("admin", "secret123")
                .contentType("application/json")
                .body(json)
                .post("/goods/add");

        // Проверяем, что id пришёл
        createdId = response.jsonPath().getLong("data.id");
        if (createdId == null) {
            throw new RuntimeException("Товар не создался: " + response.getBody().asString());
        }

        open("/");
        $("div[data-name='" + name + "'] h4")
                .shouldBe(visible)
                .shouldHave(text(name));
    }
}