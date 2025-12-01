package api.tests.store;

import api.base.BaseSetup;
import api.client.StoreApi;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTests extends BaseSetup {
    private final StoreApi storeApi = new StoreApi(API_KEY);

    @Test
    @DisplayName("GET /store/inventory: when authorized return status 200")
    void testInventoryReturnsStatusOkWithAuth() {
        assertEquals(storeApi.getInventory().statusCode(), 200, "Status must be 200");
    }

    @Disabled
    @Test
    @DisplayName("GET /store/inventory: returns 4XX when unauthorized")
    void testInventoryShouldFallWhenUnauthorized() {
        Response response = storeApi.getInventoryWithoutAuth();
        int code = response.statusCode();

        assertTrue(
                code == 401 || code == 403,
                "Expected code without token 4XX, but got: " + code
        );
    }

    @Disabled
    @Test
    @DisplayName("GET /store/inventory: returns 4XX with wrong token")
    void inventoryShouldFailWithWrongToken() {
        Response response = storeApi.getInventoryWithWrongKey();
        int code = response.statusCode();

        assertTrue(
                code == 401 || code == 403,
                "Expected code with wrong token 4XX, but got: " + code
        );
    }

    @Test
    @DisplayName("GET /store/inventory - keys mustn't be null")
    void testInventoryKeysNotNull() {
        Map<String, Integer> map = storeApi.getInventory().jsonPath().getMap("");

        map.keySet().forEach(key -> assertNotEquals("", key.trim(), "Empty key found"));
    }

    @Test
    @DisplayName("GET /store/inventory - values must be positive")
    void testInventoryValuesIsPositive() {
        Map<String, Integer> map = storeApi.getInventory().jsonPath().getMap("");

        map.values().forEach(value -> assertFalse(value < 0, "Negative value: " + value));

    }

    @Test
    @DisplayName("GET /store/inventory - return Map<String, Integer>")
    void testInventoryAnswerIsMap() {
        Map<String, Integer> map = storeApi.getInventory().jsonPath().getMap("");

        assertNotNull(map, "Answer's map is null");

        map.keySet().forEach(key -> assertInstanceOf(String.class, key, "Key is not String.class: " + key));
        map.values().forEach(value -> assertInstanceOf(Integer.class, value, "Value is not Integer.class " + value));
    }

    @Test
    @DisplayName("GET /store/inventory - return not empty Map<>")
    void testInventoryAnswerIsNotEmpty() {
        Map<String, Integer> map = storeApi.getInventory().jsonPath().getMap("");

        assertFalse(map.isEmpty(), "Map is empty");
    }
}
