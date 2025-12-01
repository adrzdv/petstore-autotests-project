package api.client;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class StoreApi {
    private static final String INVENTORY = "/store/inventory";

    public ValidatableResponse getInventory() {
        return given()
                .accept(ContentType.JSON)
                .when()
                .get(INVENTORY)
                .then()
                .log().all();
    }
}
