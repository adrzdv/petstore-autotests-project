package api.tests.user;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDeleteTest extends UserBaseTest {

    @Test
    @DisplayName("DELETE user/{username}: successfully delete user by username")
    void testUserDeleteUserByUsername() {
        Response deleteResponse = userapi.deleteUserByUsername(user.getUsername());
        Response getResponse = userapi.getUserByUsername("username");

        assertEquals(200, deleteResponse.statusCode(),
                "Expected code/got code: 200/" + deleteResponse.statusCode());
        assertEquals(404, getResponse.statusCode(), "User found or got different code");
    }

    @Test
    @DisplayName("DELETE user/{username}: successfully delete user by username")
    void testUserDeleteUserByEmptyUsername() {
        Response deleteResponseNullUsername = userapi.deleteUserByUsername(null);
        Response deleteResponseUnknownUsername = userapi.deleteUserByUsername("Unknown");

        assertEquals(404, deleteResponseNullUsername.statusCode(),
                "Expected code/got code: 404/" + deleteResponseNullUsername.statusCode());
        assertEquals(404, deleteResponseUnknownUsername.statusCode(),
                "Expected code/got code: 404/" + deleteResponseUnknownUsername.statusCode());
    }
}
