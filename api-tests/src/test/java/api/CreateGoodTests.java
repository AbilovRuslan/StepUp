package api;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import rest.assertions.BasicApiAssert;
import rest.endpoints.GoodsEndpoint;

import java.util.List;

@Epic("API")
@Feature("Товары")
public class CreateGoodTests {

    record GoodRequest(String name, double price) {}
    record GoodPatch(String name, double price) {}

    // ===== 1.1 given-when-then =====

    @Test
    @DisplayName("GET /goods/list через given-when-then")
    void testGivenWhenThen() {
        Response response = GoodsEndpoint.getListAuthorized();

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(200);
    }


    @Test
    @DisplayName("GET /goods/list через RequestSpecification")
    void testWithRequestSpec() {
        Response response = GoodsEndpoint.getListAuthorized();

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(200);
    }


    @Test
    @DisplayName("Создать товар и получить его через REST Assured")
    void testCreateAndCheckViaRestAssured() {
        String name = "Good_" + System.currentTimeMillis();
        GoodRequest good = new GoodRequest(name, 99.99);

        Response createResponse = GoodsEndpoint.create(good);
        BasicApiAssert.assertThat(createResponse).statusCodeIsEquals(200);

        long id = createResponse.jsonPath().getLong("data.id");

        Response response = GoodsEndpoint.getById(id);
        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(200)
                .fieldIsEquals("name", name);
    }


    @Test
    @DisplayName("Создать товар и проверить через AssertJ")
    void testCreateAndCheckViaAssertJ() {
        String name = "AssertJGood_" + System.currentTimeMillis();
        GoodRequest good = new GoodRequest(name, 49.99);

        Response createResponse = GoodsEndpoint.create(good);
        BasicApiAssert.assertThat(createResponse)
                .statusCodeIsEquals(200)
                .fieldIsExists("data.id");

        Response listResponse = GoodsEndpoint.getListAuthorized();
        BasicApiAssert.assertThat(listResponse).statusCodeIsEquals(200);

        List<String> names = listResponse.jsonPath().getList("goods.name");
        org.assertj.core.api.Assertions.assertThat(names).contains(name);
    }


    @Test
    @DisplayName("POST /goods/add создаёт товар")
    void addNewGood() {
        GoodRequest good =
                new GoodRequest("Ластик_" + System.currentTimeMillis(), 3.3);

        Response response = GoodsEndpoint.create(good);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(200)
                .fieldIsExists("data.id")
                .fieldIsEquals("message", "success");
    }

    @Test
    @DisplayName("POST /goods/add без авторизации возвращает 401")
    void testAddGoodUnauthorized() {
        GoodRequest good =
                new GoodRequest("НовыйТовар_" + System.currentTimeMillis(), 99.99);

        Response response = GoodsEndpoint.createUnauthorized(good);

        BasicApiAssert.assertThat(response).statusCodeIsEquals(401);
    }

    @Test
    @DisplayName("POST /goods/add с дублирующимся именем возвращает 400")
    void testAddGoodDuplicateName() {
        GoodRequest good =
                new GoodRequest("Дубликат_" + System.currentTimeMillis(), 50.0);

        GoodsEndpoint.create(good);
        Response response = GoodsEndpoint.create(good);

        BasicApiAssert.assertThat(response).statusCodeIsEquals(400);
    }


    @Test
    @DisplayName("GET /goods/list возвращает 200")
    void testGetGoodsList() {
        Response response = GoodsEndpoint.getListAuthorized();

        BasicApiAssert.assertThat(response).statusCodeIsEquals(200);
    }

    @Test
    @DisplayName("GET /goods/list без авторизации возвращает 401")
    void testGetGoodsListUnauthorized() {
        Response response = GoodsEndpoint.getListUnauthorized();

        BasicApiAssert.assertThat(response).statusCodeIsEquals(401);
    }


    @Test
    @DisplayName("GET /goods/{id} возвращает товар")
    void testGetGoodById() {
        String name = "ТоварДляПроверки_" + System.currentTimeMillis();
        GoodRequest good = new GoodRequest(name, 99.99);

        Response createResponse = GoodsEndpoint.create(good);
        long id = createResponse.jsonPath().getLong("data.id");

        Response response = GoodsEndpoint.getById(id);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(200)
                .fieldIsEquals("name", name);
    }

