package api.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class StoreApi {
    private static final String INVENTORY = "/store/inventory";
    private final String apiKey;

    public StoreApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public Response getInventory() {
        return given()
                .header("api_key", apiKey)
                .accept(ContentType.JSON)
                .when()
                .get(INVENTORY);
    }

    public Response getInventoryWithWrongKey() {
        return given()
                .header("api_key", "WRONG_KEY")
                .accept(ContentType.JSON)
                .when()
                .get(INVENTORY);
    }

    public Response getInventoryWithoutAuth() {
        return given()
                .accept(ContentType.JSON)
                .when()
                .get(INVENTORY);
    }


}
