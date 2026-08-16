package rest.endpoints;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestApiBuilder {
    RequestSpecification spec;

    private static final String BASIC_URL = "http://localhost:8080";
    private static final String LOGIN = "admin";
    private static final String PASS = "secret123";

    public RestApiBuilder() {
        spec = given()
                .baseUri(BASIC_URL)
                .basePath(Urls.GOODS)
                .log().all()
                .relaxedHTTPSValidation();
    }

    public RestApiBuilder addAuth(String login, String password) {
        spec = spec.auth().basic(login, password);
        return this;
    }

    public RestApiBuilder setContentJSON() {
        spec = spec.contentType("application/json");
        return this;
    }

    public RequestSpecification getSpec() {
        return spec;
    }

    public static RestApiBuilder getBuilder() {
        return new RestApiBuilder();
    }
}