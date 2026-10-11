package rest.endpoints;

import config.ConfigReader;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestApiBuilder {

    RequestSpecification spec;

    public RestApiBuilder() {
        RequestSpecification base = given()
                .filter(new AllureRestAssured())
                .baseUri(ConfigReader.getApiUrl())
                .basePath(Urls.GOODS)
                .relaxedHTTPSValidation();

        String mode = ConfigReader.getLoggingMode();
        if ("full".equalsIgnoreCase(mode)) {
            base = base.log().all();
        } else if ("validation".equalsIgnoreCase(mode)) {
            base = base.log().ifValidationFails();
        }
        // "none" или другое — вообще без логирования

        spec = base;
    }

    @Step("Добавить базовую авторизацию: {login}")
    public RestApiBuilder addAuth(String login, String password) {
        spec = spec.auth().basic(login, password);
        return this;
    }

    @Step("Установить Content-Type: application/json")
    public RestApiBuilder setContentJSON() {
        spec = spec.contentType("application/json");
        return this;
    }

    @Step("Получить RequestSpecification")
    public RequestSpecification getSpec() {
        return spec;
    }

    @Step("Создать RestApiBuilder")
    public static RestApiBuilder getBuilder() {
        return new RestApiBuilder();
    }
}