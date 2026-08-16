package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rest.assertions.BasicApiAssert;
import rest.endpoints.RestApiBuilder;
import rest.endpoints.Urls;

import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateGoodTests {

    record GoodRequest(String name, double price) {}

    record GoodPatch(String name, double price) {}

    // ===== 1.1 given-when-then =====

    @Test
    @DisplayName("GET /goods/list через given-when-then")
    void testGivenWhenThen() {
        given()
                .baseUri(Urls.BASE_URL)
                .basePath(Urls.GOODS)
                .auth().basic("admin", "secret123")
                .when()
                .get(Urls.LIST)
                .then()
                .statusCode(200);
    }

    // ===== 1.2 RequestSpecification =====

    @Test
    @DisplayName("GET /goods/list через RequestSpecification")
    void testWithRequestSpec() {
        RequestSpecification spec = new RequestSpecBuilder()
                .setBaseUri(Urls.BASE_URL)
                .setBasePath(Urls.GOODS)
                .setAuth(RestAssured.basic("admin", "secret123"))
                .setContentType(ContentType.JSON)
                .build();

        given()
                .spec(spec)
                .get(Urls.LIST)
                .then()
                .statusCode(200);
    }

    // ===== 1.3 POST + GET через REST Assured =====

    @Test
    @DisplayName("Создать товар и получить его через REST Assured")
    void testCreateAndCheckViaRestAssured() {
        String name = "Good_" + System.currentTimeMillis();

        GoodRequest good = new GoodRequest(name, 99.99);

        Response createResponse = given()
                .baseUri(Urls.BASE_URL)
                .basePath(Urls.GOODS)
                .auth().basic("admin", "secret123")
                .contentType(ContentType.JSON)
                .body(good)
                .post(Urls.ADD)
                .then()
                .statusCode(200)
                .extract()
                .response();

        long id = createResponse.jsonPath().getLong("data.id");

        given()
                .baseUri(Urls.BASE_URL)
                .basePath(Urls.GOODS)
                .auth().basic("admin", "secret123")
                .pathParam("id", id)
                .get(Urls.ID)
                .then()
                .statusCode(200)
                .body("id", equalTo((int) id))
                .body("name", equalTo(name))
                .body("price", equalTo(99.99f));
    }

    // ===== 1.4 POST + GET через AssertJ =====

    @Test
    @DisplayName("Создать товар и проверить через AssertJ")
    void testCreateAndCheckViaAssertJ() {
        String name = "AssertJGood_" + System.currentTimeMillis();

        GoodRequest good = new GoodRequest(name, 49.99);

        Response createResponse = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .setContentJSON()
                .getSpec()
                .body(good)
                .post(Urls.ADD);

        BasicApiAssert.assertThat(createResponse)
                .statusCodeIsEquals(200)
                .fieldIsExists("data.id");

        Response listResponse = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .getSpec()
                .get(Urls.LIST);

        BasicApiAssert.assertThat(listResponse)
                .statusCodeIsEquals(200);

        List<String> names = listResponse.jsonPath().getList("name");

        assertThat(names).contains(name);
    }

    // ===== POST /goods/add =====

    @Test
    @DisplayName("POST /goods/add создаёт товар")
    void addNewGood() {
        GoodRequest good = new GoodRequest(
                "Ластик_" + System.currentTimeMillis(),
                3.3
        );

        Response response = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .setContentJSON()
                .getSpec()
                .body(good)
                .post(Urls.ADD);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(200)
                .fieldIsExists("data.id")
                .fieldIsEquals("message", "success");
    }

    @Test
    @DisplayName("POST /goods/add без авторизации возвращает 401")
    void testAddGoodUnauthorized() {
        String name = "НовыйТовар_" + System.currentTimeMillis();

        Response response = given()
                .baseUri(Urls.BASE_URL)
                .basePath(Urls.GOODS)
                .contentType(ContentType.JSON)
                .body(String.format(Locale.US, """
                    {
                        "name": "%s",
                        "price": %f
                    }
                    """, name, 99.99))
                .when()
                .post(Urls.ADD);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(401);
    }

    @Test
    @DisplayName("POST /goods/add с дублирующимся именем возвращает 400")
    void testAddGoodDuplicateName() {
        record GoodRequest(String name, double price) {}

        GoodRequest good = new GoodRequest(
                "Дубликат_" + System.currentTimeMillis(),
                50.0
        );

        String body = String.format(Locale.US, """
            {
                "name": "%s",
                "price": %.2f
            }
            """, good.name(), good.price());

        RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .setContentJSON()
                .getSpec()
                .body(body)
                .post(Urls.ADD);

        Response response = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .setContentJSON()
                .getSpec()
                .body(body)
                .post(Urls.ADD);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(400);
    }
    // ===== GET /goods/list =====

    @Test
    @DisplayName("GET /goods/list возвращает 200")
    void testGetGoodsList() {
        Response response = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .getSpec()
                .get(Urls.LIST);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(200);
    }

    @Test
    @DisplayName("GET /goods/list без авторизации возвращает 401")
    void testGetGoodsListUnauthorized() {
        Response response = RestApiBuilder.getBuilder()
                .getSpec()
                .get(Urls.LIST);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(401);
    }

    // ===== GET /goods/{id} =====

    @Test
    @DisplayName("GET /goods/{id} возвращает товар")
    void testGetGoodById() {
        String name = "ТоварДляПроверки_" + System.currentTimeMillis();

        GoodRequest good = new GoodRequest(name, 99.99);

        Response createResponse = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .setContentJSON()
                .getSpec()
                .body(good)
                .post(Urls.ADD);

        long id = createResponse.jsonPath().getLong("data.id");

        Response response = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .getSpec()
                .pathParam("id", id)
                .get(Urls.ID);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(200)
                .fieldIsEquals("data.id", String.valueOf(id))
                .fieldIsEquals("data.name", name);
    }

    @Test
    @DisplayName("GET /goods/{id} с несуществующим ID возвращает 404")
    void testGetGoodByIdNotFound() {
        Response response = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .getSpec()
                .pathParam("id", 999999)
                .get(Urls.ID);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(404);
    }

    @Test
    @DisplayName("GET /goods/{id} без авторизации возвращает 401")
    void testGetGoodByIdUnauthorized() {
        Response response = RestApiBuilder.getBuilder()
                .getSpec()
                .pathParam("id", 1)
                .get(Urls.ID);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(401);
    }

    // ===== PATCH /goods/{id} =====

    @Test
    @DisplayName("PATCH /goods/{id} обновляет товар")
    void testPatchGood() {
        GoodRequest good = new GoodRequest(
                "ДляОбновления_" + System.currentTimeMillis(),
                50.0
        );

        Response createResponse = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .setContentJSON()
                .getSpec()
                .body(good)
                .post(Urls.ADD);

        long id = createResponse.jsonPath().getLong("data.id");

        String newName = "ОбновлённыйТовар_" + System.currentTimeMillis();

        GoodPatch patch = new GoodPatch(newName, 150.0);

        Response response = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .setContentJSON()
                .getSpec()
                .pathParam("id", id)
                .body(patch)
                .patch(Urls.ID);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(200)
                .fieldIsEquals("name", newName);   // ← без data
    }

    @Test
    @DisplayName("PATCH /goods/{id} с невалидным телом возвращает 400")
    void testPatchGoodBadRequest() {
        GoodRequest good = new GoodRequest(
                "ДляПатча_" + System.currentTimeMillis(),
                50.0
        );

        Response createResponse = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .setContentJSON()
                .getSpec()
                .body(good)
                .post(Urls.ADD);

        long id = createResponse.jsonPath().getLong("data.id");

        String invalidPatch = """
                {
                    "price": "не число"
                }
                """;

        Response response = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .setContentJSON()
                .getSpec()
                .pathParam("id", id)
                .body(invalidPatch)
                .patch(Urls.ID);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(400);
    }

    @Test
    @DisplayName("PATCH /goods/{id} с несуществующим ID возвращает 404")
    void testPatchGoodNotFound() {
        GoodPatch patch = new GoodPatch("Неважно", 100.0);

        Response response = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .setContentJSON()
                .getSpec()
                .pathParam("id", 999999)
                .body(patch)
                .patch(Urls.ID);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(404);
    }

    @Test
    @DisplayName("PATCH /goods/{id} без авторизации возвращает 401")
    void testPatchGoodUnauthorized() {
        GoodPatch patch = new GoodPatch("Неважно", 100.0);

        Response response = RestApiBuilder.getBuilder()
                .setContentJSON()
                .getSpec()
                .pathParam("id", 1)
                .body(patch)
                .patch(Urls.ID);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(401);
    }

    // ===== DELETE /goods/{id} =====

    @Test
    @DisplayName("DELETE /goods/{id} удаляет товар")
    void testDeleteGood() {
        GoodRequest good = new GoodRequest(
                "ДляУдаления_" + System.currentTimeMillis(),
                30.0
        );

        Response createResponse = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .setContentJSON()
                .getSpec()
                .body(good)
                .post(Urls.ADD);

        long id = createResponse.jsonPath().getLong("data.id");

        Response response = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .getSpec()
                .pathParam("id", id)
                .delete(Urls.ID);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(200);
    }

    @Test
    @DisplayName("DELETE /goods/{id} с несуществующим ID возвращает 404")
    void testDeleteGoodNotFound() {
        Response response = RestApiBuilder.getBuilder()
                .addAuth("admin", "secret123")
                .getSpec()
                .pathParam("id", 999999)
                .delete(Urls.ID);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(404);
    }

    @Test
    @DisplayName("DELETE /goods/{id} без авторизации возвращает 401")
    void testDeleteGoodUnauthorized() {
        Response response = RestApiBuilder.getBuilder()
                .getSpec()
                .pathParam("id", 1)
                .delete(Urls.ID);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(401);
    }
}