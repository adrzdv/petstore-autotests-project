package api.tests.store;

import api.base.BaseSetup;
import api.client.StoreApi;
import api.model.OrderDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTests extends BaseSetup {
    private final StoreApi storeApi = new StoreApi(API_KEY);
    private OrderDto order;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @BeforeEach
    void initOrderDto() {
        order = OrderDto.builder()
                .id(1L)
                .petId(1L)
                .complete(false)
                .quantity(1L)
                .shipDate("2025-12-01T18:39:48.953Z")
                .status("placed")
                .build();
    }

    @Test
    @DisplayName("POST /store/order: return 200 and not empty body on success")
    void testOrderAddOrder() {
        Response response = storeApi.createOrder(order);
        assertEquals(200, response.statusCode(), "Expected code 200 but got: " + response.statusCode());
        assertFalse(response.getBody().jsonPath().getMap("").isEmpty(), "Response returned an empty map");
    }

    @Test
    @DisplayName("GET /store/order: get created order by id")
    void testOrderGetOrderById() {
        Response response = storeApi.createOrder(order);
        OrderDto actual = response.getBody().as(OrderDto.class);

        assertAll(
                "Validate the created and retrieved order",
                () -> assertEquals(actual.getId(), order.getId(), "ID mismatch"),
                () -> assertEquals(actual.getPetId(), order.getPetId(), "Pet id mismatch"),
                () -> assertEquals(actual.getStatus(), order.getStatus(), "Status mismatch"),
                () -> assertEquals(actual.getQuantity(), order.getQuantity(), "Quantity mismatch"),
                () -> assertEquals(actual.getComplete(), order.getComplete(), "Complete mismatch"),
                () -> assertEquals(
                        OffsetDateTime.parse(actual.getShipDate(), formatter).toString(),
                        order.getShipDate(),
                        "Ship date mismatch")
        );
    }

    @Test
    @DisplayName("GET /store/order: return 404 on unknown order")
    void testOrderGetUnknownOrder() {
        storeApi.deleteOrderById(1L);
        Response response = storeApi.getOrderById(1L);
        assertEquals(404, response.statusCode(),
                "Expected code: 404, got: " + response.statusCode());
    }

    @Test
    @DisplayName("DELETE /store/order/{id}: delete order by id, returns 200")
    void testOrderDeleteOrderById() {
        Response response = storeApi.createOrder(order);
        long id = response.getBody().as(OrderDto.class).getId();

        Response deleteResponse = storeApi.deleteOrderById(id);
        assertEquals(200, deleteResponse.statusCode(),
                "Expected 200 but got: " + deleteResponse.statusCode());
    }

    @Test
    @DisplayName("DELETE /store/order/{id}: delete order by id, returns 404")
    void testOrderDeleteShouldReturnNotFound() {
        Response deleteResponse = storeApi.deleteOrderById(10000L);
        assertEquals(404, deleteResponse.statusCode(),
                "Expected 404 but got: " + deleteResponse.statusCode());
    }


}
