package api.tests.store;

import api.base.BaseSetup;
import api.client.StoreApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTests extends BaseSetup {
    private final StoreApi storeApi = new StoreApi();

    @Test
    @DisplayName("GET /store/inventory - return status 200")
    void testInventoryReturnsStatusOk() {
        storeApi.getInventory().statusCode(200);
    }

    @Test
    @DisplayName("GET /store/inventory - keys mustn't be null")
    void testInventoryKeysNotNull() {
        Map<String, Integer> map = storeApi.getInventory().extract().jsonPath().getMap("");

        map.keySet().forEach(key -> assertNotEquals("", key.trim(), "Empty key found"));
    }

    @Test
    @DisplayName("GET /store/inventory - values must be positive")
    void testInventoryValuesIsPositive() {
        Map<String, Integer> map = storeApi.getInventory().extract().jsonPath().getMap("");

        map.values().forEach(value -> assertFalse(value < 0, "Negative value: " + value));

    }

    @Test
    @DisplayName("GET /store/inventory - return Map<String, Integer>")
    void testInventoryAnswerIsMap() {
        Map<String, Integer> map = storeApi.getInventory().extract().jsonPath().getMap("");

        assertNotNull(map, "Answer's map is null");

        map.keySet().forEach(key -> assertInstanceOf(String.class, key, "Key is not String.class: " + key));
        map.values().forEach(value -> assertInstanceOf(Integer.class, value, "Value is not Integer.class " + value));
    }

    @Test
    @DisplayName("GET /store/inventory - return not empty Map<>")
    void testInventoryAnswerIsNotEmpty() {
        Map<String, Integer> map = storeApi.getInventory().extract().jsonPath().getMap("");

        assertFalse(map.isEmpty(), "Map is empty");
    }
}