    @Test
    @DisplayName("GET /goods/{id} с несуществующим ID возвращает 404")
    void testGetGoodByIdNotFound() {
        Response response = GoodsEndpoint.getById(999999);

        BasicApiAssert.assertThat(response).statusCodeIsEquals(404);
    }

    @Test
    @DisplayName("GET /goods/{id} без авторизации возвращает 401")
    void testGetGoodByIdUnauthorized() {
        Response response = GoodsEndpoint.getByIdUnauthorized(1);

        BasicApiAssert.assertThat(response).statusCodeIsEquals(401);
    }


    @Test
    @DisplayName("PATCH /goods/{id} обновляет товар")
    void testPatchGood() {
        GoodRequest good =
                new GoodRequest("ДляОбновления_" + System.currentTimeMillis(), 50.0);

        Response createResponse = GoodsEndpoint.create(good);
        long id = createResponse.jsonPath().getLong("data.id");

        String newName = "ОбновлённыйТовар_" + System.currentTimeMillis();
        GoodPatch patch = new GoodPatch(newName, 150.0);

        Response response = GoodsEndpoint.patch(id, patch);

        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(200)
                .fieldIsEquals("name", newName);
    }

    @Test
    @DisplayName("PATCH /goods/{id} с невалидным телом возвращает 400")
    void testPatchGoodBadRequest() {
        GoodRequest good =
                new GoodRequest("ДляПатча_" + System.currentTimeMillis(), 50.0);

        Response createResponse = GoodsEndpoint.create(good);
        long id = createResponse.jsonPath().getLong("data.id");

        String invalidPatch = "{\"price\": \"не число\"}";

        Response response = GoodsEndpoint.patch(id, invalidPatch);

        BasicApiAssert.assertThat(response).statusCodeIsEquals(400);
    }

    @Test
    @DisplayName("PATCH /goods/{id} с несуществующим ID возвращает 404")
    void testPatchGoodNotFound() {
        GoodPatch patch = new GoodPatch("Неважно", 100.0);

        Response response = GoodsEndpoint.patch(999999, patch);

        BasicApiAssert.assertThat(response).statusCodeIsEquals(404);
    }

    @Test
    @DisplayName("PATCH /goods/{id} без авторизации возвращает 401")
    void testPatchGoodUnauthorized() {
        GoodPatch patch = new GoodPatch("Неважно", 100.0);

        Response response = GoodsEndpoint.patchUnauthorized(1, patch);

        BasicApiAssert.assertThat(response).statusCodeIsEquals(401);
    }


    @Test
    @DisplayName("DELETE /goods/{id} удаляет товар")
    void testDeleteGood() {
        GoodRequest good =
                new GoodRequest("ДляУдаления_" + System.currentTimeMillis(), 30.0);

        Response createResponse = GoodsEndpoint.create(good);
        long id = createResponse.jsonPath().getLong("data.id");

        Response response = GoodsEndpoint.delete(id);

        BasicApiAssert.assertThat(response).statusCodeIsEquals(200);
    }

    @Test
    @DisplayName("DELETE /goods/{id} с несуществующим ID возвращает 404")
    void testDeleteGoodNotFound() {
        Response response = GoodsEndpoint.delete(999999);

        BasicApiAssert.assertThat(response).statusCodeIsEquals(404);
    }

    @Test
    @DisplayName("DELETE /goods/{id} без авторизации возвращает 401")
    void testDeleteGoodUnauthorized() {
        Response response = GoodsEndpoint.deleteUnauthorized(1);

        BasicApiAssert.assertThat(response).statusCodeIsEquals(401);
    }


    @AfterEach
    void cleanUp() {
        try {
            Response listResponse = GoodsEndpoint.getListAuthorized();
            List<Integer> ids = listResponse.jsonPath().getList("goods.id");

            if (ids != null && !ids.isEmpty()) {
                for (Integer id : ids) {
                    GoodsEndpoint.delete(id);
                }
            }
        } catch (Exception e) {
            System.out.println("Cleanup skipped: " + e.getMessage());
        }
    }

    @AfterAll
    static void generateAndOpenAllureReport() throws Exception {
        Process generate = new ProcessBuilder(
                "/bin/zsh", "-lc",
                "allure generate build/allure-results " +
                        "-o build/allure-report --clean"
        ).inheritIO().start();

        generate.waitFor();

        new ProcessBuilder(
                "/bin/zsh", "-lc",
                "allure open build/allure-report"
        ).inheritIO().start();
    }
}