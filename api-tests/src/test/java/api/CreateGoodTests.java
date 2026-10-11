package api;

import config.ConfigReader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import rest.assertions.BasicApiAssert;
import rest.endpoints.GoodsApi;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Epic("API")
@Feature("Товары")
public class CreateGoodTests {

    private static final Set<Long> createdGoods = ConcurrentHashMap.newKeySet();

    record GoodRequest(String name, double price) {}
    record GoodPatch(String name, double price) {}

    @Test
    @Tag("smoke")
    @DisplayName("GET /goods/list через given-when-then")
    void testGivenWhenThen() {
        Response response = GoodsApi.getListAuthorized();
        BasicApiAssert.assertThat(response).statusCodeIsEquals(ConfigReader.getStatusOk());
    }

    @Test
    @DisplayName("GET /goods/list через RequestSpecification")
    void testWithRequestSpec() {
        Response response = GoodsApi.getListAuthorized();
        BasicApiAssert.assertThat(response).statusCodeIsEquals(ConfigReader.getStatusOk());
    }

    @Test
    @DisplayName("Создать товар и получить его через REST Assured")
    void testCreateAndCheckViaRestAssured() {
        String name = "Good_" + System.currentTimeMillis();
        GoodRequest good = new GoodRequest(name, ConfigReader.getPriceDefault());

        Response createResponse = GoodsApi.create(good);
        BasicApiAssert.assertThat(createResponse).statusCodeIsEquals(ConfigReader.getStatusOk());

        long id = createResponse.jsonPath().getLong("data.id");
        trackCreated(id);

        Response response = GoodsApi.getById(id);
        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(ConfigReader.getStatusOk())
                .fieldIsEquals("name", name);
    }

    @Test
    @DisplayName("Создать товар и проверить через AssertJ")
    void testCreateAndCheckViaAssertJ() {
        String name = "AssertJGood_" + System.currentTimeMillis();
        GoodRequest good = new GoodRequest(name, ConfigReader.getPriceLow());

        Response createResponse = GoodsApi.create(good);
        BasicApiAssert.assertThat(createResponse)
                .statusCodeIsEquals(ConfigReader.getStatusOk())
                .fieldIsExists("data.id");

        long id = createResponse.jsonPath().getLong("data.id");
        trackCreated(id);

        Response listResponse = GoodsApi.getListAuthorized();
        BasicApiAssert.assertThat(listResponse).statusCodeIsEquals(ConfigReader.getStatusOk());

        List<String> names = listResponse.jsonPath().getList("goods.name");
        org.assertj.core.api.Assertions.assertThat(names).contains(name);
    }

    @Test
    @Tag("smoke")
    @DisplayName("POST /goods/add создаёт товар")
    void addNewGood() {
        GoodRequest good = new GoodRequest(
                "Ластик_" + System.currentTimeMillis(),
                ConfigReader.getPriceCheap()
        );

        Response response = GoodsApi.create(good);
        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(ConfigReader.getStatusOk())
                .fieldIsExists("data.id")
                .fieldIsEquals("message", ConfigReader.getMessageSuccess());

        trackCreated(response.jsonPath().getLong("data.id"));
    }

    @Test
    @DisplayName("POST /goods/add без авторизации возвращает 401")
    void testAddGoodUnauthorized() {
        GoodRequest good = new GoodRequest(
                "НовыйТовар_" + System.currentTimeMillis(),
                ConfigReader.getPriceDefault()
        );
        Response response = GoodsApi.createUnauthorized(good);
        BasicApiAssert.assertThat(response).statusCodeIsEquals(ConfigReader.getStatusUnauthorized());
    }

    @Test
    @DisplayName("POST /goods/add с дублирующимся именем возвращает 400")
    void testAddGoodDuplicateName() {
        GoodRequest good = new GoodRequest(
                "Дубликат_" + System.currentTimeMillis(),
                ConfigReader.getPriceMid()
        );

        Response first = GoodsApi.create(good);
        trackCreated(first.jsonPath().getLong("data.id"));

        Response response = GoodsApi.create(good);
        BasicApiAssert.assertThat(response).statusCodeIsEquals(ConfigReader.getStatusBadRequest());
    }

    @Test
    @DisplayName("GET /goods/list возвращает 200")
    void testGetGoodsList() {
        Response response = GoodsApi.getListAuthorized();
        BasicApiAssert.assertThat(response).statusCodeIsEquals(ConfigReader.getStatusOk());
    }

    @Test
    @DisplayName("GET /goods/list без авторизации возвращает 200 (эндпоинт публичный)")
    void testGetGoodsListUnauthorized() {
        Response response = GoodsApi.getListUnauthorized();
        BasicApiAssert.assertThat(response).statusCodeIsEquals(ConfigReader.getStatusOk());
    }

