package api.client;

import api.model.OrderDto;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class StoreApi {
    private static final String INVENTORY = "/store/inventory";
    private static final String ORDER = "/store/order";
    private final String apiKey;

    public StoreApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public Response getInventory() {
        return given()
                .filter(new AllureRestAssured())
                .header("api_key", apiKey)
                .accept(ContentType.JSON)
                .when()
                .get(INVENTORY);
    }

    public Response getInventoryWithWrongKey() {
        return given()
                .filter(new AllureRestAssured())
                .header("api_key", "WRONG_KEY")
                .accept(ContentType.JSON)
                .when()
                .get(INVENTORY);
    }

    public Response getInventoryWithoutAuth() {
        return given()
                .filter(new AllureRestAssured())
                .accept(ContentType.JSON)
                .when()
                .get(INVENTORY);
    }

    public Response createOrder(OrderDto order) {
        return given()
                .filter(new AllureRestAssured())
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(order)
                .post(ORDER);
    }

    public Response getOrderById(long id) {
        return given()
                .filter(new AllureRestAssured())
                .get(ORDER + "/" + id);
    }

    public Response deleteOrderById(long id) {
        return given()
                .filter(new AllureRestAssured())
                .delete(ORDER + "/" + id);
    }
}
