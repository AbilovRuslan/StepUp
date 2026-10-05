package rest.endpoints;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestApiBuilder {

    RequestSpecification spec;

    private static final String BASIC_URL = "http://localhost:8080";
    private static final String LOGIN = "admin";
    private static final String PASS = "secret123";


    public RestApiBuilder() {
        spec = given()
                .filter(new AllureRestAssured())
                .baseUri(BASIC_URL)
                .basePath(Urls.GOODS)
                .log().all()
                .relaxedHTTPSValidation();
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