    @Test
    @DisplayName("GET /goods/{id} возвращает товар")
    void testGetGoodById() {
        String name = "ТоварДляПроверки_" + System.currentTimeMillis();
        GoodRequest good = new GoodRequest(name, ConfigReader.getPriceDefault());

        Response createResponse = GoodsApi.create(good);
        long id = createResponse.jsonPath().getLong("data.id");
        trackCreated(id);

        Response response = GoodsApi.getById(id);
        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(ConfigReader.getStatusOk())
                .fieldIsEquals("name", name);
    }

    @Test
    @DisplayName("GET /goods/{id} с несуществующим ID возвращает 404")
    void testGetGoodByIdNotFound() {
        Response response = GoodsApi.getById(ConfigReader.getNotFoundId());
        BasicApiAssert.assertThat(response).statusCodeIsEquals(ConfigReader.getStatusNotFound());
    }

    @Test
    @DisplayName("GET /goods/{id} без авторизации возвращает 401")
    void testGetGoodByIdUnauthorized() {
        Response response = GoodsApi.getByIdUnauthorized(ConfigReader.getUnauthorizedId());
        BasicApiAssert.assertThat(response).statusCodeIsEquals(ConfigReader.getStatusUnauthorized());
    }

    @Test
    @DisplayName("PATCH /goods/{id} обновляет товар")
    void testPatchGood() {
        GoodRequest good = new GoodRequest(
                "ДляОбновления_" + System.currentTimeMillis(),
                ConfigReader.getPriceMid()
        );

        Response createResponse = GoodsApi.create(good);
        long id = createResponse.jsonPath().getLong("data.id");
        trackCreated(id);

        String newName = "ОбновлённыйТовар_" + System.currentTimeMillis();
        GoodPatch patch = new GoodPatch(newName, ConfigReader.getPriceUpdated());

        Response response = GoodsApi.patch(id, patch);
        BasicApiAssert.assertThat(response)
                .statusCodeIsEquals(ConfigReader.getStatusOk())
                .fieldIsEquals("name", newName);
    }

    @Test
    @DisplayName("PATCH /goods/{id} с невалидным телом возвращает 400")
    void testPatchGoodBadRequest() {
        GoodRequest good = new GoodRequest(
                "ДляПатча_" + System.currentTimeMillis(),
                ConfigReader.getPriceMid()
        );
        Response createResponse = GoodsApi.create(good);
        long id = createResponse.jsonPath().getLong("data.id");
        trackCreated(id);

        Response response = GoodsApi.patch(id, ConfigReader.getInvalidPatchBody());
        BasicApiAssert.assertThat(response).statusCodeIsEquals(ConfigReader.getStatusBadRequest());
    }

    @Test
    @DisplayName("PATCH /goods/{id} с несуществующим ID возвращает 404")
    void testPatchGoodNotFound() {
        GoodPatch patch = new GoodPatch(ConfigReader.getPrefixNevazhno(), 100.0);
        Response response = GoodsApi.patch(ConfigReader.getNotFoundId(), patch);
        BasicApiAssert.assertThat(response).statusCodeIsEquals(ConfigReader.getStatusNotFound());
    }

    @Test
    @DisplayName("PATCH /goods/{id} без авторизации возвращает 401")
    void testPatchGoodUnauthorized() {
        GoodPatch patch = new GoodPatch(ConfigReader.getPrefixNevazhno(), 100.0);
        Response response = GoodsApi.patchUnauthorized(ConfigReader.getUnauthorizedId(), patch);
        BasicApiAssert.assertThat(response).statusCodeIsEquals(ConfigReader.getStatusUnauthorized());
    }

    @Test
    @DisplayName("DELETE /goods/{id} удаляет товар")
    void testDeleteGood() {
        GoodRequest good = new GoodRequest(
                "ДляУдаления_" + System.currentTimeMillis(),
                ConfigReader.getPriceDelete()
        );
        Response createResponse = GoodsApi.create(good);
        long id = createResponse.jsonPath().getLong("data.id");
        // не трекаем — удалим сами в тесте

        Response response = GoodsApi.delete(id);
        BasicApiAssert.assertThat(response).statusCodeIsEquals(ConfigReader.getStatusOk());
    }

    @Test
    @DisplayName("DELETE /goods/{id} с несуществующим ID возвращает 404")
    void testDeleteGoodNotFound() {
        Response response = GoodsApi.delete(ConfigReader.getNotFoundId());
        BasicApiAssert.assertThat(response).statusCodeIsEquals(ConfigReader.getStatusNotFound());
    }

    @Test
    @DisplayName("DELETE /goods/{id} без авторизации возвращает 401")
    void testDeleteGoodUnauthorized() {
        Response response = GoodsApi.deleteUnauthorized(ConfigReader.getUnauthorizedId());
        BasicApiAssert.assertThat(response).statusCodeIsEquals(ConfigReader.getStatusUnauthorized());
    }

    // ===== Cleanup: удаляем ТОЛЬКО созданные этим классом товары =====

    @AfterEach
    void cleanUp() {
        for (Long id : createdGoods) {
            try {
                GoodsApi.delete(id);
            } catch (Exception ignored) {
            }
        }
        createdGoods.clear();
    }

    private static void trackCreated(long id) {
        createdGoods.add(id);
    }
}