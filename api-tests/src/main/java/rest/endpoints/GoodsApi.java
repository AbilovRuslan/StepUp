package rest.endpoints;

import config.ConfigReader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class GoodsApi {

    private static final String LOGIN = ConfigReader.getAdminUsername();
    private static final String PASS = ConfigReader.getAdminPassword();

    @Step("GET /goods/list (авторизованный)")
    public static Response getListAuthorized() {
        return RestApiBuilder.getBuilder()
                .addAuth(LOGIN, PASS)
                .getSpec()
                .get(Urls.LIST);
    }

    @Step("GET /goods/list (без авторизации)")
    public static Response getListUnauthorized() {
        return RestApiBuilder.getBuilder()
                .getSpec()
                .get(Urls.LIST);
    }

    @Step("POST /goods/add (авторизованный)")
    public static Response create(Object body) {
        return RestApiBuilder.getBuilder()
                .addAuth(LOGIN, PASS)
                .setContentJSON()
                .getSpec()
                .body(body)
                .post(Urls.ADD);
    }

    @Step("POST /goods/add (без авторизации)")
    public static Response createUnauthorized(Object body) {
        return RestApiBuilder.getBuilder()
                .setContentJSON()
                .getSpec()
                .body(body)
                .post(Urls.ADD);
    }

    @Step("GET /goods/{id} (авторизованный)")
    public static Response getById(long id) {
        return RestApiBuilder.getBuilder()
                .addAuth(LOGIN, PASS)
                .getSpec()
                .pathParam("id", id)
                .get(Urls.ID);
    }

    @Step("GET /goods/{id} (без авторизации)")
    public static Response getByIdUnauthorized(long id) {
        return RestApiBuilder.getBuilder()
                .getSpec()
                .pathParam("id", id)
                .get(Urls.ID);
    }

    @Step("PATCH /goods/{id} (авторизованный)")
    public static Response patch(long id, Object body) {
        return RestApiBuilder.getBuilder()
                .addAuth(LOGIN, PASS)
                .setContentJSON()
                .getSpec()
                .pathParam("id", id)
                .body(body)
                .patch(Urls.ID);
    }

    @Step("PATCH /goods/{id} (без авторизации)")
    public static Response patchUnauthorized(long id, Object body) {
        return RestApiBuilder.getBuilder()
                .setContentJSON()
                .getSpec()
                .pathParam("id", id)
                .body(body)
                .patch(Urls.ID);
    }

    @Step("DELETE /goods/{id} (авторизованный)")
    public static Response delete(long id) {
        return RestApiBuilder.getBuilder()
                .addAuth(LOGIN, PASS)
                .getSpec()
                .pathParam("id", id)
                .delete(Urls.ID);
    }

    @Step("DELETE /goods/{id} (без авторизации)")
    public static Response deleteUnauthorized(long id) {
        return RestApiBuilder.getBuilder()
                .getSpec()
                .pathParam("id", id)
                .delete(Urls.ID);
    }
}